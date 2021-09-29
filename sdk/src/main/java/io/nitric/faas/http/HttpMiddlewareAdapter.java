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

import io.nitric.faas.event.EventHandler;
import io.nitric.util.Contracts;

/**
 * Provides a HttpHandler to HttpMiddleware adapter class.
 */
public class HttpMiddlewareAdapter extends HttpMiddleware {

    /** The HttpHandler to adapt. */
    protected final HttpHandler handler;

    /**
     * Create a new HttpMiddleware adapter from the given HttpHandler object.
     *
     * @param handler the HttpHandler to adapt to middleware
     */
    public HttpMiddlewareAdapter(HttpHandler handler) {
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
