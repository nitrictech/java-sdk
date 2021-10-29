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

package io.nitric.faas.event;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import io.nitric.util.Contracts;

/**
 * <p>
 * Provides an Event Context class. The EventContext object is composed of an immutable Request object and a
 * mutable Response object.
 * </p>
 *
 * <p>
 * Function handlers and middleware can set properties and data on the response object during the request cycle.
 * </p>
 *
 * <h3>Handler Example</h3>
 *
 * <p>
 * The example below is creating a new customer document, and specifying the event was successful processed in
 * the context response success status. If an error occurred the response success status is set to false.
 * </p>
 *
 * <pre><code class="code">
 * &#64;Override
 * public EventContext handle(EventContext context) {
 *     try {
 *         var json = context.getRequest().getDataAsText();
 *         var customer = new ObjectMapper().readValue(json, Customer.class);
 *         var id = UUID.randomUUID().toString();
 *
 *         documents.collection("customer")
 *             .doc(id, Customer.class)
 *             .set(customer);
 *
 *         context.getResponse().success(true);
 *
 *     } catch (IOException ioe) {
 *         logger.error(ioe);
 *
 *         context.getResponse()
 *              .success(false)
 *              .text("Error: %s", ioe);
 *     }
 *
 *     return context;
 * }
 * </code></pre>
 *
 * <h3>Unit Test Example</h3>
 *
 * <p>
 *  A EventContext Builder class is also provided to support unit testing of function handlers.
 * </p>
 *
 * <pre><code class="code">
 * var json = "{ \"email\": \"user@server.com\" }";
 *
 * var context = EventContext.newBuilder()
 *     .topic("createCustomer")
 *     .text(json)
 *     .build();
 *
 * var function = new CreateHandler(documents);
 *
 * var ctx = function.handle(context);
 * </code></pre>
 *
 * @see EventHandler
 * @see EventMiddleware
 */
public class EventContext {

    final Request request;
    final Response response;

    // Constructors -----------------------------------------------------------

    /**
     * Provides a Event context object.
     *
     * @param request the event request (required)
     * @param response the event response (required)
     */
    public EventContext(Request request, Response response) {
        Contracts.requireNonNull(request, "request");
        Contracts.requireNonNull(response, "response");

        this.request = request;
        this.response = response;
    }

