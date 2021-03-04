package io.nitric.faas;

import java.io.IOException;

import com.sun.net.httpserver.HttpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.Map.Entry;

/**
 * <p>
 *     Provides a Nitric Function as a Service (FaaS) server.
 * </p>
 *
 * <p>
 *     The example below provides a simple hello world example where all requests are routed to the
 *     <code>HelloHandler</code>.
 * </p>
 *
 * <code>
 * public class HelloWorld {
 *
 *     public static void main(String[] args) {
 *
 *         new Faas().start(new HttpHandler() {
 *             public HttpResponse handle(HttpRequest httpRequest) {
 *                 return HttpResponse.build(200, "Hello Nitric");
 *             }
 *         });
 *     }
 * }
 * </code>
 *
 * <p>
 *      The example below supports registering separate handlers for different routes.
 * </p>
 *
 * <code>
 * public class MultiRoutes {
 *
 *     public static void main(String[] args) {
 *
 *         new Faas()
 *                 .register("/accounts", new HttpHandler() {
 *                     public HttpResponse handle(HttpRequest httpRequest) {
 *                         return HttpResponse.newBuilder()
 *                                 .bodyText("customers : " + httpRequest.getPath())
 *                                 .build();
 *                     }
 *                 })
 *                 .register("/products", new HttpHandler() {
 *                     public HttpResponse handle(HttpRequest httpRequest) {
 *                         return HttpResponse.newBuilder()
 *                                 .bodyText("products : " + httpRequest.getPath())
 *                                 .build();
 *                     }
 *                 })
 *                 .start();
 *     }
 * }
 * </code>
 *
 * @since 1.0.0
 */
public class Faas {

    String hostname = "0.0.0.0"; // Use "0.0.0.0" to support accessing WSL2 Linux server from Windows
    int port = 9001;
    final Map<String, io.nitric.faas.http.HttpHandler> pathHandlers = new HashMap<>();
    HttpServer httpServer;

    // Public Methods -------------------------------------------------------------------

    /**
     * Set the server hostname.
     * @param hostname the server hostname
     * @return the Faas server instance
     */
    public Faas setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    /**
     * Set the server port.
     * @param port the server port
     * @return the Faas server instance
     */
    public Faas setPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Register the function handler for the given path.
     *
     * @param path the route path (required)
     * @param handler the function handler (required)
     * @return the Faas server instance
     */
    public Faas register(String path, io.nitric.faas.http.HttpHandler handler) {
        Objects.requireNonNull(path, "null path parameter");
        Objects.requireNonNull(handler, "null handler parameter");

        var checkHandler = pathHandlers.get(path);
        if (checkHandler != null) {
            var msg = checkHandler.getClass().getName() + " already registered for path: " + path;
            throw new IllegalArgumentException(msg);
        }

        pathHandlers.put(path, handler);
        return this;
    }

    /**
     * Start the function server after configuring the given function handler to the "/" route path.
     *
     * @param handler the function handler (required)
     */
    public void start(io.nitric.faas.http.HttpHandler handler) {
        register("/", handler);
        start();
    }

    /**
     * Start the function server.
     */
    public void start() {
        if (httpServer != null) {
            throw new IllegalStateException("server already started");
        }

        if (pathHandlers.isEmpty()) {
            System.out.println("WARN No handlers registered..");
        }

        long time = System.currentTimeMillis();

        try {
            httpServer = HttpServer.create(new InetSocketAddress(hostname, port), 0);

            for (String path : pathHandlers.keySet()) {
                var handler = pathHandlers.get(path);
                httpServer.createContext(path, buildServerHandler(handler));
            }

            httpServer.setExecutor(null);

            // Start the server
            httpServer.start();

            var builder = new StringBuilder()
                    .append(getClass().getSimpleName())
                    .append(" listening on port ")
                    .append(port)
                    .append(" with ")
                    .append(pathHandlers.size())
                    .append(" handler");

            if (pathHandlers.size() == 0) {
                builder.append("s");
            } else if (pathHandlers.size() > 1) {
                builder.append("s:");
            }

            System.out.println(builder);

            if (pathHandlers.size() > 1) {
                for (String path : pathHandlers.keySet()) {
                    System.out.printf("   %s\t-> %s\n", path, pathHandlers.get(path).getClass().getName());
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * <p>
     *     Stop the function server if currently running.
     * </p>
     * <p>
     *     This method will stop the server by closing the listening socket and disallowing any new exchanges from being
     *     processed. The method will then block until all current handlers have completed or else when approximately
     *     a 2 second delay seconds has elapsed (whichever happens sooner). Then, all open TCP connections are
     *     closed, the background thread created by start() exits, and the method returns.
     * </p>
     */
    public void stop() {
        if (httpServer != null) {
            httpServer.stop(2);
            httpServer = null;
            System.out.printf("%s stopped\n", getClass().getSimpleName(), port);
        }
    }

    // Package Private Methods ------------------------------------------------

    HttpHandler buildServerHandler(final io.nitric.faas.http.HttpHandler handler) {

        return new HttpHandler() {

            /**
             * Implements the JDK HTTP server handler.
             * @param he the HTTP exchange object
             * @throws IOException if an I/O error occurs
             */
            @Override
            public void handle(HttpExchange he) throws IOException {

                long start = System.currentTimeMillis();

                try {
                    var requestBuilder = io.nitric.faas.http.HttpRequest.newBuilder()
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

                    var httpRequest = requestBuilder.build();

                    var httpResponse = handler.handle(httpRequest);

                    he.getResponseHeaders().putAll(httpResponse.getHeaders());

                    he.sendResponseHeaders(httpResponse.getStatusCode(), httpResponse.getBodyLength());

                    if (httpResponse.getBody() != null) {
                        he.getResponseBody().write(httpResponse.getBody());
                        he.getResponseBody().close();
                    }

                } catch (Throwable t) {
                    System.err.printf("Error occurred handling request %s with %s \n",
                            he.getRequestURI(),
                            handler.getClass().getSimpleName());
                    t.printStackTrace();
                }
            }
        };
    }

}