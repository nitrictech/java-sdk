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

package io.nitric.faas2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.stub.StreamObserver;
import io.nitric.faas2.event.EventHandler;
import io.nitric.faas2.http.HttpHandler;
import io.nitric.proto.faas.v1.ClientMessage;
import io.nitric.proto.faas.v1.FaasServiceGrpc;
import io.nitric.proto.faas.v1.InitRequest;
import io.nitric.proto.faas.v1.ServerMessage;
import io.nitric.proto.faas.v1.TriggerRequest;
import io.nitric.proto.faas.v1.TriggerResponse;
import io.nitric.util.Contracts;
import io.nitric.util.GrpcChannelProvider;

/**
 * <p>
 *  Provides a Nitric FaaS (Function as a Service) server.
 * </p>
 *
 * <p>
 *  The example below starts a new <code>Faas</code> server with the <code>Handler</code> function.
 * </p>
 *
 * <pre><code class="code">
 * TODO...
 * </code></pre>
 *
 * @see NitricFunction
 */
public class Faas {

    private static final Logger LOGGER = Logger.getLogger("Faas");

    private FaasServiceGrpc.FaasServiceStub stub = null;

    private EventHandler eventHandler;
    private HttpHandler httpHandler;

    // Public Methods -------------------------------------------------------------------

    /**
     * Register a Faas EventHandler.
     *
     * @param eventHandler the EventHandler to register (required)
     * @return the Faas object for chaining
     */
    public Faas setHandler(EventHandler eventHandler) {
        Contracts.requireNonNull(eventHandler, "eventHandler");

        this.eventHandler = eventHandler;
        return this;
    }

    /**
     * Register a Faas HttpHandler.
     *
     * @param httpHandler the HttpHandler to register (required)
     * @return the Faas object for chaining
     */
    public Faas setHandler(HttpHandler httpHandler) {
        Contracts.requireNonNull(httpHandler, "httpHandler");

        this.httpHandler = httpHandler;
        return this;
    }

    /**
     * Start the FaaS server after configuring the given function.
     * This method will block until the stream has terminated.
     */
    public void start() {
        if (eventHandler == null && httpHandler == null) {
            throw new IllegalArgumentException("No trigger handler functions have been registered");
        }

        var triggerProcesor = new TriggerProcessor(eventHandler, httpHandler, LOGGER);

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
        var observer = this.stub.triggerStream(new FaasStreamObserver(triggerProcesor, clientObserver, finishedLatch));

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

        } catch (InterruptedException e) {
            var handlerName = (eventHandler != null)
                ? eventHandler.getClass().getSimpleName() : httpHandler.getClass().getSimpleName();
            logError(e,
                     "Stream was prematurely terminated for function: %s, error: %s \n",
                     handlerName);
            // Restore thread interrupted state
            Thread.currentThread().interrupt();

        } finally {
            // Always ensure the client stream is closed
            observer.onCompleted();
        }
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

    // Package Private Methods ------------------------------------------------

    static void logError(String format, Object...args) {
        String msg = String.format(format, args);
        LOGGER.log(Level.SEVERE, msg);
    }

    static void logError(Throwable error, String format, Object...args) {
        String msg = String.format(format, args);
        LOGGER.log(Level.SEVERE, msg, error);
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides the FaaS GRCP function stream handler.
     */
    static class FaasStreamObserver implements StreamObserver<ServerMessage> {

        final TriggerProcessor triggerProcessor;
        final AtomicReference<StreamObserver<ClientMessage>> clientObserver;
        final CountDownLatch finishedLatch;

        FaasStreamObserver(
            TriggerProcessor triggerProcessor,
            AtomicReference<StreamObserver<ClientMessage>> clientObserver,
            CountDownLatch finishedLatch
        ) {
            this.triggerProcessor = triggerProcessor;
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
                    TriggerRequest request = null;
                    try {
                        request = serverMessage.getTriggerRequest();

                        TriggerResponse response = triggerProcessor.process(request);

                        // Write back the response to the server
                        clientObserver.get().onNext(
                                ClientMessage
                                        .newBuilder()
                                        .setId(serverMessage.getId())
                                        .setTriggerResponse(response)
                                        .build());

                    } catch (Throwable error) {
                        logError(error, "onNext() error occurred handling trigger %s", request);
                    }
                    break;

                default:
                    logError("onNext() default case %s reached",
                             serverMessage.getContentCase());
                    break;
            }
        }

        @Override
        public void onError(Throwable error) {
            logError(error, "onError() occurred");
        }

        @Override
        public void onCompleted() {
            // The server has indicated that streaming is now over we can exit
            // Unlock from exit
            finishedLatch.countDown();
        }
    }

}
