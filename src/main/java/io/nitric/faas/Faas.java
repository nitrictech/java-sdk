package io.nitric.faas;

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
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Objects;

import io.nitric.proto.faas.v1.TriggerRequest;
import com.google.protobuf.util.JsonFormat;

/**
 * <p>
 *  Provides a Nitric FaaS (Function as a Service) server.
 * </p>
 *
 * <p>
 *  The example below starts a new <code>Fass</code> server with the <code>HelloWorld</code> function.
 * </p>
 *
 * <pre><code class="code">
 * package com.example;
 *
 * import io.nitric.faas.Faas;
 * import io.nitric.faas.NitricEvent;
 * import io.nitric.faas.NitricFunction;
 * import io.nitric.faas.NitricResponse;
 *
 * public class HelloWorld implements NitricFunction {
 *
 *     public NitricResponse handle(Trigger trigger) {
 *         return trigger.defaultResponse("Hello World");
 *     }
 *
 *     public static void main(String... args) {
 *         Faas.start(new HelloWorld());
 *     }
 * }
 * </code></pre>
 *
 * @see NitricFunction
 *
 * @since 1.0.0
 */
public class Faas {

    static final String DEFAULT_HOSTNAME = "127.0.0.1";

    String hostname = DEFAULT_HOSTNAME;
    int port = 8080;
    HttpServer httpServer;

    // Public Methods -------------------------------------------------------------------

    /**
     * Set the server hostname.
     *
     * @param hostname the server hostname
     * @return the Faas server instance
     */
    public Faas hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    /**
     * Set the server port. The default port is 8080.
     *
     * @param port the server port
     * @return the Faas server instance
     */
    public Faas port(int port) {
        this.port = port;
        return this;
    }

    /**
     * Start the FaaS server after configuring the given function.
     *
     * @param function the function (required)
     */
    public void startFunction(NitricFunction function) {
        Objects.requireNonNull(function, "null function parameter");

        if (httpServer != null) {
            throw new IllegalStateException("server already started");
        }

        var childAddress = System.getenv("CHILD_ADDRESS");
        if (childAddress != null && !childAddress.isBlank()) {
            hostname = childAddress;
        }

        try {
            httpServer = HttpServer.create(new InetSocketAddress(hostname, port), 0);

            httpServer.createContext("/", buildServerHandler(function));

            httpServer.setExecutor(null);

            // Start the server
            httpServer.start();

            var builder = new StringBuilder().append(getClass().getSimpleName());
            if (DEFAULT_HOSTNAME.equals(hostname)) {
                builder.append(" listening on port ").append(port);

            } else {
                builder.append(" listening on ").append(hostname).append(":").append(port);
            }
            builder.append(" with function: ");

            if (!function.getClass().getSimpleName().isEmpty()) {
                builder.append(function.getClass().getSimpleName());
            } else {
                builder.append(function.getClass().getName());
            }

            System.out.println(builder);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Quick Start a Nitric Function with defaults
     *
     * @param function The function to start
     */
    public static Faas start(NitricFunction function) {
        var faas = new Faas();
        faas.startFunction(function);
        return faas;
    }

    // Package Private Methods ------------------------------------------------

    HttpHandler buildServerHandler(final NitricFunction function) {

        return new HttpHandler() {

            /**
             * Implements the JDK HTTP server handler.
             *
             * @param he the HTTP exchange object
             * @throws IOException if an I/O error occurs
             */
            @Override
            public void handle(HttpExchange he) throws IOException {

                try {
                    if (he.getRequestBody() == null) {
                        // Invalid request from membrane
                        // TODO: Likely need to change this to a more valid exception type
                        throw new InvalidObjectException("Membrane did not send a trigger request");
                    }

                    var trBuilder = TriggerRequest.newBuilder();
                    // Deserialize the requset from the membrane
                    var jsonString = new String(he.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    JsonFormat.parser().ignoringUnknownFields().merge(jsonString, trBuilder);
                    var triggerRequest = trBuilder.build();

                    var trigger = Trigger.fromGrpcTriggerRequest(triggerRequest);

                    Response response = null;

                    try {
                        response = function.handle(trigger);
                    } catch (Throwable t) {
                        // Return default response type back to the membrane with failure indicators
                        response = trigger.defaultResponse(
                            "An error occurred, please see logs for details.\n".getBytes(StandardCharsets.UTF_8)
                        );
                        if (response.getContext().isHttp()) {
                            response.getContext().asHttp().setStatus(500);
                            response.getContext().asHttp().addHeader("Content-Type", "text/plain");
                        }
                    }

                    var triggerResponse = response.toGrpcTriggerResponse();
                    var jsonResponse = JsonFormat.printer().print(triggerResponse);

                    he.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
                    he.sendResponseHeaders(200, jsonResponse.length());

                    he.getResponseBody().write(jsonResponse.getBytes(StandardCharsets.UTF_8));
                    he.getResponseBody().close();

                } catch (Throwable t) {
                    // Log error
                    System.err.printf("Error occurred handling request %s %s with function: %s \n",
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
