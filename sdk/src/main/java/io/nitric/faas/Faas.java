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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.stub.StreamObserver;
import io.nitric.faas.event.EventHandler;
import io.nitric.faas.event.EventMiddleware;
import io.nitric.faas.http.HttpHandler;
import io.nitric.faas.http.HttpMiddleware;
import io.nitric.proto.faas.v1.ClientMessage;
import io.nitric.proto.faas.v1.FaasServiceGrpc;
import io.nitric.proto.faas.v1.InitRequest;
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
 * import io.nitric.api.NotFoundException;
 * import io.nitric.api.document.Documents;
 * import io.nitric.faas.Faas;
 * import io.nitric.faas.http.HttpContext;
 * import io.nitric.faas.http.HttpHandler;
 *
 * public class ReadFunction implements HttpHandler {
 *
 *     final Documents documents;
 *
 *     public ReadFunction(Documents documents) {
 *         this.documents = documents;
 *     }
 *
 *     public HttpContext handle(HttpContext context) {
 *
 *         var paths = context.getRequest().getPath().split("/");
 *         var id = paths[paths.length - 1];
 *
 *         try {
 *             var json = documents.collection("examples")
 *                 .doc(id)
 *                 .getJson();
 *
 *             context.getResponse()
 *                 .status(200)
 *                 .addHeader("Content-Type", "application/json")
 *                 .data(json);
 *
 *         } catch (NotFoundException nfe) {
 *             context.getResponse()
 *                 .status(404)
 *                 .data("Document not found: " + id);
 *         }
 *
 *         return context;
 *     }
 *
 *     public static void main(String[] args) {
 *         var read = new ReadFunction(new Documents());
 *         new Faas().http(read).start();
 *     }
 * } </code></pre>
 *
 * @see NitricFunction
 */
public class Faas {

    private static final Logger LOGGER = Logger.getLogger("Faas");

    private FaasServiceGrpc.FaasServiceStub stub = null;

    private EventHandler eventHandler;
    private HttpHandler httpHandler;
    private List<EventMiddleware> eventMiddlewares = new ArrayList<>();
    private List<HttpMiddleware> httpMiddlewares = new ArrayList<>();

    // Public Methods -------------------------------------------------------------------

    /**
     * Register a EventHandler function.
     *
     * @param eventHandler the EventHandler to register (required)
     * @return this chainable Faas object
     */
    public Faas event(EventHandler eventHandler) {
        Contracts.requireNonNull(eventHandler, "eventHandler");

        if (httpHandler != null || !httpMiddlewares.isEmpty()) {
            String msg = "Cannot register a EventHander if a HttpHandler or HttpMiddleware added";
            throw new IllegalArgumentException(msg);
        }

        this.eventHandler = eventHandler;
        return this;
    }

    /**
     * Register a HttpHandler function.
     *
     * @param httpHandler the HttpHandler to register (required)
     * @return this chainable Faas object
     */
    public Faas http(HttpHandler httpHandler) {
        Contracts.requireNonNull(httpHandler, "httpHandler");

        if (eventHandler != null || !eventMiddlewares.isEmpty()) {
            String msg = "Cannot register a HttpHandler if a EventHandler or EventMiddleware added";
            throw new IllegalArgumentException(msg);
        }

        this.httpHandler = httpHandler;
        return this;
    }

    /**
     * Add an EventMiddleware handler object.
     *
     * @param middleware the EventMiddleware handler object (required)
     * @return this chainable Faas object
     */
    public Faas addMiddleware(EventMiddleware middleware) {
        Contracts.requireNonNull(middleware, "middleware");

        if (httpHandler != null || !httpMiddlewares.isEmpty()) {
            String msg = "Cannot register a EventHander if a HttpHandler or HttpMiddleware added";
            throw new IllegalArgumentException(msg);
        }

        eventMiddlewares.add(middleware);
        return this;
    }

    /**
     * Add an HttpMiddleware handler object.
     *
     * @param middleware the HttpMiddleware handler object (required)
     * @return this chainable Faas object
     */
    public Faas addMiddleware(HttpMiddleware middleware) {
        Contracts.requireNonNull(middleware, "middleware");

        if (eventHandler != null || !eventMiddlewares.isEmpty()) {
            String msg = "Cannot register a HttpHandler if a EventHandler or EventMiddleware added";
            throw new IllegalArgumentException(msg);
        }

        httpMiddlewares.add(middleware);
        return this;
    }

    /**
     * Start the FaaS server after configuring the given function.
     * This method will block until the stream has terminated.
     */
    public void start() {

        if (eventHandler == null
            && httpHandler == null
            && eventMiddlewares.isEmpty()
            && httpMiddlewares.isEmpty()) {

            throw new IllegalArgumentException("No handler or middleware functions have been registered");
        }

        var triggerProcesor = new TriggerProcessor(
            eventHandler,
            httpHandler,
            eventMiddlewares,
            httpMiddlewares);

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

    protected static void logError(String format, Object...args) {
        String msg = String.format(format, args);
        LOGGER.log(Level.SEVERE, msg);
    }

    protected static void logError(Throwable error, String format, Object...args) {
        String msg = String.format(format, args);
        LOGGER.log(Level.SEVERE, msg, error);
    }

}
