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

package io.nitric.faas2.http;

import io.nitric.util.Contracts;

/**
 * <p>
 * Provides an HTTP Middleware handler class.
 * </p>
 *
 * <p>
 * Middleware Handlers follow the 'Chain of Responsibility' design pattern whereby they
 * invoke the next handler which can handle the request. This pattern is equivalent
 * to the J2EE Servlet Filters or JavaScript front-end middleware design patterns.
 * </p>
 */
public abstract class HttpMiddleware {

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
     * Return the next HttpMiddleware to process request context.
     *
     * @return the next HttpMiddleware to process request context
     */
    public HttpMiddleware getNext() {
        return next;
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

}
