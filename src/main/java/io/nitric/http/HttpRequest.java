package io.nitric.http;

/*-
 * #%L
 * Nitric Java SDK
 * %%
 * Copyright (C) 2021 Nitric Pty Ltd
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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Provides an immutable HTTP request class.
 * </p>
 *
 * <p>
 *  The example bellow illustrates using the request object for debugging.
 * </p>
 *
 * ```java
 * import io.nitric.http.HttpHandler;
 * import io.nitric.http.HttpRequest;
 * import io.nitric.http.HttpResponse;
 * import io.nitric.http.HttpServer;
 *
 *
 * public class RequestInfo {
 *
 *     public static void main(String... args) {
 *
 *         new HttpServer().start((HttpRequest r) -> {
 *             var logger = System.out;
 *
 *             logger.printf("request: \n");
 *             logger.printf("   .method: %s \n", r.getMethod());
 *             logger.printf("   .path: %s \n", r.getPath());
 *
 *             logger.printf("   .headers: \n");
 *             for (String name : r.getHeaders().keySet()) {
 *                 logger.printf("      %s: %s \n", name, r.getHeaders().get(name));
 *             }
 *
 *             logger.printf("   .parameters: \n");
 *             for (String name : r.getParameters().keySet()) {
 *                 logger.printf("      %s: %s \n", name, r.getParameters().get(name));
 *
 *             }
 *
 *             logger.printf("   .body: \n");
 *             logger.printf("      %s \n", r.getBodyText());
 *
 *             return HttpResponse.build(200);
 *         });
 *     }
 * }
 * ```
 *
 * @see HttpHandler
 * @see HttpResponse
 * @see HttpResponse.Builder
 *
 * @since 1.0
 */
public class HttpRequest {

    static final String CONTENT_TYPE = "Content-Type";

    final String method;
    final String path;
    final String query;
    final Map<String, List<String>> headers;
    final byte[] body;
    final Map<String, List<String>> parameters;

