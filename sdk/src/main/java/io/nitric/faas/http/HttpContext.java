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

package io.nitric.faas.http;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.nitric.util.Contracts;

/**
 * Provides a Http Context object.
 */
public class HttpContext {

    final Request request;
    final Response response;

    // Constructors -----------------------------------------------------------

    /**
     * Provides a HTTP context object.
     *
     * @param request the HTTP request (required)
     * @param response the HTTP response (required)
     */
    public HttpContext(Request request, Response response) {
        Contracts.requireNonNull(request, "request");
        Contracts.requireNonNull(response, "response");

        this.request = request;
        this.response = response;
    }

    /**
     * Create a new HTTP context object from the given context.
     *
     * @param context the HTTP context object to copy (required)
     */
    public HttpContext(HttpContext context) {
        Contracts.requireNonNull(context, "context");

        this.request = context.request;
        this.response = new Response(context.response);
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the HTTP request object.
     *
     * @return the HTTP request object
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Return the HTTP response object.
     *
     * @return the HTTP response object
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
     * Return a new HttpContext builder.
     *
     * @return a new HttpContext builder
     */
    public static Builder newBuilder() {
        return new HttpContext.Builder();
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a HttpContext builder class.
     */
    public static class Builder {

        String method;
        String path;
        Map<String, String> headers;
        Map<String, String> queryParams;
        String mimeType;
        byte[] data;

        /**
         * Set the HTTP request method [ GET | POST | DELETE | PUT ].
         *
         * @param method The HTTP request method [ GET | POST | DELETE | PUT ]
         * @return this chainable builder object
         */
        public Builder method(String method) {
            this.method = method;
            return this;
        }

        /**
         * Set the HTTP request path.
         *
         * @param path the HTTP request path
         * @return this chainable builder object
         */
        public Builder path(String path) {
            this.path = path;
            return this;
        }

        /**
         * Set the HTTP request headers.
         *
         * @param headers the HTTP request headers
         * @return this chainable builder object
         */
        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Set the HTTP request query parameters.
         *
         * @param queryParams the HTTP request query parameters
         * @return this chainable builder object
         */
        public Builder queryParams(Map<String, String> queryParams) {
            this.queryParams = queryParams;
            return this;
        }

        /**
         * Set the HTTP request mime-type.
         *
         * @param mimeType the HTTP request mime-type
         * @return this chainable builder object
         */
        public Builder mimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        /**
         * Set the HTTP request body data.
         *
         * @param data the HTTP request body data
         * @return this chainable builder object
         */
        public Builder data(byte[] data) {
            this.data = data;
            return this;
        }

        /**
         * Set the HTTP request body data as text
         *
         * @param data the HTTP request body data (required)
         * @return this chainable builder object
         */
        public Builder data(String data) {
            Contracts.requireNonBlank(data, "data");

            this.data = data.getBytes(StandardCharsets.UTF_8);
            return this;
        }

        /**
         * Build a new HttpContext object.
         *
         * @return a new HttpContext object
         */
        public HttpContext build() {
            var request = new HttpContext.Request(method, path, headers, queryParams, mimeType, data);
            var response = new HttpContext.Response();
            return new HttpContext(request, response);
        }
    }

    /**
     * Provides a HTTP request object.
     */
    public static class Request {

        final String method;
        final String path;
        final Map<String, String> headers;
        final Map<String, String> queryParams;
        final String mimeType;
        final byte[] data;

        // Constructors -----------------------------------------------------------

        /**
         * Create a new Request object.
         *
         * @param method the method of the HTTP request
         * @param path the path of the HTTP request
         * @param headers the headers of the HTTP request
         * @param queryParams the query parameters of the HTTP request
         * @param mimeType the request mime-type
         * @param data the HTTP body data
         */
        public Request(
            String method,
            String path,
            Map<String, String> headers,
            Map<String, String> queryParams,
            String mimeType,
            byte[] data
        ) {
            this.method = method;
            this.path = path;
            this.headers = headers;
            this.queryParams = queryParams;
            this.mimeType = mimeType;
            this.data = data;
        }

        // Public Methods ---------------------------------------------------------

        /**
         * @return The method of the HTTP Request that raised this trigger
         */
        public String getMethod() {
            return method;
        }

        /**
         * @return The path of the HTTP Request that raised this trigger
         */
        public String getPath() {
            return path;
        }

        /**
         * @return The headers of the HTTP Request that raised this trigger
         */
        public Map<String, String> getHeaders() {
            if (headers != null) {
                return Collections.unmodifiableMap(headers);
            } else {
                return Collections.emptyMap();
            }
        }

        /**
         * @return The query parameters of the HTTP Request that raised this trigger
         */
        public Map<String, String> getQueryParams() {
            if (queryParams != null) {
                return Collections.unmodifiableMap(queryParams);
            } else {
                return Collections.emptyMap();
            }
        }

        /**
         * Return the trigger mime type.
         *
         * @return the trigger mime type
         */
        public String getMimeType() {
            return mimeType;
        }

        /**
         * Get the trigger data.
         *
         * @return the data of the trigger
         */
        public byte[] getData() {
            return data;
        }

        /**
         * Get the trigger data as UTF-8 encode text, or null if not defined.
         *
         * @return the trigger data as UTF-8 encode text, or null if not defined
         */
        public String getDataAsText() {
            return (getData() != null) ? new String(getData(), StandardCharsets.UTF_8) : null;
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
                    + "[method=" + method
                    + ", path=" + path
                    + ", headers=" + headers
                    + ", queryParams" + queryParams
                    + ", mimeType=" + mimeType
                    + ", data=" + dataSample
                    + "]";
        }
    }

    /**
     * Provides a HTTP response class.
     */
    public static class Response {

        int status = 200;
        Map<String, String> headers = new HashMap<>();
        byte[] data;

        // Constructors -----------------------------------------------------------

        /**
         * Create a new Response object.
         */
        public Response() {
        }

        /**
         * Create a new Response object copied from the given response.
         *
         * @param response the Response object to clone (required)
         */
        public Response(Response response) {
            Contracts.requireNonNull(response, "response");

            this.status = response.status;
            this.headers = new HashMap<>(response.headers);
            this.data = response.data;
        }

        // Public Methods ---------------------------------------------------------

        /**
         * Return the HTTP response status code.
         *
         * @return the HTTP response status code
         */
        public int getStatus() {
            return status;
        }

        /**
         * Set the HTTP response status code.
         *
         * @param status the HTTP response status code
         * @return this chainable Response object
         */
        public Response status(int status) {
            this.status = status;
            return this;
        }

        /**
         * Return the map of HTTP response headers.
         *
         * @return the map of HTTP response headers
         */
        public Map<String, String> getHeaders() {
            return this.headers;
        }

        /**
         * Add a header with the given name and value.
         *
         * @param name the header name to add (required)
         * @param value the header value to add (required)
         * @return this chainable Response object
         */
        public Response addHeader(String name, String value) {
            Contracts.requireNonBlank(name, "name");
            Contracts.requireNonBlank(value, "value");

            headers.put(name, value);
            return this;
        }

        /**
         * Set the HTTP response headers.
         *
         * @param headers the HTTP response headers
         * @return this chainable Response object
         */
        public Response headers(Map<String, String> headers) {
            this.headers = headers;
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
         * Set the HTTP response body data.
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
         * @return this HttpResponse object for chaining
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
                    dataSample = dataSample.substring(0, 42) + "...";
                }
            }
            return getClass().getSimpleName()
                + "[status=" + status
                + ", headers=" + headers
                + ", data=" + dataSample
                + "]";
        }
    }

}
