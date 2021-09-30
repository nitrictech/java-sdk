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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Provides an HttpMiddleware test case.
 */
public class HttpMiddlewareTest {

    @Test
    public void test_next() {
        var middleware1 = new TestHttpMiddleware();

        var middleware2 = new TestHttpMiddleware();

        middleware1.setNext(middleware2);

        assertSame(middleware2, middleware1.getNext());
    }

    @Test
    public void test_adapter_handle() {
        var handler = new TestHttpHandler();

        var middlewareAdapter = new HttpMiddleware.HandlerAdapter(handler);
        assertSame(handler, middlewareAdapter.getHandler());

        var context = HttpContext.newBuilder().method("GET").build();

        var ctx = middlewareAdapter.handle(context, HttpMiddleware.FINAL_MIDDLEWARE);
        assertNotNull(ctx);

        assertTrue(handler.called);
    }

    // Inner Classes ----------------------------------------------------------

    public static class TestHttpMiddleware extends HttpMiddleware {

        @Override
        public HttpContext handle(HttpContext context, HttpMiddleware next) {
            return null;
        }
    }

    public static class TestHttpHandler implements HttpHandler {

        boolean called;

        @Override
        public HttpContext handle(HttpContext context) {
            called = true;
            return context;
        }
    }

}
