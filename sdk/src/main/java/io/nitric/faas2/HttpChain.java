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

package io.nitric.faas2;

import io.nitric.faas2.http.HttpContext;
import io.nitric.faas2.http.HttpMiddleware;

/**
 * Provides a HTTP Middleware chain link.
 */
public class HttpChain implements HttpMiddleware {

    final HttpMiddleware middleware;
    HttpChain nextChain;

    /**
     * Create a new HttpMiddleware chain.
     *
     * @param middleware the HttpMiddle to process
     */
    HttpChain(HttpMiddleware middleware) {
        this.middleware = middleware;
    }

    /**
     * Set the next middleware HttpChain.
     *
     * @param nextChain
     */
    void setNext(HttpChain nextChain) {
        this.nextChain = nextChain;
    }

    @Override
    public HttpContext handle(HttpContext context, HttpMiddleware next) {
        return middleware.handle(context, next);
    }

    // public void process(HttpContext context, HttpChain chain) {
    //     var resultCtx = middleware.handle(context, chain);
    // }

    /**
     * Provides the final chain HTTP middlware which returns the given context
     */
    static class FinalMiddleware implements HttpMiddleware {

        @Override
        public HttpContext handle(HttpContext context, HttpMiddleware next) {
            return context;
        }
    }
    
}

      