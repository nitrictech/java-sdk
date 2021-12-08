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

import io.nitric.faas.logger.Logger;
import io.nitric.util.Contracts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Provides a HTTP Middleware Router class. This class enables you to create a fat lambda, or 'minilith' which can
 * route HTTP requests to multiple handlers or middleware. This can be useful when creating simple REST services
 * which can be deployed as a single Faas function.
 * </p>
 *
 * <h3>Router Example</h3>
 *
 * <p>
 *  The example below provides a Order service REST API. The OrderService defines a HTTP router which is configured
 *  with routes and function handlers for the various REST API operations.
 * </p>
 *
 * <pre><code class="code">
 * import com.example.order.service.handler.CreateOrder;
 * import com.example.order.service.handler.ListOrders;
 * import com.example.order.service.handler.GetOrder;
 *
 * import io.nitric.faas.Faas;
 * import io.nitric.faas.http.HttpRouter;
 *
 * public class OrderService {
 *
 *    public static void main(String[] args) {
 *
 *         var router = new HttpRouter()
 *                 .post("/orders", new CreateOrder())
 *                 .get("/orders", new ListOrders())
 *                 .get("/orders/{orderId}", new GetOrder());
 *
 *         new Faas().http(router).start();
 *     }
 * }
 * </code></pre>
 *
 * <p>
 *  The project Open API (api.yaml) file defines the HTTP operation routes and a <code>'x-nitric-target'</code>
 *  function name mapping to our <code>order-service</code>.
 * </p>
 *
 * <pre><code class="code">
 * # Order REST Open API Definition
 * openapi: 3.0.0
 * info:
 *   version: 1.0.0
 *   title: Order API
 * paths:
 *   /orders:
 *     get:
 *       operationId: orders-list
 *       x-nitric-target:
 *         name: <b><u>order-service</u></b>
 *         type: function
 *       responses:
 *         '200':
 *     post:
 *       operationId: orders-create
 *       x-nitric-target:
 *         name: <b><u>order-service</u></b>
 *         type: function
 *       responses:
 *         '200':
 *       requestBody:
 *         required: true
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/OrdersCreate'
 *   /orders/{orderId}:
 *     get:
 *       operationId: orders-read
 *       parameters:
 *         - in: path
 *           name: orderId
 *           schema:
 *             type: string
 *           required: true
 *       x-nitric-target:
 *         name: <b><u>order-service</u></b>
 *         type: function
 *       responses:
 *         '200':
 * ...
 * </code></pre>
 *
 * <p>
 *  In the Nitric Stack definition (nitric.yaml) the <code>order-service</code> function is handled by the
 *  <code>OrderService</code> application (target/order-service-1.0.jar).
 * </p>
 *
 * <pre><code class="code">
 * # Nitric Stack Definition
 * name: order-service
 *
 * # Nitric functions
 * functions:
 *   <b><u>order-service</u></b>:
 *     handler: target/order-service-1.0.jar
 *
 * # Nitric collections
 * collections:
 *   orders: {}
 *
 * # Nitric APIs
 * apis:
 *   orders: api.yaml
 * </code></pre>
 *
 * <p>
 *  Internally the <code>OrderService</code> HttpRouter will dispatch requests to the correct handlers.
 * </p>
 *
 * <h3>Query Path Parameters</h3>
 *
 * <p>
 *  The router will parse any request path parameters defined in the Open API paths parameter variables
 *  If the router defines Open API path parameter variables
 *
 *  . The HttpRouter will parse these values from the request
 *  path and add them to the context request query parameters. In the example below the <code>GetOrder</code> route
 *  defines an <code>orderId</code> path parameter.
 * </p>
 *
 * <pre><code class="code">
 * var router = new HttpRouter()
 *         .post("/orders", new CreateOrder())
 *         .get("/orders", new ListOrders())
 *         .get("/orders/{<u>orderId</u>}", new GetOrder());
 * </code></pre>
 *
 * <pre><code class="code">
 * pubic class GetOrder extends HttpHandler {
 *
 *     &#64;OOverride
 *     public HttpContext handle(HttpContext context) {
 *
 *         var id = context.getRequest().getQueryParam("<u>orderId</u>");
 *
 *         ..
 *     }
 * }
 * </code></pre>
 *
 * <h3>Not Found Handling</h3>
 *
 * <p>
 *  If no route handler is found for a HTTP request then the default <code>NotFoundMiddleware</code>
 *  will handle the request, returning a HTTP 404 status code (resource not found).
 * </p>
 *
 * <p>
 *  To customize the not found behaviour configure your own Not Found middleware or handler using the router
 *  <code>notFound()</code> methods.
 * </p>
 */
