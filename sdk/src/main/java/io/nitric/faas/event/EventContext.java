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

import io.nitric.util.Contracts;

/**
 * Provides a Event Context object.
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
     * Create a new EventContext object from the given context.
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
         * Set the Event request body data as text.
         *
         * @param data the Event request body data (required)
         * @return this chainable builder object
         */
        public Builder data(String data) {
            Contracts.requireNonBlank(data, "data");

            this.data = data.getBytes(StandardCharsets.UTF_8);
            return this;
        }

        /**
         * Build a new EventContext object.
         *
         * @return a new EventContext object
         */
        public EventContext build() {
            var request = new EventContext.Request(topic, mimeType, data);
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

        // Constructors -----------------------------------------------------------

        /**
         * Create a new Request object.
         *
         * @param topic the event topic
         * @param data the event request data
         */
        public Request(String topic, String mimeType, byte[] data) {
            this.topic = topic;
            this.mimeType = mimeType;
            this.data = data;
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
                + "]";
        }
    }

    /**
     * Provides a Event response class.
     */
    public static class Response {

        boolean success;
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
         * Set the data for this response as UTF-8 encoded text.
         *
         * @param text the UTF-8 encode text to set as the data
         * @return this chainable Response object
         */
        public Response data(String text) {
            Contracts.requireNonNull(text, "text");
            this.data = text.getBytes(StandardCharsets.UTF_8);
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
