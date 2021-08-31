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

package io.nitric.mock.faas;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.ByteString;

import io.nitric.faas.AbstractTriggerContext;
import io.nitric.faas.Response;
import io.nitric.faas.Trigger;
import io.nitric.proto.faas.v1.HttpTriggerContext;
import io.nitric.proto.faas.v1.TopicTriggerContext;
import io.nitric.proto.faas.v1.TriggerRequest;
import io.nitric.util.Contracts;

/**
 * <p>
 *  Provides a Mock Trigger class for function unit testing.
 * </p>
 *
 * <p>
 *  The example below illustrates the Mock API.
 * </p>
 *
 * <pre><code class="code">
 * import io.nitric.mock.faas.MockTrigger;
 * import org.junit.Test;
 * import java.util.Map;
 *
 * import static org.junit.Assert.*;
 * ...
 *
 * &commat;Test
 * public void test_user_not_found() {
 *
 *     final var expectHeaders = Map.of("Content-Type", "application/json");
 *
 *     var trigger = MockTrigger.newHttpTriggerBuilder()
 *         .setMethod("GET")
 *         .setPath("/user/")
 *         .setQueryParams(Map.of("id", "123456"))
 *         .build();
 *
 *     // Test User Not Found
 *     var response = new GetUserHandler().handle(trigger);
 *     assertNotNull(response);
 *     assertEquals("{ \"message\": \"User '123456' not found\" }", response.getDataAsText());
 *
 *     var httpCtx = response.getContext().asHttp();
 *     assertEquals(404, httpCtx.getStatus());
 *     assertEquals(expectHeaders, httpCtx.getHeaders());
 * }
 * </code></pre>
 *
 * @see Trigger
 * @see Response
 */
public class MockTrigger implements Trigger {

    private final byte[] data;
    private final String mimeType;
    private final AbstractTriggerContext context;

    // Constructors -----------------------------------------------------------

