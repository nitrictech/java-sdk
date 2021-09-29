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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Provides an HttpMiddlewareAdapter test case.
 */
public class HttpMiddlewareAdapterTest {

    @Test
    public void test_handle() {
        var handler = new TestHttpHandler();

        var httpMiddlewareAdapter = new HttpMiddlewareAdapter(handler);

        var context = HttpContext.newBuilder().method("GET").build();

        var ctx = httpMiddlewareAdapter.handle(context, new FinalHttpMiddleware());
        assertNotNull(ctx);

        assertTrue(handler.called);
    }

    // Inner Classes -----------------------------------------------------------------

    static class TestHttpHandler implements HttpHandler {

        boolean called;

        @Override
        public HttpContext handle(HttpContext context) {
            called = true;
            return context;
        }
    }

    static class FinalHttpMiddleware extends HttpMiddleware {

        @Override
        public HttpContext handle(HttpContext context, HttpMiddleware next) {
            return context;
        }
    }

}