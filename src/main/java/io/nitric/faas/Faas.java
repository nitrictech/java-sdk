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
 *     The example below provides a simple hello world example where all requests are routed to a lambda function
 *     handler.
 * </p>
 *
 * <code>
 * public class HelloWorld {
 *
 *     public static void main(String[] args) {
 *
 *         new Faas().start((NitricRequest r) -> {
 *             return NitricResponse.build("Hello World");
 *         });
 *     }
 * }
 * </code>
 *
 * <p>
 *      The example below supports registering separate lambda functions for different routes.
 * </p>
 *
 * <code>
 * public class MultiRoutes {
 *
 *     public static void main(String[] args) {
 *
 *         new Faas()
 *                 .register("/accounts", (NitricRequest r) -> {
 *                     return NitricResponse.newBuilder()
 *                             .bodyText("/accounts: " + r.getParameters())
 *                             .build();
 *                 })
 *                 .register("/products", (NitricRequest r) -> {
 *                     return NitricResponse.newBuilder()
 *                             .bodyText("/products: " + r.getParameters())
 *                             .build();
 *                 })
 *                 .start();
 *     }
 * }
 * </code>
 *
 * @see NitricFunction
 *
 * @since 1.0.0
 */
public class Faas {

    String hostname = "127.0.0.1";
    int port = 8080;
    final Map<String, NitricFunction> pathFunctions = new LinkedHashMap<>();
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
     * Set the server port. The default port is 8080.
     * @param port the server port
     * @return the Faas server instance
     */
    public Faas setPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Register the function for the given path.
     *
     * @param path the function route path (required)
     * @param function the function to register (required)
     * @return the Faas server instance
     */
    public Faas register(String path, NitricFunction function) {
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
    public void start(NitricFunction function) {
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

        if (pathFunctions.isEmpty()) {
            System.out.println("WARN No functions registered..");
        }

        long time = System.currentTimeMillis();

        var childAddress = System.getenv("CHILD_ADDRESS");
        if (childAddress != null && !childAddress.isBlank()) {
            hostname = childAddress;
        }

        try {
            httpServer = HttpServer.create(new InetSocketAddress(hostname, port), 0);

            for (String path : pathFunctions.keySet()) {
                var function = pathFunctions.get(path);
                httpServer.createContext(path, buildServerHandler(function));
            }

            httpServer.setExecutor(null);

            // Start the server
            httpServer.start();

            var builder = new StringBuilder()
                    .append(getClass().getSimpleName())
                    .append(" listening on port ")
                    .append(port)
                    .append(" with ")
                    .append(pathFunctions.size())
                    .append(" function");

            if (pathFunctions.size() == 0) {
                builder.append("s");
            } else if (pathFunctions.size() > 1) {
                builder.append("s:");
            }

            System.out.println(builder);

            if (pathFunctions.size() > 1) {
                for (String path : pathFunctions.keySet()) {
                    System.out.printf("   %s\t-> %s\n", path, pathFunctions.get(path).getClass().getName());
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Package Private Methods ------------------------------------------------

    HttpHandler buildServerHandler(final NitricFunction function) {

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
                    var requestBuilder = NitricRequest.newBuilder()
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
                    System.err.printf("Error occurred handling request %s with %s \n",
                            he.getRequestURI(),
                            function.getClass().getSimpleName());
                    t.printStackTrace();
                }
            }
        };
    }

}