    /**
     * Provides a copy construct creating a new Event context object from the given context parameter.
     *
     * @param context the context object to copy (required)
     */
    public EventContext(EventContext context) {
        Contracts.requireNonNull(context, "context");

        this.request = context.request;
        this.response = new Response(context.response);
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the Event request object.
     *
     * @return the Event request object
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Return the Event response object.
     *
     * @return the Event response object
     */
    public Response getResponse() {
        return response;
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
            + "[request=" + request
            + ", response=" + response
            + "]";
    }

    /**
     * Create a new EventContext builder object.
     *
     * @return a new EventContext builder object
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    // Inner Classes --------------------------------------------------------------

    /**
     * Provides an Event Builder class.
     */
    public static class Builder {

        String topic;
        String mimeType;
        byte[] data;
        Map<String, Object> extras;

        /**
         * Set the Event request topic.
         *
         * @param topic the event request topic
         * @return this chainable builder object
         */
        public Builder topic(String topic) {
            this.topic = topic;
            return this;
        }

        /**
         * Set the Event request mime-type.
         *
         * @param mimeType the event request  mime-type
         * @return this chainable builder object
         */
        public Builder mimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        /**
         * Set the Event request body data.
         *
         * @param data the Event request body data
         * @return this chainable builder object
         */
        public Builder data(byte[] data) {
            this.data = data;
            return this;
        }

        /**
         * Set the Event request body data with the given text. The text value
         * will be encoded as UTF-8 data.
         *
         * @param text the request body text (required)
         * @return this chainable builder object
         */
        public Builder text(String text) {
            Contracts.requireNonBlank(text, "text");

            this.data = text.getBytes(StandardCharsets.UTF_8);
            return this;
        }

        /**
         * Add an event extras attribute value with the given key. The event extras map
         * provide support for middleware enhancements to the context request.
         *
         * @param key the extras attribute key (required)
         * @param value the extras attribute value (required)
         * @return this chainable builder object
         */
        public Builder addExtras(String key, Object value) {
            Contracts.requireNonBlank(key, "key");
            Contracts.requireNonNull(value, "value");

            if (extras == null) {
                extras = new LinkedHashMap<>();
            }
            extras.put(key, value);

            return this;
        }

        /**
         * Build a new EventContext object.
         *
         * @return a new EventContext object
         */
        public EventContext build() {
            var request = new EventContext.Request(topic, mimeType, data, extras);
            var response = new EventContext.Response();
            return new EventContext(request, response);
        }
    }

    /**
     * Provides a Event request object.
     */
    public static class Request {

        final String topic;
        final String mimeType;
        final byte[] data;
        final Map<String, Object> extras;

        // Constructors -----------------------------------------------------------

        /**
         * Create a new Request object.
         *
         * @param topic the event topic
         * @param data the event request data
         * @param extras the request extra attributes
         */
        public Request(String topic, String mimeType, byte[] data,  Map<String, Object> extras) {
            this.topic = topic;
            this.mimeType = mimeType;
            this.data = data;
            this.extras = (extras != null) ? Collections.unmodifiableMap(extras) : Collections.emptyMap();
        }

        // Public Methods ---------------------------------------------------------

        /**
         * Retrieve the topic name for this trigger
         *
         * @return The name of the topic that raised the trigger
         */
        public String getTopic() {
            return (topic != null) ? topic : "";
        }

        /**
         * Return the trigger mime type, or an empty string if not defined.
         *
         * @return the trigger mime type, or an empty string if not defined.
         */
        public String getMimeType() {
            return (mimeType != null) ? mimeType : "";
        }

        /**
         * Get the trigger data.
         *
         * @return the data of the trigger
         */
        public byte[] getData() {
            return this.data;
        }

        /**
         * Get the trigger data as UTF-8 encode text, or an empty string if not defined.
         *
         * @return the trigger data as UTF-8 encode text, or an empty string if not defined
         */
        public String getDataAsText() {
            return (data != null) ? new String(data, StandardCharsets.UTF_8) : "";
        }

        /**
         * Return the request extras map. The request extras map provides support for custom middleware
         * enhancement to the context request.
         *
         * @return the request extras attributes, or an empty map if not defined
         */
        public Map<String, Object> getExtras() {
            return extras;
        }

        /**
         * Return the string representation of this object.
         *
         * @return the string representation of this object
         */
        @Override
        public String toString() {
            String dataSample = "";
            if (data != null) {
                dataSample = getDataAsText();
                if (dataSample.length() > 40) {
                    dataSample = dataSample.substring(0, 40) + "...";
                }
            }
            return getClass().getSimpleName()
                + "[topic=" + topic
                + ", mimeType=" + mimeType
                + ", data=" + dataSample
                + ", extras=" + extras
                + "]";
        }
    }

    /**
     * Provides a Event response class.
     */
    public static class Response {

        boolean success = true;
        byte[] data;

        // Constructors -----------------------------------------------------------

        /**
         * Create a new Event Response object.
         */
        public Response() {
        }

        /**
         * Create a new Event Response object from the given response.
         */
        public Response(Response response) {
            success = response.isSuccess();
            data = response.data;
        }

        // Public Methods ---------------------------------------------------------

        /**
         * @return The success status of the event processing
         */
        public boolean isSuccess() {
            return success;
        }

        /**
         * Set the status for processing this topic trigger, false will cause the event to be re-queued.
         *
         * @param success the status for processing this topic trigger, false will cause the event to be re-queued
         * @return this chainable Response object
         */
        public Response success(boolean success) {
            this.success = success;
            return this;
        }

        /**
         * Return the response data, or null if not defined.
         *
         * @return the response data, or null if not defined
         */
        public byte[] getData() {
            return data;
        }

        /**
         * Get the data contained in the response as UTF-8 encode text, or null if not define.
         *
         * @return the response data as UTF-8 encoded text, or null if not defined
         */
        public String getDataAsText() {
            return (data != null) ? new String(data, StandardCharsets.UTF_8) : null;
        }

        /**
         * Set the data for this response.
         *
         * @param data The data as an array of bytes
         * @return this chainable Response object
         */
        public Response data(byte[] data) {
            this.data = data;
            return this;
        }

        /**
         * Set the Event response body data with the text value. The text value will be encoded as UTF-8 data.
         *
         * @param text the text value to be encoded UTF-8 data
         * @return this chainable Response object
         */
        public Response text(String text) {
            Contracts.requireNonNull(text, "text");
            this.data = text.getBytes(StandardCharsets.UTF_8);
            return this;
        }

        /**
         * Set the Event response body data with the given text format value and args. This overloaded
         * <code>text()</code> method enables you to easily create formatted response text using:
         * <code>String.format(text, args)</code>
         *
         * @param text the text format (required)
         * @param args the text format arguments (required)
         * @return this chainable Response object
         * @throws java.util.IllegalFormatException if the text format is invalid
         */
        public Response text(String text, Object...args) {
            Contracts.requireNonNull(text, "text");
            Contracts.requireNonNull(args, "args");

            var resp = String.format(text, args);
            this.data = resp.getBytes(StandardCharsets.UTF_8);

            return this;
        }

        /**
         * Return the string representation of this object.
         *
         * @return the string representation of this object
         */
        @Override
        public String toString() {
            String dataSample = "null";
            if (data != null) {
                dataSample = getDataAsText();
                if (dataSample.length() > 40) {
                    dataSample = dataSample.substring(0, 40) + "...";
                }
            }
            return getClass().getSimpleName()
                + "[success=" + success
                + ", data=" + dataSample
                + "]";
        }
    }

}
