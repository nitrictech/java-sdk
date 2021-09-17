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

import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.protobuf.ByteString;

import io.nitric.faas2.event.EventContext;
import io.nitric.faas2.event.EventHandler;
import io.nitric.faas2.event.EventRequest;
import io.nitric.faas2.event.EventResponse;
import io.nitric.faas2.http.HttpContext;
import io.nitric.faas2.http.HttpHandler;
import io.nitric.faas2.http.HttpRequest;
import io.nitric.faas2.http.HttpResponse;
import io.nitric.proto.faas.v1.HeaderValue;
import io.nitric.proto.faas.v1.HttpResponseContext;
import io.nitric.proto.faas.v1.TopicResponseContext;
import io.nitric.proto.faas.v1.TriggerRequest;
import io.nitric.proto.faas.v1.TriggerResponse;
import io.nitric.util.Contracts;

/**
 * Provides a Nitric TriggerRequest processor class.
 */
public class TriggerProcessor {

    private EventHandler eventHandler;
    private HttpHandler httpHandler;
    private Logger logger;

    // Constructor ------------------------------------------------------------

    /**
     * Create a Nitric TriggerProcessor object with the given handlers and logger.
     *
     * @param eventHandler the Topic event handler
     * @param httpHandler the HTTP handler
     * @param logger the logger instance
     */
    public TriggerProcessor(EventHandler eventHandler, HttpHandler httpHandler, Logger logger) {
        this.eventHandler = eventHandler;
        this.httpHandler = httpHandler;
        this.logger = logger;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Process the given GRPC TriggerRequest and return a TriggerResponse object.
     *
     * @param triggerRequest the GRPC TriggerRequest object
     * @return the GRPC TriggerResponse object
     */
    public TriggerResponse process(TriggerRequest triggerRequest) {

        if (triggerRequest.hasHttp()) {
            if (httpHandler == null) {
                throw new IllegalArgumentException("No HttpHandler has been registered");
            }

            var context = toHttpContext(triggerRequest);

            var resultContext = httpHandler.handle(context, null);

            if (resultContext != null) {
                return toHttpTriggerResponse(resultContext.getResponse());

            } else {
                // TODO: log null response
                return TriggerResponse.newBuilder().build();
            }

        } else if (triggerRequest.hasTopic()) {
            if (eventHandler == null) {
                throw new IllegalArgumentException("No EventHandler has been registered");
            }

            var context = toEventContext(triggerRequest);

            var resultContext = eventHandler.handle(context, null);

            if (resultContext != null) {
                return toTopicTriggerResponse(resultContext.getResponse());

            } else {
                // TODO: log null response
                return TriggerResponse.newBuilder().build();
            }

        } else {
            String msg = "Trigger type is not supported: " + triggerRequest;
            throw new UnsupportedOperationException(msg);
        }
    }

    // Package Private Methods ------------------------------------------------

    EventContext toEventContext(TriggerRequest trigger) {
        Contracts.requireNonNull(trigger, "trigger");

        if (!trigger.hasTopic()) {
            throw new IllegalArgumentException("trigger must be Topic type");
        }

        var request = new EventRequest(
            trigger.getTopic().getTopic(),
            trigger.getMimeType(),
            trigger.getData().toByteArray()
        );

        var response = new EventResponse();

        return new EventContext(request, response);
    }

    HttpContext toHttpContext(TriggerRequest trigger) {
        Contracts.requireNonNull(trigger, "trigger");

        if (!trigger.hasHttp()) {
            throw new IllegalArgumentException("trigger must be HTTP type");
        }

        var http = trigger.getHttp();

        Map<String, String> headers = http.getHeadersMap()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().getValue(0)));

        var request = new HttpRequest(
            http.getMethod(),
            http.getPath(),
            headers,
            http.getQueryParamsMap(),
            trigger.getMimeType(),
            trigger.getData().toByteArray()
        );

        var response = new HttpResponse();

        return new HttpContext(request, response);
    }

    TriggerResponse toTopicTriggerResponse(EventResponse response) {
        var trBuilder = TriggerResponse.newBuilder();

        trBuilder.setData(ByteString.copyFrom(response.getData()));

        var topicCtxBuilder = TopicResponseContext.newBuilder().setSuccess(response.isSuccess());

        trBuilder.setTopic(topicCtxBuilder);

        return trBuilder.build();
    }

    TriggerResponse toHttpTriggerResponse(HttpResponse response) {
        var trBuilder = TriggerResponse.newBuilder();

        trBuilder.setData(ByteString.copyFrom(response.getData()));

        var httpCtxBuilder = HttpResponseContext.newBuilder();
        httpCtxBuilder.setStatus(response.getStatus());
        response.getHeaders().entrySet().forEach(e -> {
            httpCtxBuilder.putHeaders(e.getKey(), HeaderValue.newBuilder().addValue(e.getValue()).build());
        });

        trBuilder.setHttp(httpCtxBuilder);

        return trBuilder.build();
    }

}