    /*
     * Private constructor to enforce request builder pattern.
     */
    private HttpRequest(Builder builder) {
        this.method = builder.method;
        this.path = builder.path;
        this.query = builder.query;
        this.headers = Collections.unmodifiableMap(builder.headers);
        this.body = builder.body;
        this.parameters = Collections.unmodifiableMap(builder.parameters);
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return the HTTP operation method [ GET, POST, PUT, DELETE ].
     */
    public String getMethod() {
        return method;
    }

    /**
     * @return the request path or null if not defined.
     */
    public String getPath() {
        return path;
    }

    /**
     * @return the GET request query string value.
     */
    public String getQuery() {
        return query;
    }

    /**
     * @return an immutable map of HTTP request headers, an empty map will be returned if there are no headers.
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * Return the named HTTP header or null if not found. If the header has multiple values the first value will be
     * returned. Please note Nitric headers are case-insensitive
     *
     * @param name the name of the HTTP header
     * @return the named HTTP header or null if not found
     */
    public String getHeader(String name) {
        return Builder.getHeaderValue(name, headers);
    }

    /**
     * @return the request body
     */
    public byte[] getBody() {
        return body;
    }

    /**
     * @return the request body as text (UTF-8 encoded)
     */
    public String getBodyText() {
        return (body != null) ? new String(body, StandardCharsets.UTF_8) : null;
    }

    /**
     * Return the 'Content-Type' header value, or the MIME type of the request body. If the 'Content-Type' header
     * is not defined this method will return null.
     *
     * @return the 'Content-Type' header value, or null if the not defined.
     */
    public String getContentType() {
        return Builder.getHeaderValue(CONTENT_TYPE, headers);
    }

    /**
     * <p>
     *  Return map of request parameter values, parsed from GET URL query or POST form encoded values Note an empty
     *  empty map will be returned if there are no headers.
     * </p>
     * <p>
     *  Note this method does currently support parsing parameters with form 'multipart/form-data' post requests.
     * </p>
     *
     * @return map of request parameter values, or an empty map one are defined.
     */
    public Map<String, List<String>> getParameters() {
        return parameters;
    }

    /**
     * Return the first parameter value defined for the given name, or null if not found.
     * Note this method does currently support form 'multipart/form-data' post requests.
     *
     * @param name the parameter name
     * @return first parameter value defined for the given name, or null if not found
     */
    public String getParameter(String name) {
        if (parameters.containsKey(name)) {
            return parameters.get(name).get(0);
        } else {
            return null;
        }
    }

    /**
     * @return the string representation of this object
     */
    public String toString() {
        return getClass().getSimpleName()
                + "[method=" + method
                + ", path=" + path
                + ", query=" + query
                + ", headers=" + headers
                + ", parameters=" + parameters
                + ", body.length=" + ((body != null) ? body.length : 0)
                + "]";
    }

    /**
     * @return a new HttpRequest builder class.
     */
    public static HttpRequest.Builder newBuilder() {
        return new HttpRequest.Builder();
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a Nitric request builder class.
     *
     * @since 1.0
     */
    public static class Builder {

        static final String FORM_URL_ENCODED = "application/x-www-form-urlencoded";

        String method;
        String path;
        String query;
        Map<String, List<String>> headers;
        Map<String, List<String>> parameters;
        byte[] body;

        /*
         * Package Private constructor to enforce request builder pattern.
         */
        Builder() {
        }

        /**
         * Set the request method, for example with HTTP this would be ["GET" | "POST" | "PUT" | "DELETE" ].
         *
         * @param method the request method
         * @return the request builder instance
         */
        public Builder method(String method) {
            this.method = method;
            return this;
        }

        /**
         * Set the request path.
         *
         * @param path the request path
         * @return the request builder instance
         */
        public Builder path(String path) {
            this.path = path;
            return this;
        }

        /**
         * Set the request URL query.
         *
         * @param query the request URL query
         * @return the request builder instance
         */
        public Builder query(String query) {
            this.query = query;
            return this;
        }

        /**
         * Set the request headers.
         *
         * @param headers the request headers
         * @return the request builder instance
         */
        public Builder headers(Map<String, List<String>> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Set the request body.
         *
         * @param body the request body
         * @return the request builder instance.
         */
        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        /**
         * @return a new HTTP response.
         */
        public HttpRequest build() {
            headers = (headers != null) ? headers : Collections.emptyMap();

            // Parse parameters
            String urlParameters = null;
            if ("POST".equalsIgnoreCase(method)) {
                var contentType = getHeaderValue(CONTENT_TYPE, headers);

                if (body != null && body.length > 0 && FORM_URL_ENCODED.equalsIgnoreCase(contentType)) {
                    urlParameters = URLDecoder.decode(new String(body, StandardCharsets.UTF_8), StandardCharsets.UTF_8);
                }

            } else if (query != null) {
                urlParameters = query;
            }

            parameters = parseParameters(urlParameters);

            return new HttpRequest(this);
        }

        // Package Private Methods --------------------------------------------

        Map<String, List<String>> parseParameters(String urlParameters) {
            Map<String, List<String>> params = new HashMap<>();

            if (urlParameters != null && urlParameters.length() > 2) {
                String[] urlParams = urlParameters.split("&");

                for (String param : urlParams) {
                    var paramArray = param.split("=");

                    if (paramArray.length > 1) {
                        String name = paramArray[0];
                        String value = paramArray[1];
                        var valueList = params.get(name);
                        if (params.get(name) == null) {
                            valueList = new ArrayList<>(2);
                            params.put(name, valueList);
                        }
                        valueList.add(value);
                    }
                }
            }

            return params;
        }

        static String getHeaderValue(String name, Map<String, List<String>> headers) {
            if (headers == null || headers.isEmpty()) {
                return null;
            }

            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(name)) {
                    return entry.getValue().get(0);
                }
            }

            return null;
        }
    }

}
