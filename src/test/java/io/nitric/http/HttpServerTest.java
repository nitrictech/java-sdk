package io.nitric.http;

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

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpServerTest {

    // Avoid default port and potential conflicts when running unit tests
    private final static int PORT = 8081;

    HttpHandler handlerA = new HttpHandler() {
        public HttpResponse handle(HttpRequest request) {
            return null;
        }
    };

    HttpHandler handlerB = new HttpHandler() {
        public HttpResponse handle(HttpRequest request) {
            return null;
        }
    };

    @Test public void test_config() {
        var faas = new HttpServer();

        assertEquals(8080, faas.port);
        assertEquals("127.0.0.1", faas.hostname);
        assertTrue(faas.pathFunctions.isEmpty());

        faas.hostname("localhost");
        assertEquals("localhost", faas.hostname);

        faas.port(PORT);
        assertEquals(PORT, faas.port);
    }

    @Test public void test_start_stop() {
        var faas = new HttpServer().port(PORT);
        assertNull(faas.httpServer);

        faas.start();
        assertNotNull(faas.httpServer);

        // Ensure server cant be started twice
        try {
            faas.start();
            assertFalse(true);

        } catch (IllegalStateException ise) {
        }

        faas.httpServer.stop(2);
        faas.httpServer = null;

        faas.start(handlerA);

        assertNotNull(faas.httpServer);
        assertEquals(1, faas.pathFunctions.size());
        assertEquals(handlerA, faas.pathFunctions.get("/"));

        faas.httpServer.stop(2);
    }

    @Test public void test_register() {

        var faas = new HttpServer().port(PORT);

        faas.register("/rest/", handlerA);

        assertEquals(1, faas.pathFunctions.size());
        assertEquals(handlerA, faas.pathFunctions.get("/rest/"));

        faas.register("/customers/", handlerB);
        assertEquals(2, faas.pathFunctions.size());
        assertEquals(handlerB, faas.pathFunctions.get("/customers/"));

        faas.start();
        faas.httpServer.stop(2);

        try {
            faas.register("/customers/", handlerA);
            assertTrue(false);

        } catch (IllegalArgumentException iae) {
            assertTrue(iae.toString().endsWith("already registered for path: /customers/"));
        }
    }

}
