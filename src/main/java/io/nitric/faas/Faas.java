/*-
 * #%L
 * Nitric Java SDK
 * %%
 * Copyright (C) 2021 Nitric Technologies Pty Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package io.nitric.faas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import io.grpc.stub.StreamObserver;
import io.nitric.proto.faas.v1.ClientMessage;
import io.nitric.proto.faas.v1.FaasServiceGrpc;
import io.nitric.proto.faas.v1.InitRequest;
import io.nitric.proto.faas.v1.ServerMessage;
import io.nitric.util.Contracts;
import io.nitric.util.GrpcChannelProvider;

/**
 * <p>
 *  Provides a Nitric FaaS (Function as a Service) server.
 * </p>
 *
 * <p>
 *  The example below starts a new <code>Faas</code> server with the <code>HelloWorld</code> function.
 * </p>
 *
 * <pre><code class="code">
 * package com.example;
 *
 * import io.nitric.faas.Faas;
 * import io.nitric.faas.Trigger;
 * import io.nitric.faas.NitricFunction;
 * import io.nitric.faas.Response;
 *
 * public class Handler implements NitricFunction {
 *
 *     &commat;Override
 *     public Response handle(Trigger trigger) {
 *         return trigger.buildResponse("Hello World");
 *     }
 *
 *     public static void main(String... args) {
 *         Faas.start(new Handler());
 *     }
 * }
 * </code></pre>
 *
 * @see NitricFunction
 */
public class Faas {

    private FaasServiceGrpc.FaasServiceStub stub = null;

    // Public Methods -------------------------------------------------------------------

    /**
     * Start the FaaS server after configuring the given function.
     * This method will block until the stream has terminated.
     *
     * @param function the function handler (required)
     */
    public void startFunction(NitricFunction function) {
        Contracts.requireNonNull(function, "function");

        // FIXME: Uncoverable code without mocking static methods (need to include Powermock)
        // Once we've asserted that this interfaces with a mocked stream observer
        // We only need to assert the FaasGrpc.newStub is called to cover this logic in
        // the case where the user has not provided a custom stub
        if (this.stub == null) {
            // Create a default stub with the singleton channel
            // TODO: Determine if we should use a dedicated channel for this FaaS loop?
            this.stub = FaasServiceGrpc.newStub(GrpcChannelProvider.getChannel());
        }

        AtomicReference<StreamObserver<ClientMessage>> clientObserver = new AtomicReference<>();

        // Add a latch to block on while the stream is running
        CountDownLatch finishedLatch = new CountDownLatch(1);

        // Begin the stream
        var observer = this.stub.triggerStream(new FaasStreamObserver(function, clientObserver, finishedLatch));

        // Set atomic reference for the client to send messages back to the server
        // In the server message stream observer loop (see above)
        clientObserver.set(observer);

        // Send an init request to the server and let it know we're ready to receive work
        observer.onNext(
            ClientMessage
                    .newBuilder()
                    .setInitRequest(InitRequest.newBuilder().build())
                    .build()
        );

        try {
            finishedLatch.await();
        } catch (Throwable error) {
            System.err.printf("Stream was prematurely terminated for function: %s, error: %s \n",
                              function.getClass().getSimpleName(),
                              error);

        } finally {
            // Always ensure the client stream is closed
            observer.onCompleted();
        }
    }

    /**
     * Start a Nitric function server with the given function.
     *
     * @param function The function to start (required)
     * @return a new started Nitric Function server
     */
    public static Faas start(NitricFunction function) {
        var faas = new Faas();
        faas.startFunction(function);
        return faas;
    }

    // Package Methods --------------------------------------------------------

    /**
     * Set the gRPC stub to use for this FaaS instance.
     * Can be used to provide a connection on a new channel
     * in order to connect securely with a remote membrane host.
     *
     * @param stub - Stub instance to provide
     */
    protected Faas stub(FaasServiceGrpc.FaasServiceStub stub) {
        this.stub = stub;
        return this;
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides the FaaS GRCP function stream handler.
     */
    static class FaasStreamObserver implements StreamObserver<ServerMessage> {

        final NitricFunction function;
        final AtomicReference<StreamObserver<ClientMessage>> clientObserver;
        final CountDownLatch finishedLatch;

        FaasStreamObserver(
            NitricFunction function,
            AtomicReference<StreamObserver<ClientMessage>> clientObserver,
            CountDownLatch finishedLatch
        ) {
            this.function = function;
            this.clientObserver = clientObserver;
            this.finishedLatch = finishedLatch;
        }

        @Override
        public void onNext(ServerMessage serverMessage) {
            // We got a new message from the server
            switch (serverMessage.getContentCase()) {
                case INIT_RESPONSE:
                    // We have an init ack from the membrane
                    // XXX: NO OP for now
                    break;
                case TRIGGER_REQUEST:
                    Trigger trigger = null;
                    try {
                        // Call the function
                        trigger = Trigger.buildTrigger(serverMessage.getTriggerRequest());
                        var response = function.handle(trigger);

                        // Write back the response to the server
                        var grpcResponse = response.toGrpcTriggerResponse();
                        clientObserver.get().onNext(
                                ClientMessage
                                        .newBuilder()
                                        .setId(serverMessage.getId())
                                        .setTriggerResponse(grpcResponse)
                                        .build());
                    } catch (Throwable error) {
                        System.err.printf("onNext() error occurred handling trigger %s with function: %s \n",
                                          trigger,
                                          function.getClass().getName());
                        error.printStackTrace();
                    }
                    break;
                default:
                    System.err.printf("onNext() default case %s reached with function: %s \n",
                                      serverMessage.getContentCase(),
                                      function.getClass().getName());
                    break;
            }
        }

        @Override
        public void onError(Throwable error) {
            // We may want to exit when the membrane server indicates an error...
            System.err.printf("onError() occurred with function: %s, error: %s \n",
                              function.getClass().getName(),
                              error);
            error.printStackTrace();
        }

        @Override
        public void onCompleted() {
            // The server has indicated that streaming is now over we can exit
            // Unlock from exit
            finishedLatch.countDown();
        }
    }

}