public class HttpRouter extends HttpMiddleware {

    /**
     * The HTTP Methods enum.
     */
    public enum Method {
        /** The HTTP GET method. */
        GET,
        /** The HTTP POST method. */
        POST,
        /** The HTTP PUT method. */
        PUT,
        /** The HTTP DELETE method. */
        DELETE,
        /** The HTTP PATCH method. */
        PATCH,
        /** The HTTP HEAD method. */
        HEAD,
        /** The HTTP OPTIONS method. */
        OPTIONS,
        /** The HTTP TRACE method. */
        TRACE
    }

    /** The default HTTP 404 Not Found middleware when a route was not found. */
    public static final HttpMiddleware NOT_FOUND_MIDDLEWARE = new NotFoundMiddleware();

    /** The map of routes keyed by method. */
    protected Map<Method, List<Route>> routeMap = new HashMap<>();

    /** The registered HTTP 404 not found middleware. */
    protected HttpMiddleware notFoundMiddleware;

    /** The router logger. */
    protected Logger logger;

    // Public Methods ---------------------------------------------------------

    /**
     * Provides an HTTP routing middleware handler.
     *
     * @param context the HTTP request/response context
     * @param next the next HttpMiddleware handler to invoke in the chain
     * @return the context object returned by the next handler
     */
    @Override
    public HttpContext handle(HttpContext context, HttpMiddleware next) {
        var start = System.currentTimeMillis();

        // TODO: need to handle list of routes
        var route = getRoute(context);

        // Add any path parameters to request query params
        var pathParams = route.getPathParams(context.getRequest().getPath());
        if (!pathParams.isEmpty()) {
            var builder = HttpContext.newBuilder().request(context.getRequest());
            pathParams.forEach((name, value) -> {
                builder.addQueryParam(name, value);
            });
            context = builder.build();
        }

        var middleware = route.middleware;

        if (logger != null) {
            var ctx = middleware.handle(context, next);

            var duration = System.currentTimeMillis() - start;

            var name = middleware.getClass().getSimpleName();
            if (middleware instanceof HttpMiddleware.HandlerAdapter) {
                var handler  = ((HttpMiddleware.HandlerAdapter) middleware).getHandler();
                name = handler.getClass().getSimpleName();
            }

            logger.info("HTTP %s %s -> %s %s handled in %s ms",
                    context.getRequest().getMethod(),
                    context.getRequest().getPath(),
                    ctx.getResponse().getStatus(),
                    name,
                    duration);

            return ctx;

        } else {
            return middleware.handle(context, next);
        }
    }

    /**
     * Add a GET method route handler for the given path.
     *
     * @param path the GET path (required)
     * @param handler the route handler (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter get(String path, HttpHandler handler) {
        return addRoute(Method.GET, path, handler);
    }

    /**
     * Add a GET method route middle for the given path.
     *
     * @param path the GET path (required)
     * @param middleware the route middleware (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter get(String path, HttpMiddleware middleware) {
        return addRoute(Method.GET, path, middleware);
    }

    /**
     * Add a GET method route handler for the "/" path.
     *
     * @param handler the route handler (required)
     * @return this chainable HttpRouter object (required)
     */
    public HttpRouter get(HttpHandler handler) {
        return addRoute(Method.GET, "/", handler);
    }

