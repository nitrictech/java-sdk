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

import com.google.protobuf.ByteString;
import io.nitric.faas.event.EventContext;
import io.nitric.faas.event.EventMiddleware;
import io.nitric.faas.http.HttpContext;
import io.nitric.faas.http.HttpMiddleware;
import io.nitric.proto.faas.v1.HttpResponseContext;
import io.nitric.proto.faas.v1.TriggerRequest;
import io.nitric.proto.faas.v1.TriggerResponse;
import io.nitric.util.Contracts;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Provides a Nitric TriggerRequest processor class.
 */
public class TriggerProcessor {

    static final ThreadLocal<String> middlewareThreadLocal = new ThreadLocal<>();

    List<EventMiddleware> eventMiddlewares;
    List<HttpMiddleware> httpMiddlewares;

    // Protected --------------------------------------------------------------

    /**
     * Set the Topic TriggerRequest EventMiddlewares.
     *
     * @param eventMiddlewares the list of Topic TriggerRequest EventMiddleware objects
     */
    protected void setEventMiddlewares(List<EventMiddleware> eventMiddlewares) {
        this.eventMiddlewares = eventMiddlewares;
    }

    /**
     * Set the HTTP TriggerRequest HttpMiddlewares.
     *
     * @param httpMiddlewares the list of HTTP TriggerRequest HttpMiddleware objects
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
            if (httpMiddlewares == null || httpMiddlewares.isEmpty()) {
                throw new IllegalStateException("No HTTP handler or middlewares have been registered");
            }

            return processHttpTrigger(triggerRequest);

        } else if (triggerRequest.hasTopic()) {
            if (eventMiddlewares == null || eventMiddlewares.isEmpty()) {
                throw new IllegalStateException("No Event handler or middlewares have been registered");
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

        var middleware = buildHttpMiddlewareChain();

        try {
            var resultCtx = middleware.handle(context, middleware.getNext());

            return Marshaller.toHttpTriggerResponse(resultCtx.getResponse());

        } catch (Throwable error) {
            Faas.logError(error,
                    "error handling Trigger HTTP %s '%s' with: %s",
                    context.getRequest().getMethod(),
                    context.getRequest().getPath(),
                    middlewareThreadLocal.get());

            return TriggerResponse.newBuilder()
                    .setHttp(HttpResponseContext.newBuilder().setStatus(500))
                    .setData(ByteString.copyFrom("Error occurred see logs for details", StandardCharsets.UTF_8))
                    .build();
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

        var middleware = buildEventMiddlewareChain();

        try {
            var resultCtx = middleware.handle(context, middleware.getNext());

            return Marshaller.toTopicTriggerResponse(resultCtx.getResponse());

        } catch (Throwable error) {
            Faas.logError(error,
                    "error handling Trigger Topic '%s' with: %s",
                    context.getRequest().getTopic(),
                    middlewareThreadLocal.get());
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
            throw new IllegalStateException("no httpMiddlewares have been configured");
        }

        EventMiddleware firstMiddleware = new EventMiddlewareWrapper(eventMiddlewares.get(0));

        var middleware = firstMiddleware;

        for (int i = 1; i < eventMiddlewares.size(); i++) {
            var next = new EventMiddlewareWrapper(eventMiddlewares.get(i));
            middleware.setNext(next);
            middleware = next;
        }

        return firstMiddleware;
    }

    /**
     * Build a HttpMiddleware chain from the configured eventMiddlewares, or null if none defined.
     *
     * @return a new HttpMiddleware chain from the configured eventMiddlewares.
     */
    protected HttpMiddleware buildHttpMiddlewareChain() {
        if (httpMiddlewares == null || httpMiddlewares.isEmpty()) {
            throw new IllegalStateException("no httpMiddlewares have been configured");
        }

        HttpMiddleware firstMiddleware = new HttpMiddlewareWrapper(httpMiddlewares.get(0));

        var middleware = firstMiddleware;

        for (int i = 1; i < httpMiddlewares.size(); i++) {
            var next = new HttpMiddlewareWrapper(httpMiddlewares.get(i));
            middleware.setNext(next);
            middleware = next;
        }

        return firstMiddleware;
    }

    // Inner Classes -----------------------------------------------------------------

    /**
     * Provides an EventMiddleware wrapper to accurately report processing errors.
     */
    protected static class EventMiddlewareWrapper extends EventMiddleware {

        final EventMiddleware target;

        /**
         * Create new EventMiddleware wrapper object with given target.
         *
         * @param target the target middleware to process
         */
        protected EventMiddlewareWrapper(EventMiddleware target) {
            this.target = target;
        }

        @Override
        public EventContext handle(EventContext context, EventMiddleware next) {
            // Stash the target name for error reporting
            if (target instanceof EventMiddleware.HandlerAdapter) {
                var adapter = (EventMiddleware.HandlerAdapter) target;
                middlewareThreadLocal.set(adapter.getHandler().getClass().getName());
            } else {
                middlewareThreadLocal.set(target.getClass().getName());
            }

            var resultCtx = target.handle(context, next);
            if (resultCtx == null) {
                throw new IllegalStateException("invoked handle() method returned null context");
            }

            return next.handle(resultCtx, next.getNext());
        }
    }

    /**
     * Provides an HttpMiddleware wrapper to accurately report processing errors.
     */
    protected static class HttpMiddlewareWrapper extends HttpMiddleware {

        final HttpMiddleware target;

        /**
         * Create new HttpMiddleware wrapper object with given target.
         *
         * @param target the target middleware to process
         */
        protected HttpMiddlewareWrapper(HttpMiddleware target) {
            this.target = target;
        }

        @Override
        public HttpContext handle(HttpContext context, HttpMiddleware next) {
            // Stash the target name for error reporting
            if (target instanceof HttpMiddleware.HandlerAdapter) {
                var adapter = (HttpMiddleware.HandlerAdapter) target;
                middlewareThreadLocal.set(adapter.getHandler().getClass().getName());
            } else {
                middlewareThreadLocal.set(target.getClass().getName());
            }

            var resultCtx = target.handle(context, next);
            if (resultCtx == null) {
                throw new IllegalStateException("invoked handle() method returned null context");
            }

            return next.handle(resultCtx, next.getNext());
        }
    }

}