    /*
     * Enforce builder pattern.
     */
    MockTrigger(byte[] data, String mimeType, AbstractTriggerContext context) {
        this.data = data;
        this.mimeType = mimeType;
        this.context = context;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return Retrieves the context that raised the trigger
     */
    @Override
    public AbstractTriggerContext getContext() {
        return this.context;
    }

    /**
     * @return Retrieve the data of the trigger
     */
    @Override
    public byte[] getData() {
        return this.data;
    }

    /**
     * @return Retrieve the mimeType of the trigger
     */
    @Override
    public String getMimeType() {
        return this.mimeType;
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        String dataSample = "null";
        if (getData() != null) {
            dataSample = new String(getData(), StandardCharsets.UTF_8);
            if (dataSample.length() > 40) {
                dataSample = dataSample.substring(0, 42) + "...";
            }
        }
        return getClass().getSimpleName()
            + "[context=" + getContext()
            + ", mimeType=" + getMimeType()
            + ", data=" + dataSample
            + "]";
    }

    /**
     * Return a new Mock HTTP Trigger builder.
     *
     * @return a new Mock HTTP Trigger builder
     */
    public static HttpTriggerBuilder newHttpTriggerBuilder() {
        return new HttpTriggerBuilder();
    }

    /**
     * Return a new Mock Topic Trigger builder.
     *
     * @return a new Mock Topic Trigger builder
     */
    public static TopicTriggerBuilder newTopicTriggerBuilder() {
        return new TopicTriggerBuilder();
    }

    // Protected Methods ------------------------------------------------------

    /**
     * Provides a Mock HTTP Trigger builder class.
     */
    public static class HttpTriggerBuilder {

        String method;
        String path;
        Map<String, String> headers = new HashMap<>();
        Map<String, String> queryParams = new HashMap<>();
        String mimeType;
        byte[] data;

        /*
         * Enforce builder pattern.
         */
        HttpTriggerBuilder() {
        }

        /**
         * Set the Trigger HTTP Method.
         *
         * @param method the trigger HTTP Method.
         * @return this builder object
         */
        public HttpTriggerBuilder setMethod(String method) {
            this.method = method;
            return this;
        }

        /**
         * Set the Trigger HTTP URL path.
         *
         * @param path the trigger HTTP URL path.
         * @return this builder object
         */
        public HttpTriggerBuilder setPath(String path) {
            this.path = path;
            return this;
        }

        /**
         * Add a Trigger HTTP header value.
         *
         * @param name the trigger header name (required)
         * @param value the trigger header value (required)
         * @return this builder object
         */
        public HttpTriggerBuilder addHeader(String name, String value) {
            Contracts.requireNonBlank(name, "name");
            Contracts.requireNonBlank(value, "value");

            headers.put(name, value);
            return this;
        }

        /**
         * Set the Trigger HTTP headers.
         *
         * @param headers the trigger HTTP headers (required).
         * @return this builder object
         */
        public HttpTriggerBuilder setHeaders(Map<String, String> headers) {
            Contracts.requireNonNull(headers, "headers");
            this.headers = headers;
            return this;
        }

        /**
         * Add a Trigger HTTP query parameter.
         *
         * @param name the trigger HTTP query parameter name (required)
         * @param value the trigger HTTP query parameter value (required)
         * @return this builder object
         */
        public HttpTriggerBuilder addQueryParam(String name, String value) {
            Contracts.requireNonBlank(name, "name");
            Contracts.requireNonBlank(value, "value");

            queryParams.put(name, value);
            return this;
        }

        /**
         * Set the Trigger HTTP query parameter.
         *
         * @param queryParams the trigger HTTP query parameters (required).
         * @return this builder object
         */
        public HttpTriggerBuilder setQueryParams(Map<String, String> queryParams) {
            Contracts.requireNonNull(queryParams, "queryParams");
            this.queryParams = queryParams;
            return this;
        }

        /**
         * Set the Trigger request MIME type.
         *
         * @param mimeType the trigger request MIME type.
         * @return this builder object
         */
        public HttpTriggerBuilder setMimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        /**
         * Set the Trigger request data.
         *
         * @param data the trigger request data.
         * @return this builder object
         */
        public HttpTriggerBuilder setData(byte[] data) {
            this.data = data;
            return this;
        }

        /**
         * Set the Trigger request data as text.
         *
         * @param text the trigger request text data.
         * @return this builder object
         */
        public HttpTriggerBuilder setDataAsText(String text) {
            this.data = text.getBytes(StandardCharsets.UTF_8);
            return this;
        }

        /**
         * Build a new Mock HTTP Trigger object.
         *
         * @return a new Mock HTTP Trigger object
         */
        public Trigger build() {
            var protoContextBuilder = HttpTriggerContext.newBuilder();
            if (method != null) {
                protoContextBuilder.setMethod(method);
            }
            if (path != null) {
                protoContextBuilder.setPath(path);
            }
            if (!headers.isEmpty()) {
                protoContextBuilder.putAllHeaders(headers);
            }
            if (!queryParams.isEmpty()) {
                protoContextBuilder.putAllQueryParams(queryParams);
            }

            var protoTriggerRequestBuilder = TriggerRequest.newBuilder();
            if (data != null) {
                protoTriggerRequestBuilder.setData(ByteString.copyFrom(data));
            }
            if (mimeType != null) {
                protoTriggerRequestBuilder.setMimeType(mimeType);
            }
            protoTriggerRequestBuilder.setHttp(protoContextBuilder);

            var triggerRequest = protoTriggerRequestBuilder.build();

            var triggerContext = new MockHttpTriggerContext(
                triggerRequest.getHttp().getMethod(),
                triggerRequest.getHttp().getPath(),
                triggerRequest.getHttp().getHeadersMap(),
                triggerRequest.getHttp().getQueryParamsMap()
            );

            return new MockTrigger(
                 triggerRequest.getData().toByteArray(),
                 triggerRequest.getMimeType(),
                 triggerContext
            );
        }
    }

    /**
     * Provides a Mock Topic Trigger builder class.
     */
    public static class TopicTriggerBuilder {

        String topic;
        String mimeType;
        byte[] data;

        /*
         * Enforce builder pattern.
         */
        TopicTriggerBuilder() {
        }

        /**
         * Set the Trigger request MIME type.
         *
         * @param mimeType the trigger request MIME type.
         * @return this builder object
         */
        public TopicTriggerBuilder setMimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        /**
         * Add a Trigger Topic name. If a Topic name is specified then an
         * Topic trigger context will be created for the MockTrigger.
         *
         * @param topic the trigger topic name (required)
         * @return this builder object
         */
        public TopicTriggerBuilder setTopic(String topic) {
            Contracts.requireNonBlank(topic, "topic");

            this.topic = topic;
            return this;
        }

        /**
         * Set the Trigger request data.
         *
         * @param data the trigger request data.
         * @return this builder object
         */
        public TopicTriggerBuilder setData(byte[] data) {
            this.data = data;
            return this;
        }

        /**
         * Set the Trigger request data as text.
         *
         * @param text the trigger request text data.
         * @return this builder object
         */
        public TopicTriggerBuilder setDataAsText(String text) {
            this.data = text.getBytes(StandardCharsets.UTF_8);
            return this;
        }

        /**
         * Build a new Mock Trigger object.
         *
         * @return a new Mock Trigger object
         */
        public Trigger build() {
            var protoContextBuilder = TopicTriggerContext.newBuilder().setTopic(topic);

            var protoTriggerRequestBuilder = TriggerRequest.newBuilder();
            if (data != null) {
                protoTriggerRequestBuilder.setData(ByteString.copyFrom(data));
            }
            if (mimeType != null) {
                protoTriggerRequestBuilder.setMimeType(mimeType);
            }
            protoTriggerRequestBuilder.setTopic(protoContextBuilder);

            var triggerRequest = protoTriggerRequestBuilder.build();

            var triggerContext = new MockTopicTriggerContext(topic);

            return new MockTrigger(
                    triggerRequest.getData().toByteArray(),
                    triggerRequest.getMimeType(),
                    triggerContext
            );
        }
    }

    /**
     * Provides a Mock HttpRequestTriggerContext for unit testing.
     */
    static class MockHttpTriggerContext extends io.nitric.faas.HttpTriggerContext {

        /**
         * Create a new Mock HttpRequestTriggerContext.
         *
         * @param method The method of the HTTP request for the raised trigger
         * @param path The path of the HTTP request for the raised trigger
         * @param headers The headers of the HTTP request for the raised trigger
         * @param queryParams The query parameters of the HTTP request for the raised trigger
         */
        protected MockHttpTriggerContext(
            String method,
            String path,
            Map<String, String> headers,
            Map<String, String> queryParams
        ) {
            super(method, path, headers, queryParams);
        }
    }

    /**
     * Provides a Mock MockTopicTriggerContext for unit testing.
     */
    static class MockTopicTriggerContext extends io.nitric.faas.TopicTriggerContext {

        /**
         * Creates a new TopicTriggerContext
         *
         * @param topic The name of the topic that raised the trigger
         */
        protected MockTopicTriggerContext(String topic) {
            super(topic);
        }
    }

}