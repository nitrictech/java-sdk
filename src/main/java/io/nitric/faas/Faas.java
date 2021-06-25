package io.nitric.faas;

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

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.nitric.proto.faas.v1.FaasGrpc;
import io.nitric.proto.faas.v1.InitRequest;
import io.nitric.proto.faas.v1.ServerMessage;
import io.nitric.proto.faas.v1.ClientMessage;
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
 * public class HelloWorld implements NitricFunction {
 *
 *     public Response handle(Trigger trigger) {
 *         return trigger.buildResponse("Hello World");
 *     }
 *
 *     public static void main(String... args) {
 *         Faas.start(new HelloWorld());
 *     }
 * }
 * </code></pre>
 *
 * @see NitricFunction
 *
 */
public class Faas {

    private FaasGrpc.FaasStub stub = null;

    /**
     * Set the gRPC stub to use for this FaaS instance
     * Can be used to provide a connection on a new channel
     * in order to connect securely with a remote membrane host
     *
     * @param stub - Stub instance to provide
     */
    protected Faas stub(FaasGrpc.FaasStub stub) {
        this.stub = stub;
        return this;
    }

    // Public Methods -------------------------------------------------------------------
    /**
     * Start the FaaS server after configuring the given function.
     * This method will block until the stream has terminated
     *
     * @param function the function (required)
     */
    public void startFunction(NitricFunction function) {
        Objects.requireNonNull(function, "null function parameter");

        // FIXME: Uncoverable code without mocking static methods (need to include Powermock)
        // Once we've asserted that this interfaces with a mocked stream observer
        // We only need to assert the FaasGrpc.newStub is called to cover this logic in
        // the case where the user has not provided a custom stub
        if (this.stub == null) {
            // Create a default stub with the singleton channel
            // TODO: Determine if we should use a dedicated channel for this FaaS loop?
            this.stub = FaasGrpc.newStub(GrpcChannelProvider.getChannel());
        }

        AtomicReference<StreamObserver<ClientMessage>> clientObserver = new AtomicReference<>();
        // Add a latch to block on while the stream is running
        CountDownLatch finishedLatch = new CountDownLatch(1);
        // Begin the stream
        var observer = this.stub.triggerStream(new StreamObserver<>() {
            @Override
            public void onNext(ServerMessage serverMessage) {
                // We got a new message from the server
                switch (serverMessage.getContentCase()) {
                    case INIT_RESPONSE:
                        // We have an init ack from the membrane
                        // XXX: NO OP for now
                        break;
                    case TRIGGER_REQUEST:
                        var trigger = Trigger.buildTrigger(serverMessage.getTriggerRequest());
                        // Call the function
                        var response = function.handle(trigger);
                        var grpcResponse = response.toGrpcTriggerResponse();
                        // write back the response to the server
                        clientObserver.get().onNext(
                                ClientMessage
                                        .newBuilder()
                                        .setId(serverMessage.getId())
                                        .setTriggerResponse(grpcResponse)
                                        .build());
                        break;
                    default:
                        // TODO: Add error case here
                        break;
                }
            }

            @Override
            public void onError(Throwable throwable) {
                // TODO: Handle stream errors here
                // We may want to exit when the membrane server indicates an error...
            }

            @Override
            public void onCompleted() {
                // The server has indicated that streaming is now over we can exit
                // Unlock from exit
                finishedLatch.countDown();
            }
        });

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
        } catch (Throwable t) {
            // Log that the stream was prematurely terminated
        } finally {
            // Always ensure the client stream is closed
            observer.onCompleted();
        }
    }

    /**
     * Quick Start a Nitric Function with defaults
     *
     * @param function The function to start
     */
    public static Faas start(NitricFunction function) {
        var faas = new Faas();
        faas.startFunction(function);
        return faas;
    }
}
