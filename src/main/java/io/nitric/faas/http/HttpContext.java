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

import io.nitric.util.Contracts;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Provides an HTTP Context class. The HttpContext object is composed of an immutable Request object and a
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
 * The example below is creating a new customer document, and renders a success message to the HTTP response.
 * If an error occurred a HTTP 500 status code is set an error message is rendered.
 * </p>
 *
 * <pre><code class="code">
 * &#64;Override
 * public HttpContext handle(HttpContext context) {
 *     Stream&lt;ResultDoc&lt;Customer&gt;&gt; stream = documents.collection("customers")
 *         .query(Customer.class)
 *         .stream();
 *
 *     List&lt;Customer&gt; customers = stream
 *          .map(ResultDoc::getContent)
 *          .collect(Collectors.toUnmodifiableList());
 *
 *     try {
 *         var json = new ObjectMapper().writeValueAsString(customers);
 *
 *         context.getResponse()
 *             .contentType("application/json")
 *             .text(json);
 *
 *     } catch (IOException ioe) {
 *         logger.error(ioe);
 *
 *         context.getResponse()
 *             .status(500)
 *             .text("Error querying customers: %s", ioe);
 *     }
 *
 *     return context;
 * }
 * </code></pre>
 *
 * <h3>Unit Test Example</h3>
 *
 * <p>
 *  A HttpContext Builder class is also provided to support unit testing of function handlers.
 * </p>
 *
 * <pre><code class="code">
 * var context = HttpContext.newBuilder()
 *     .method("GET")
 *     .path("/customers")
 *     .build();
 *
 * var function = new ListHandler(documents);
 *
 * var ctx = function.handle(context);
 * </code></pre>
 *
 * @see HttpHandler
 * @see HttpMiddleware
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
     * Provides a copy construct creating a new Event context object from the given context parameter.
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
        Map<String, List<String>> headers = new HashMap<>();
        Map<String, List<String>> queryParams = new HashMap<>();
        String mimeType;
        byte[] data;
        Map<String, String> pathParams;
        Map<String, Object> extras;

        /**
         * Copy the request values.
         *
         * @param request the request values to copy (required)
         * @return this chainable builder object
         */
        public Builder request(Request request) {
            Contracts.requireNonNull(request, "request");

            method = request.method;
            path = request.path;
            headers.putAll(request.getHeaders());
            queryParams.putAll(request.getQueryParams());
            mimeType = request.mimeType;
            data = request.data;
            pathParams = request.pathParams;
            extras = request.extras;
            return this;
        }

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
         * Add a header with the given name and value.
         *
         * @param name the header name to add (required)
         * @param value the header value to add (required)
         * @return this chainable builder object
         */
        public Builder addHeader(String name, String value) {
            Contracts.requireNonBlank(name, "name");
            Contracts.requireNonBlank(value, "value");

            var values = headers.get(name);
            if (values == null) {
                values = new ArrayList<>();
                values.add(value);
                headers.put(name, values);
            } else {
                if (!values.contains(value)) {
                    values.add(value);
                }
            }
            return this;
        }

        /**
         * Add a query parameter with the given name and value.
         *
         * @param name the query parameter name to add (required)
         * @param value the query parameter value to add (required)
         * @return this chainable builder object
         */
        public Builder addQueryParam(String name, String value) {
            Contracts.requireNonBlank(name, "name");
            Contracts.requireNonBlank(value, "value");

            var values = queryParams.get(name);
            if (values == null) {
                values = new ArrayList<>();
                values.add(value);
                queryParams.put(name, values);
            } else {
                if (!values.contains(value)) {
                    values.add(value);
                }
            }
            return this;
        }

        /**
         * Set the HTTP request 'Content-Type' header value and mime-type.
         *
         * @param contentType the HTTP request Content-Type (required)
         * @return this chainable builder object
         */
        public Builder contentType(String contentType) {
            Contracts.requireNonBlank(contentType, "contentType");
            addHeader("Content-Type", contentType);
            this.mimeType = contentType;
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
         * Set the HTTP request body data with the given text. The text value
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
         * Add a URL path parameter with the given name and value. The request pathParams map
         * provide support for middleware enhancements to the context request.
         *
         * @param name the URL path parameter name to add (required)
         * @param value the URL path parameter value to add (required)
         * @return this chainable builder object
         */
        public Builder addPathParam(String name, String value) {
            Contracts.requireNonBlank(name, "name");
            Contracts.requireNonBlank(value, "value");

            if (pathParams == null) {
                pathParams = new LinkedHashMap<>();
            }
            pathParams.put(name, value);

            return this;
        }

        /**
         * Add a request extras attribute value with the given key. The request extras map
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
         * Build a new HttpContext object.
         *
         * @return a new HttpContext object
         */
        public HttpContext build() {
            var request = new HttpContext.Request(
                    method,
                    path,
                    headers,
                    queryParams,
                    mimeType,
                    data,
                    pathParams,
                    extras
            );
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
        final Map<String, List<String>> headers;
        final Map<String, List<String>> queryParams;
        final String mimeType;
        final byte[] data;
        final Map<String, String> pathParams;
        final Map<String, Object> extras;

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
         * @param pathParams the request URL path parameters
         * @param extras the request extra attributes
         */
        public Request(
            String method,
            String path,
            Map<String, List<String>> headers,
            Map<String, List<String>> queryParams,
            String mimeType,
            byte[] data,
            Map<String, String> pathParams,
            Map<String, Object> extras
        ) {
            this.method = method;
            this.path = path;
            this.headers = headers;
            this.queryParams = queryParams;
            this.mimeType = mimeType;
            this.data = data;
            this.pathParams = (pathParams != null) ? Collections.unmodifiableMap(pathParams) : Collections.emptyMap();
            this.extras = (extras != null) ? Collections.unmodifiableMap(extras) : Collections.emptyMap();
        }

        // Public Methods ---------------------------------------------------------

        /**
         * Return the HTTP request method [ GET | POST | PUT | DELETE ].
         *
         * @return the HTTP request method
         */
        public String getMethod() {
            return (method != null) ? method  : "";
        }

        /**
         * Return HTTP request path, or an empty string if not defined.
         *
         * @return HTTP request path, or an empty string if not defined
         */
        public String getPath() {
            return (path != null) ? path : "";
        }

        /**
         * Return the first HTTP header value for the given name, or null not found.
         *
         * @param name the HTTP header name (required)
         * @return the first HTTP header value for the given name, or null not found.
         */
        public String getHeader(String name) {
            Contracts.requireNonBlank(name, "name");

            if (headers != null) {
                List<String> values = headers.get(name);
                return (values != null && values.size() > 0) ? values.get(0) : null;

            } else {
                return null;
            }
        }

        /**
         * Return an immutable map of HTTP request header values. If no headers are present an empty map will be
         * returned.
         *
         * @return map of HTTP request header values
         */
        public Map<String, List<String>> getHeaders() {
            if (headers != null) {
                return Collections.unmodifiableMap(headers);
            } else {
                return Collections.emptyMap();
            }
        }

        /**
         * Return the first HTTP query parameter value for the given name, or null not found.
         *
         * @param name the HTTP query parameter name (required)
         * @return the first HTTP query parameter value for the given name, or null not found.
         */
        public String getQueryParam(String name) {
            Contracts.requireNonBlank(name, "name");

            if (queryParams != null) {
                List<String> values = queryParams.get(name);
                return (values != null && values.size() > 0) ? values.get(0) : null;

            } else {
                return null;
            }
        }

        /**
         * Return an immutable map of HTTP request query parameters. If no parameters are present then an empty map
         * will be returned.
         *
         * @return an immutable map of HTTP request query parameter values
         */
        public Map<String, List<String>> getQueryParams() {
            if (queryParams != null) {
                return Collections.unmodifiableMap(queryParams);
            } else {
                return Collections.emptyMap();
            }
        }

        /**
         * Return the HTTP request MIME Type or "Content-Type" header value, or an empty string if not defined.
         *
         * @return the HTTP request MIME Type or "Content-Type" header value
         */
        public String getMimeType() {
            return (mimeType != null) ? mimeType : "";
        }

        /**
         * Return the HTTP request body data. If no data is present a zero length array will be returned.
         *
         * @return the HTTP request body data
         */
        public byte[] getData() {
            return data;
        }

        /**
         * Return the HTTP request body data (UTF-8 encoded) as text, or an empty string if not defined.
         *
         * @return the HTTP request body data (UTF-8 encoded) as text, or an empty string if not defined.
         */
        public String getDataAsText() {
            return (data != null) ? new String(data, StandardCharsets.UTF_8) : "";
        }

        /**
         * Return the request URL path params map. The request pathParams map provides support for custom
         * middleware enhancements to the context request.
         *
         * @return the request URL path parameters, or an empty map if not defined
         */
        public Map<String, String> getPathParams() {
            return pathParams;
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
                    + "[method=" + method
                    + ", path=" + path
                    + ", headers=" + headers
                    + ", queryParams=" + queryParams
                    + ", mimeType=" + mimeType
                    + ", data=" + dataSample
                    + ", pathParams=" + pathParams
                    + ", extras=" + extras
                    + "]";
        }
    }

    /**
     * Provides a HTTP response class.
     */
    public static class Response {

        int status = 200;
        Map<String, List<String>> headers = new HashMap<>();
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
        public Map<String, List<String>> getHeaders() {
            return this.headers;
        }

        /**
         * Add an HTTP header with the given name and value.
         *
         * @param name the header name to add (required)
         * @param value the header value to add (required)
         * @return this chainable Response object
         */
        public Response addHeader(String name, String value) {
            Contracts.requireNonBlank(name, "name");
            Contracts.requireNonBlank(value, "value");

            var values = headers.get(name);
            if (values == null) {
                values = new ArrayList<>();
                values.add(value);
                headers.put(name, values);
            } else {
                if (!values.contains(value)) {
                    values.add(value);
                }
            }
            return this;
        }

        /**
         * Set the HTTP response headers.
         *
         * @param headers the HTTP response headers (required)
         * @return this chainable Response object
         */
        public Response headers(Map<String, List<String>> headers) {
            Contracts.requireNonNull(headers, "headers");
            this.headers = headers;
            return this;
        }

        /**
         * Set the HTTP request 'Content-Type' header value.
         *
         * @param contentType the HTTP Content-Type header value (required)
         * @return this chainable Response object
         */
        public Response contentType(String contentType) {
            Contracts.requireNonBlank(contentType, "contentType");
            addHeader("Content-Type", contentType);
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
         * Get the data (UTF-8 encoded) contained in the response as text, or null if not define.
         *
         * @return the response data (UTF-8 encoded) as text, or null if not defined
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
         * Set the HTTP response body data with the text value. The text value will be encoded as UTF-8 data.
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
         * Set the HTTP response body data with the given text format value and args. This overloaded
         * <code>text()</code> method enabled you to easily create formatted response text using:
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