    /**
     * Add a GET method route middle for the "/" path.
     *
     * @param middleware the route middleware (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter get(HttpMiddleware middleware) {
        return addRoute(Method.GET, "/", middleware);
    }

    /**
     * Add a POST method route handler for the given path.
     *
     * @param path the POST path (required)
     * @param handler the route handler (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter post(String path, HttpHandler handler) {
        return addRoute(Method.POST, path, handler);
    }

    /**
     * Add a POST method route middleware for the given path.
     *
     * @param path the POST path (required)
     * @param middleware the route middleware (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter post(String path, HttpMiddleware middleware) {
        return addRoute(Method.POST, path, middleware);
    }

    /**
     * Add a POST method route handler for the "/" path.
     *
     * @param handler the route handler (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter post(HttpHandler handler) {
        return addRoute(Method.POST, "/", handler);
    }

    /**
     * Add a POST method route middleware for the "/" path.
     *
     * @param middleware the route middleware (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter post(HttpMiddleware middleware) {
        return addRoute(Method.POST, "/", middleware);
    }

    /**
     * Add a PUT method route handler for the given path.
     *
     * @param path the PUT path (required)
     * @param handler the route handler (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter put(String path, HttpHandler handler) {
        return addRoute(Method.PUT, path, handler);
    }

    /**
     * Add a PUT method route middleware for the given path.
     *
     * @param path the PUT path (required)
     * @param middleware the route middleware (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter put(String path, HttpMiddleware middleware) {
        return addRoute(Method.PUT, path, middleware);
    }

    /**
     * Add a PUT method route handler for the "/" path.
     *
     * @param handler the route handler (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter put(HttpHandler handler) {
        return addRoute(Method.PUT, "/", handler);
    }

    /**
     * Add a PUT method route middleware for the "/" path.
     *
     * @param middleware the route middleware (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter put(HttpMiddleware middleware) {
        return addRoute(Method.PUT, "/", middleware);
    }

    /**
     * Add a DELETE method route handler for the given path.
     *
     * @param path the DELETE path (required)
     * @param handler the route handler (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter delete(String path, HttpHandler handler) {
        return addRoute(Method.DELETE, path, handler);
    }

    /**
     * Add a DELETE method route middleware for the given path.
     *
     * @param path the DELETE path (required)
     * @param middleware the route middleware (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter delete(String path, HttpMiddleware middleware) {
        return addRoute(Method.DELETE, path, middleware);
    }

    /**
     * Add a DELETE method route handler for the "/" path.
     *
     * @param handler the route handler (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter delete(HttpHandler handler) {
        return addRoute(Method.DELETE, "/", handler);
    }

    /**
     * Add a DELETE method route middleware for the "/" path.
     *
     * @param middleware the route middleware (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter delete(HttpMiddleware middleware) {
        return addRoute(Method.DELETE, "/", middleware);
    }

    /**
     * Add a OPTIONS method route handler for the given path.
     *
     * @param path the OPTIONS path (required)
     * @param handler the route handler (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter options(String path, HttpHandler handler) {
        return addRoute(Method.OPTIONS, path, handler);
    }

    /**
     * Add a OPTIONS method route middleware for the given path.
     *
     * @param path the OPTIONS path (required)
     * @param middleware the route middleware (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter options(String path, HttpMiddleware middleware) {
        return addRoute(Method.OPTIONS, path, middleware);
    }

    /**
     * Add a OPTIONS method route handler for the "/" path.
     *
     * @param handler the route handler (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter options(HttpHandler handler) {
        return addRoute(Method.OPTIONS, "/", handler);
    }

    /**
     * Add a OPTIONS method route middleware for the "/" path.
     *
     * @param middleware the route middleware (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter options(HttpMiddleware middleware) {
        return addRoute(Method.OPTIONS, "/", middleware);
    }

    /**
     * Add the given route middleware.
     *
     * @param method the HTTP method (required)
     * @param path the URL path (required)
     * @param handler the route handler to add (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter addRoute(Method method, final String path, HttpHandler handler) {
        return addRoute(method, path, new HttpMiddleware.HandlerAdapter(handler));
    }

    /**
     * Add the given route middleware.
     *
     * @param method the HTTP method (required)
     * @param path the URL path (required)
     * @param middleware the route middleware to add (required)
     * @return this chainable HttpRouter object
     */
    public HttpRouter addRoute(Method method, final String path, HttpMiddleware middleware) {
        Contracts.requireNonNull(method, "method");
        Contracts.requireNonBlank(path, "path");
        Contracts.requireNonNull(middleware, "middleware");

        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("provide path starting with '/' : " + path);
        }

        List<Route> routes = routeMap.computeIfAbsent(method, k -> new ArrayList<>());

        routes.forEach(route -> {
            if (route.path.equals(path)) {
                String msg = route.method + " '" + path + "' route already configured with: "
                        + route.middleware.getClass().getSimpleName();
                throw new IllegalArgumentException(msg);
            }
        });

        // Parse out any path parameter names, e.g. id in '/orders/{id}'
        if (path.contains("{") && path.contains("}")) {
            List<String> pathParams = null;
            var rootPath = new StringBuilder();

            var parts = path.trim().split("/");

            for (String part : parts) {
                if (part.length() > 0) {
                    if (part.startsWith("{") && part.endsWith("}")) {
                        if (pathParams == null) {
                            pathParams = new ArrayList<>();
                        }
                        pathParams.add(part.substring(1, part.length() - 1));

                        if (rootPath.lastIndexOf("/") != rootPath.length() - 1) {
                            rootPath.append("/");
                        }

                    } else {
                        if (pathParams == null) {
                            rootPath.append("/").append(part);
                        }
                    }
                }
            }
            pathParams = (pathParams == null) ? Collections.emptyList() : pathParams;

            routes.add(new Route(method, rootPath.toString(), pathParams, middleware));

        } else {
            routes.add(new Route(method, path, middleware));
        }

