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

import com.sun.net.httpserver.HttpServer;

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
 * @since 1.0.0
 */
public class Faas {

    static final String DEFAULT_HOSTNAME = "127.0.0.1";

    String hostname = DEFAULT_HOSTNAME;
    int port = 8080;
    HttpServer httpServer;

    // Public Methods -------------------------------------------------------------------

    /**
     * Set the server hostname.
     *
     * @param hostname the server hostname
     * @return the Faas server instance
     */
    public Faas hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    /**
     * Set the server port. The default port is 8080.
     *
     * @param port the server port
     * @return the Faas server instance
     */
    public Faas port(int port) {
        this.port = port;
        return this;
    }

    /**
     * Start the FaaS server after configuring the given function.
     *
     * @param function the function (required)
     */
    public void startFunction(NitricFunction function) {
        Objects.requireNonNull(function, "null function parameter");
        // TODO: Add constants for membrane server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).build();
        var svc = FaasGrpc.newStub(channel);
        AtomicReference<StreamObserver<ClientMessage>> clientObserver = new AtomicReference<>();

        // Add a latch to block on while the stream is running
        CountDownLatch finishedLatch = new CountDownLatch(1);
        // Begin the stream
        var observer = svc.triggerStream(new StreamObserver<ServerMessage>() {
            @Override
            public void onNext(ServerMessage serverMessage) {
                // We got a new message from the server
                switch(serverMessage.getContentCase()) {
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
                    case CONTENT_NOT_SET:
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
