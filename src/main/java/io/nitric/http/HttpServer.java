package io.nitric.http;

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

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * <p>
 *  Provides a Nitric HTTP Handler server.
 * </p>
 *
 * <p>
 *  The example below starts a HTTP server with a Hello World handler for the path "/".
 * </p>
 *
 * <pre><code class="code">
 * package com.example;
 *
 * import io.nitric.http.HttpHandler;
 * import io.nitric.http.HttpRequest;
 * import io.nitric.http.HttpResponse;
 * import io.nitric.http.HttpServer;
 *
 * public class HelloWorld implements HttpHandler {
 *
 *     public HttpResponse handle(HttpRequest request) {
 *         return HttpResponse.build("Hello World");
 *     }
 *
 *     public static void main(String... args) {
 *         new HttpServer().start(new HelloWorld());
 *     }
 * }
 * </code></pre>
 *
 * @see HttpHandler
 */
public class HttpServer {

    static final String DEFAULT_HOSTNAME = "127.0.0.1";

    String hostname = DEFAULT_HOSTNAME;
    int port = 8080;
    final Map<String, HttpHandler> pathFunctions = new LinkedHashMap<>();
    com.sun.net.httpserver.HttpServer httpServer;

    // Public Methods ---------------------------------------------------------

    /**
     * Set the server hostname. The default hostname is '127.0.0.1'. You can also change
     * the hostname by setting a 'CHILD_ADDRESS' environment variable which will be used
     * at startup to override the default.
     *
     * @param hostname the server hostname
     * @return the HttpServer instance
     */
    public HttpServer hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    /**
     * Set the server port. The default port is 8080.
     *
     * @param port the server port
     * @return the HttpServer instance
     */
    public HttpServer port(int port) {
        this.port = port;
        return this;
    }

    /**
     * Register the function for the given path.
     *
     * @param path the function route path (required)
     * @param function the function to register (required)
     * @return the HttpServer instance
     */
    public HttpServer register(String path, HttpHandler function) {
        Objects.requireNonNull(path, "null path parameter");
        Objects.requireNonNull(function, "null function parameter");

        var checkHandler = pathFunctions.get(path);
        if (checkHandler != null) {
            var msg = checkHandler.getClass().getName() + " already registered for path: " + path;
            throw new IllegalArgumentException(msg);
        }

        pathFunctions.put(path, function);
        return this;
    }

    /**
     * Start the FaaS server after configuring the given function  to the path "/".
     *
     * @param function the function (required)
     */
    public void start(HttpHandler function) {
        register("/", function);
        start();
    }

    /**
     * Start the function server.
     */
    public void start() {
        if (httpServer != null) {
            throw new IllegalStateException("server already started");
        }

        var childAddress = System.getenv("CHILD_ADDRESS");
        if (childAddress != null && !childAddress.isBlank()) {
            hostname = childAddress;
        }

        try {
            httpServer = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(hostname, port), 0);

            for (String path : pathFunctions.keySet()) {
                var function = pathFunctions.get(path);
                httpServer.createContext(path, buildServerHandler(function));
            }

            httpServer.setExecutor(null);

            // Perform immediate shutdown to load the Java CDS cache during Docker build
            if ("true".equalsIgnoreCase(System.getProperty("shutdown"))) {
                System.out.println(getClass().getSimpleName() + " immediate shutdown");
                httpServer.stop(0);
                return;
            }

            // Start the server
            httpServer.start();

            var builder = new StringBuilder().append(getClass().getSimpleName());
            if (DEFAULT_HOSTNAME.equals(hostname)) {
                builder.append(" listening on port ").append(port);

            } else {
                builder.append(" listening on ").append(hostname).append(":").append(port);
            }

            if (pathFunctions.size() == 0) {
                builder.append(" - WARN No functions registered");
            } else if (pathFunctions.size() == 1) {
                builder.append(" with function:");
            } else if (pathFunctions.size() > 1) {
                builder.append(" with functions:");
            }

            System.out.println(builder);

            if (pathFunctions.size() > 0) {
                for (String path : pathFunctions.keySet()) {
                    var functionClass = pathFunctions.get(path).getClass();
                    var functionClassName = !functionClass.getSimpleName().isEmpty()
                            ? functionClass.getSimpleName() : functionClass.getName();

                    System.out.printf("   %s\t-> %s\n", path, functionClassName);
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Package Private Methods ------------------------------------------------

    com.sun.net.httpserver.HttpHandler buildServerHandler(final HttpHandler function) {

        return new com.sun.net.httpserver.HttpHandler() {

            /**
             * Implements the JDK HTTP server handler.
             *
             * @param he the HTTP exchange object
             * @throws IOException if an I/O error occurs
             */
            @Override
            public void handle(HttpExchange he) throws IOException {

                try {
                    var requestBuilder = HttpRequest.newBuilder()
                            .method(he.getRequestMethod())
                            .path(he.getRequestURI().getPath())
                            .query(he.getRequestURI().getQuery());

                    if (!he.getRequestHeaders().isEmpty()) {
                        var requestHeaders = new HashMap<String, List<String>>();
                        for (Entry<String, List<String>> header : he.getRequestHeaders().entrySet()) {
                            var key = header.getKey();
                            if (!key.equals("Connection") && !key.equals("Host") && !key.equals("Content-length")) {
                                var headerList = requestHeaders.get(key);
                                if (headerList == null) {
                                    headerList = new ArrayList<>(1);
                                    requestHeaders.put(key, headerList);
                                }
                                headerList.addAll(header.getValue());
                            }
                        }
                        requestBuilder.headers(requestHeaders);
                    }

                    if (he.getRequestBody() != null) {
                        requestBuilder.body(he.getRequestBody().readAllBytes());
                    }

                    var request = requestBuilder.build();

                    var response = function.handle(request);

                    he.getResponseHeaders().putAll(response.getHeaders());

                    var statusCode = (response.getStatus() > 0) ? response.getStatus() : 200;

                    he.sendResponseHeaders(statusCode, response.getBodyLength());

                    if (response.getBody() != null) {
                        he.getResponseBody().write(response.getBody());
                        he.getResponseBody().close();
                    }

                } catch (Throwable t) {
                    // Log error
                    System.err.printf("Error occurred handling request %s %s with handler: %s \n",
                            he.getRequestMethod(),
                            he.getRequestURI(),
                            function.getClass().getName());
                    t.printStackTrace();

                    // Write HTTP error response
                    var msg = "An error occurred, please see logs for details.\n";
                    he.sendResponseHeaders(500, msg.length());
                    he.getResponseBody().write(msg.getBytes(StandardCharsets.UTF_8));
                    he.getResponseBody().close();
                }
            }
        };
    }

}
