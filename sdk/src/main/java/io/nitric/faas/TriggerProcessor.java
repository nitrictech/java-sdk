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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.nitric.faas.event.EventContext;
import io.nitric.faas.event.EventHandler;
import io.nitric.faas.event.EventMiddleware;
import io.nitric.faas.http.HttpContext;
import io.nitric.faas.http.HttpHandler;
import io.nitric.faas.http.HttpMiddleware;
import io.nitric.proto.faas.v1.TriggerRequest;
import io.nitric.proto.faas.v1.TriggerResponse;
import io.nitric.util.Contracts;

/**
 * Provides a Nitric TriggerRequest processor class.
 */
public class TriggerProcessor {

    EventHandler eventHandler;
    HttpHandler httpHandler;
    List<EventMiddleware> eventMiddlewares;
    List<HttpMiddleware> httpMiddlewares;

    // Protected --------------------------------------------------------------

    /**
     * Set the Topic TriggerRequest EventHandler object.
     *
     * @param eventHandler the Topic TriggerRequest EventHandler object.
     */
    protected void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * Set the HTTP TriggerRequest HttpHandler object.
     *
     * @param httpHandler the HTTP TriggerRequest HttpHandler object.
     */
    protected void setHttpHandler(HttpHandler httpHandler) {
        this.httpHandler = httpHandler;
    }

    /**
     * Set the Topic TriggerRequest EventMiddlewares.
     *
     * @param eventMiddlewares the list of Topic TriggerRequest EventMiddleware objects.
     */
    protected void setEventMiddlewares(List<EventMiddleware> eventMiddlewares) {
        this.eventMiddlewares = eventMiddlewares;
    }

    /**
     * Set the HTTP TriggerRequest HttpMiddlewares.
     *
     * @param httpMiddlewares the list of HTTP TriggerRequest HttpMiddleware objects.
     */
    protected void setHttpMiddlewares(List<HttpMiddleware> httpMiddlewares) {
        this.httpMiddlewares = httpMiddlewares;
    }

    /**
     * Process the given gRPC TriggerRequest and return a TriggerResponse object.
     *
     * @param triggerRequest the gRPC TriggerRequest object (required)
     * @return the gRPC TriggerResponse object
     */
    protected TriggerResponse process(TriggerRequest triggerRequest) {
        Contracts.requireNonNull(triggerRequest, "triggerRequest");

        if (triggerRequest.hasHttp()) {
            if (httpHandler == null && httpMiddlewares.isEmpty()) {
                throw new IllegalArgumentException("No HTTP handler or middlewares have been registered");
            }

            return processHttpTrigger(triggerRequest);

        } else if (triggerRequest.hasTopic()) {
            if (eventHandler == null && eventMiddlewares.isEmpty()) {
                throw new IllegalArgumentException("No Event handler or middlewares have been registered");
            }

            return processTopicTrigger(triggerRequest);

        } else {
            String msg = "Trigger type is not supported: " + triggerRequest;
            throw new UnsupportedOperationException(msg);
        }
    }

    /**
     * Process the gRPC HTTP TriggerRequest and return a TriggerResponse.
     *
     * @param triggerRequest HTTP TriggerRequest (required)
     * @return a HTTP TriggerResponse
     */
    protected TriggerResponse processHttpTrigger(TriggerRequest triggerRequest) {
        Contracts.requireNonNull(triggerRequest, "triggerRequest");

        var context = Marshaller.toHttpContext(triggerRequest);

        try {
            // Process HTTP Middlewares
            var middleware = buildHttpMiddlewareChain();

            if (middleware != null) {
                context = middleware.handle(context, middleware.getNext());
            }

            if (context == null) {
                // TODO: log null response ?
                return TriggerResponse.newBuilder().build();
            }

            // Process Event Handlers
            if (httpHandler != null) {
                var resultCtx = httpHandler.handle(context);

                if (resultCtx != null) {
                    return Marshaller.toHttpTriggerResponse(resultCtx.getResponse());

                } else {
                    // TODO: log null response ?
                    return TriggerResponse.newBuilder().build();
                }
            } else {
                return Marshaller.toHttpTriggerResponse(context.getResponse());
            }

        } catch (Throwable error) {
            Faas.logError(error,
                          "error handling Trigger HTTP % '%s' with %s",
                          context.getRequest().getMethod(),
                          context.getRequest().getPath(),
                          httpHandler.getClass().getName());

            return null;
        }
    }

    /**
     * Process the gRPC Topic TriggerRequest and return a Topic TriggerResponse.
     *
     * @param triggerRequest Topic TriggerRequest (required)
     * @return a Topic TriggerResponse
     */
    protected TriggerResponse processTopicTrigger(TriggerRequest triggerRequest) {
        Contracts.requireNonNull(triggerRequest, "triggerRequest");

        var context = Marshaller.toEventContext(triggerRequest);

        try {
            // Process Event Middlewares
            var middleware = buildEventMiddlewareChain();

            if (middleware != null) {
                context = middleware.handle(context, middleware.getNext());
            }

            if (context == null) {
                // TODO: log null response ?
                return TriggerResponse.newBuilder().build();
            }

            // Process Event Handlers
            var resultCtx = eventHandler.handle(context);

            if (resultCtx != null) {
                return Marshaller.toTopicTriggerResponse(resultCtx.getResponse());

            } else {
                // TODO: log null response ?
                // TODO: should default to success true or false?
                return TriggerResponse.newBuilder().build();
            }

        } catch (Throwable error) {
            Faas.logError(error,
                          "error handling Trigger Topic % with %s",
                          context.getRequest().getTopic(),
                          eventHandler.getClass().getName());

            return null;
        }
    }

    /**
     * Build a EventMiddleware chain from the configured eventMiddlewares, or null if none defined.
     *
     * @return a new EventMiddleware chain from the configured eventMiddlewares.
     */
    protected EventMiddleware buildEventMiddlewareChain() {
        if (eventMiddlewares == null || eventMiddlewares.isEmpty()) {
            return null;
        }

        var firstMiddleware = eventMiddlewares.get(0);

        var middleware = firstMiddleware;

        for (int i = 1; i < eventMiddlewares.size(); i++) {
            var next = eventMiddlewares.get(i);
            middleware.setNext(next);
            middleware = next;
        }

        middleware.setNext(new FinalEventMiddleware());

        return firstMiddleware;
    }

    /**
     * Build a HttpMiddleware chain from the configured eventMiddlewares, or null if none defined.
     *
     * @return a new HttpMiddleware chain from the configured eventMiddlewares.
     */
    protected HttpMiddleware buildHttpMiddlewareChain() {
        if (httpMiddlewares == null || httpMiddlewares.isEmpty()) {
            return null;
        }

        var firstMiddleware = httpMiddlewares.get(0);

        var middleware = firstMiddleware;

        for (int i = 1; i < httpMiddlewares.size(); i++) {
            var next = httpMiddlewares.get(i);
            middleware.setNext(next);
            middleware = next;
        }

        middleware.setNext(new FinalHttpMiddleware());

        return firstMiddleware;
    }

    // Inner Classes -----------------------------------------------------------------

    /**
     * Provides the final HttpMiddleware in the chain which simply returns the context.
     */
    protected static class FinalHttpMiddleware extends HttpMiddleware {

        @Override
        public HttpContext handle(HttpContext context, HttpMiddleware next) {
            return context;
        }
    }

    /**
     * Provides the final EventMiddleware in the chain which simply returns the context.
     */
    protected static class FinalEventMiddleware extends EventMiddleware {

        @Override
        public EventContext handle(EventContext context, EventMiddleware next) {
            return context;
        }
    }

}