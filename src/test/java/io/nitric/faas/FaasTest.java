package io.nitric.faas;

import io.nitric.faas.http.HttpHandler;
import io.nitric.faas.http.HttpRequest;
import io.nitric.faas.http.HttpResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FaasTest {

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
        var faas = new Faas();

        assertEquals(9001, faas.port);
        assertEquals("0.0.0.0", faas.hostname);
        assertTrue(faas.pathHandlers.isEmpty());

        faas.setHostname("localhost");
        assertEquals("localhost", faas.hostname);

        faas.setPort(1234);
        assertEquals(1234, faas.port);
    }

    @Test public void test_start_stop() {
        var faas = new Faas();
        assertNull(faas.httpServer);

        faas.start();
        assertNotNull(faas.httpServer);

        // Ensure server cant be started twice
        try {
            faas.start();
            assertFalse(true);

        } catch (IllegalStateException ise) {
        }

        faas.stop();
        assertNull(faas.httpServer);

        faas.start(handlerA);

        assertNotNull(faas.httpServer);
        assertEquals(1, faas.pathHandlers.size());
        assertEquals(handlerA, faas.pathHandlers.get("/"));

        faas.stop();
        assertNull(faas.httpServer);
    }

    @Test public void test_register() {

        var faas = new Faas();

        faas.register("/rest/", handlerA);

        assertEquals(1, faas.pathHandlers.size());
        assertEquals(handlerA, faas.pathHandlers.get("/rest/"));

        faas.register("/customers/", handlerB);
        assertEquals(2, faas.pathHandlers.size());
        assertEquals(handlerB, faas.pathHandlers.get("/customers/"));

        faas.start();
        faas.stop();

        try {
            faas.register("/customers/", handlerA);
            assertTrue(false);

        } catch (IllegalArgumentException iae) {
            assertTrue(iae.toString().endsWith("already registered for path: /customers/"));
        }
    }

}
