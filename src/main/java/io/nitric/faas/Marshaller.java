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

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.protobuf.ByteString;

import io.nitric.faas.event.EventContext;
import io.nitric.faas.http.HttpContext;
import io.nitric.proto.faas.v1.HeaderValue;
import io.nitric.proto.faas.v1.HttpResponseContext;
import io.nitric.proto.faas.v1.TopicResponseContext;
import io.nitric.proto.faas.v1.TriggerRequest;
import io.nitric.proto.faas.v1.TriggerResponse;
import io.nitric.util.Contracts;

/**
 * Provides GRPC, Event and HTTP object marshalling functions.
 */
public class Marshaller {

    /*
     * Enforce static usage.
     */
    private Marshaller() {
    }

    /**
     * Create a new EventContext from the given GRPC Topic TriggerRequest.
     *
     * @param trigger the Topic TriggerRequest (required)
     * @return a new EventContext object
     */
    public static EventContext toEventContext(TriggerRequest trigger) {
        Contracts.requireNonNull(trigger, "trigger");

        if (!trigger.hasTopic()) {
            throw new IllegalArgumentException("trigger must be Topic type");
        }

        var request = new EventContext.Request(
            trigger.getTopic().getTopic(),
            trigger.getMimeType(),
            trigger.getData().toByteArray(),
            null
        );

        var response = new EventContext.Response();

        return new EventContext(request, response);
    }

    /**
     * Create a new HttpContext from the given GRPC HTTP TriggerRequest.
     *
     * @param trigger the HTTP TriggerRequest (required)
     * @return a new HttpContext object
     */
    public static HttpContext toHttpContext(TriggerRequest trigger) {
        Contracts.requireNonNull(trigger, "trigger");

        if (!trigger.hasHttp()) {
            throw new IllegalArgumentException("trigger must be HTTP type: " + trigger);
        }

        var http = trigger.getHttp();

        // Marshall Proto HeaderValue into List<String> header values
        final Map<String, List<String>> headers = new HashMap<>();
        http.getHeadersMap().forEach((name, headerValue) -> {
            List<String> valueList = headers.get(name);
            if (valueList == null) {
                valueList = new ArrayList<>();
                headers.put(name, valueList);
            }
            for (int i = 0; i < headerValue.getValueCount(); i++) {
                String value = headerValue.getValue(i);
                if (!valueList.contains(value)) {
                    valueList.add(value);
                }
            }
        });

        // Marshall Proto QueryParams into List<String>
        final Map<String, List<String>> queryParams = new HashMap<>();
        http.getQueryParamsMap().forEach((name, queryValue) -> {
            var valueList = queryValue.getValueList().stream().collect(Collectors.toList());
            queryParams.put(name, valueList);
        });

        var request = new HttpContext.Request(
                http.getMethod(),
                http.getPath(),
                headers,
                queryParams,
                trigger.getMimeType(),
                trigger.getData().toByteArray(),
                null,
                null
        );

        var response = new HttpContext.Response();

        return new HttpContext(request, response);
    }

    /**
     * Create a new GRPC Topic TriggerResponse from the given EventContext response.
     *
     * @param response the EventContext response (required)
     * @return new GRPC Topic TriggerResponse
     */
    public static TriggerResponse toTopicTriggerResponse(EventContext.Response response) {
        Contracts.requireNonNull(response, "response");

        var trBuilder = TriggerResponse.newBuilder();

        if (response.getData() != null) {
            trBuilder.setData(ByteString.copyFrom(response.getData()));
        }

        var topicCtxBuilder = TopicResponseContext.newBuilder().setSuccess(response.isSuccess());

        trBuilder.setTopic(topicCtxBuilder);

        return trBuilder.build();
    }

    /**
     * Create a new GRPC HTTP TriggerResponse from the given HttpContext.Response object.
     *
     * @param response the HttpContext response (required)
     * @return new GRPC HTTP TriggerResponse
     */
    public static TriggerResponse toHttpTriggerResponse(HttpContext.Response response) {
        var trBuilder = TriggerResponse.newBuilder();

        if (response.getData() != null) {
            trBuilder.setData(ByteString.copyFrom(response.getData()));
        }

        var httpCtxBuilder = HttpResponseContext.newBuilder();
        httpCtxBuilder.setStatus(response.getStatus());
        response.getHeaders().forEach((name, valueList) -> {
            var hvBuilder = HeaderValue.newBuilder();
            valueList.forEach(value -> hvBuilder.addValue(value));
            httpCtxBuilder.putHeaders(name, hvBuilder.build());
        });

        trBuilder.setHttp(httpCtxBuilder);

        return trBuilder.build();
    }

}