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

import java.util.List;

import io.nitric.faas2.event.EventHandler;
import io.nitric.faas2.event.EventMiddleware;
import io.nitric.faas2.http.HttpHandler;
import io.nitric.faas2.http.HttpMiddleware;
import io.nitric.proto.faas.v1.TriggerRequest;
import io.nitric.proto.faas.v1.TriggerResponse;

/**
 * Provides a Nitric TriggerRequest processor class.
 */
class TriggerProcessor {

    final EventHandler eventHandler;
    final HttpHandler httpHandler;
    final List<EventMiddleware> eventMiddlewares;
    final List<HttpMiddleware> httpMiddlewares;

    // Constructor ------------------------------------------------------------

    /**
     * Create a Nitric TriggerProcessor object with the given handlers and middlewares.
     *
     * @param eventHandler the Event handler function
     * @param httpHandler the HTTP handler function
     * @param eventMiddlewares the EventMiddleware list
     * @param httpMiddlewares the HttptMiddleware list
     */
    protected TriggerProcessor(
        EventHandler eventHandler,
        HttpHandler httpHandler,
        List<EventMiddleware> eventMiddlewares,
        List<HttpMiddleware> httpMiddlewares
    ) {
        this.eventHandler = eventHandler;
        this.httpHandler = httpHandler;
        this.eventMiddlewares = eventMiddlewares;
        this.httpMiddlewares = httpMiddlewares;
    }

    // Protected --------------------------------------------------------------

    /**
     * Process the given GRPC TriggerRequest and return a TriggerResponse object.
     *
     * @param triggerRequest the GRPC TriggerRequest object
     * @return the GRPC TriggerResponse object
     */
    protected TriggerResponse process(TriggerRequest triggerRequest) {

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

    // Private Methods ------------------------------------------------

    TriggerResponse processHttpTrigger(TriggerRequest triggerRequest) {

        var context = Marshaller.toHttpContext(triggerRequest);

        try {
            var resultCtx = httpHandler.handle(context);

            if (resultCtx != null) {
                return Marshaller.toHttpTriggerResponse(resultCtx.getResponse());

            } else {
                // TODO: log null response ?
                return TriggerResponse.newBuilder().build();
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

    TriggerResponse processTopicTrigger(TriggerRequest triggerRequest) {

        var context = Marshaller.toEventContext(triggerRequest);

        try {
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

}