        // Sort in descending order (most specific path to most general)
        routes.sort((r1, r2) -> r2.path.compareTo(r1.path));

        return this;
    }

    /**
     * Configure the default route handler not found handler.
     *
     * @handler the default route handler not found handler (required)
     * @return this chainable HttpRouter object
     */
    public HttpMiddleware notFound(HttpHandler  handler) {
        this.notFoundMiddleware = new HttpMiddleware.HandlerAdapter(handler);
        return this;
    }

    /**
     * Configure the default route handler not found middleware.
     *
     * @middleware the default route handler not found middleware (required)
     * @return this chainable HttpRouter object
     */
    public HttpMiddleware notFound(HttpMiddleware middleware) {
        Contracts.requireNonNull(middleware, "middleware");

        this.notFoundMiddleware = middleware;
        return this;
    }

    /**
     * Configure the router logger.
     *
     * @logger the router (required)
     * @return this chainable HttpRouter object
     */
    public HttpMiddleware logger(Logger logger) {
        Contracts.requireNonNull(logger, "logger");

        this.logger = logger;
        return this;
    }

    // Protected Methods ------------------------------------------------------

    /**
     * Return the HttpMiddle for the given request context, or the FinalMiddleware if not found.
     *
     * @param context the HTTP request context
     * @return the configured middleware route, or the FinalMiddleware if not found
     */
    protected Route getRoute(HttpContext context) {
        // T0DOO: need to support multiple routes

        var method = Method.valueOf(context.getRequest().getMethod().toUpperCase());

        var routes = routeMap.get(method);

        if (routes != null) {
            String path = context.getRequest().getPath();

            for (Route route : routes) {
                if (path.startsWith(route.path)) {
                    return route;
                }
            }
        }

        // Create not found route
        var notFound = (notFoundMiddleware != null) ? notFoundMiddleware : NOT_FOUND_MIDDLEWARE;
        return new Route(method, context.getRequest().getPath(), notFound);
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides an HTTP 404 resource not found middleware.
     */
    public static class NotFoundMiddleware extends HttpMiddleware {

        /**
         * Handle the Http Request returning HTTP 404 response and
         * not executing the next middleware.
         *
         * @param context the HTTP request/response context
         * @param next the next HttpMiddleware handler to invoke in the chain
         * @return the context object returned by the next handler
         */
        @Override
        public HttpContext handle(HttpContext context, HttpMiddleware next) {
            context.getResponse()
                    .status(404)
                    .contentType("text/plain")
                    .text("Not Found");
            return context;
        }
    }

    /**
     * Provides an HTTP Route class.
     */
    protected static class Route {

        protected final HttpMiddleware middleware;
        protected final Method method;
        protected final String path;
        protected final List<String> pathParams;

        /**
         * Create a new HTTP route object.
         *
         * @param method the HTTP method
         * @param path the URL path
         * @param middleware the route middleware
         */
        protected Route(Method method, String path, HttpMiddleware middleware) {
            this.method = method;
            this.path = path;
            this.pathParams = Collections.emptyList();
            this.middleware = middleware;
        }

        /**
         * Create a new HTTP route object.
         *
         * @param method the HTTP method
         * @param path the URL path
         * @param pathParams the path parameter names
         * @param middleware the route middleware
         */
        protected Route(Method method, String path, List<String> pathParams, HttpMiddleware middleware) {
            this.method = method;
            this.path = path;
            this.pathParams = pathParams;
            this.middleware = middleware;
        }

        /**
         * Return the route path parameters for the given request path.
         *
         * @param requestPath the request path
         * @return the route path parameters for the given request path
         */
        protected Map<String, String> getPathParams(String requestPath) {
            if (pathParams.isEmpty() || !requestPath.startsWith(path)) {
                return Collections.emptyMap();
            }

            var parts = requestPath
                    .trim()
                    .substring(path.length())
                    .split("/");

            Map<String, String> results = null;

            for (int i = 0; i < parts.length; i++) {
                if (pathParams.size() > i) {
                    var value = parts[i];
                    var name = pathParams.get(i);

                    if (results == null) {
                        results = new LinkedHashMap<>();
                    }
                    results.put(name, value);
                }
            }

            return (results != null) ? results : Collections.emptyMap();
        }

        /**
         * Return the string representation of this object.
         *
         * @return the string representation of this object
         */
        public String toString() {
            return getClass().getSimpleName()
                    + "[method=" + method
                    + ", path=" + path
                    + ", pathParams=" + pathParams
                    + ", middleware=" + ((middleware != null) ? middleware.getClass().getSimpleName() : null)
                    + "]";
        }
    }

}