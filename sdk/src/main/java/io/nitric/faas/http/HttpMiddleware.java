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

/**
 * <p>
 * Provides a HTTP Middleware handler class.
 * </p>
 *
 * @see HttpContext
 * @see HttpHandler
 */
public abstract class HttpMiddleware {

    /** The final middleware in the chain. */
    public static HttpMiddleware FINAL_MIDDLEWARE = new FinalMiddleware();

    /** The next HttpMiddleware to execute. */
    protected HttpMiddleware next;

    /**
     * Handle the Http Request and invoke the next handler in the chain.
     *
     * @param context the HTTP request/response context
     * @param next the next HttpMiddleware handler to invoke in the chain
     * @return the context object returned by the next handler
     */
    public abstract HttpContext handle(HttpContext context, HttpMiddleware next);

    /**
     * Return the next HttpMiddleware to process request context. If no next
     * middleware configured, then this method will return the FinalMiddleware.
     *
     * @return the next HttpMiddleware to process request context
     */
    public HttpMiddleware getNext() {
        return (next != null) ? next : FINAL_MIDDLEWARE;
    }

    /**
     * Set the next HttpMiddleware to process request context.
     *
     * @param middleware the next HttpMiddleware to process request context (required)
     */
    public void setNext(HttpMiddleware middleware) {
        Contracts.requireNonNull(middleware, "middleware");

        this.next = middleware;
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a HttpHandler to HttpMiddleware adapter class.
     */
    public static class HandlerAdapter extends HttpMiddleware {

        /** The HttpHandler to adapt. */
        protected final HttpHandler handler;

        /**
         * Create a new HttpMiddleware adapter from the given HttpHandler object.
         *
         * @param handler the HttpHandler to adapt to middleware
         */
        public HandlerAdapter(HttpHandler handler) {
            Contracts.requireNonNull(handler, "handler");

            this.handler = handler;
        }

        /**
         * Handle the Http Request and invoke the next handler in the chain.
         *
         * @param context the HTTP request/response context
         * @param next the next HttpMiddleware handler to invoke in the chain
         * @return the context object returned by the next handler
         */
        @Override
        public HttpContext handle(HttpContext context, HttpMiddleware next) {

            var ctx  = handler.handle(context);

            return next.handle(ctx, next.getNext());
        }

        /**
         * Return the wrapped HttpHandler object.
         *
         * @return the wrapped HttpHandler object
         */
        public HttpHandler getHandler() {
            return handler;
        }
    }

    /**
     * Provides the final HttpMiddleware in the chain. This handler will simply return the context and not invoke the
     * next middleware.
     */
    public static class FinalMiddleware extends HttpMiddleware {

        /**
         * This method will simply return the context and not invoke the next middleware.
         *
         * @param context the HTTP request/response context
         * @param next the next HttpMiddleware handler which will be ignored
         * @return the context invoked with
         */
        @Override
        public HttpContext handle(HttpContext context, HttpMiddleware next) {
            return context;
        }
    }

}
