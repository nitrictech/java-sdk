package io.nitric.faas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FaasTest {

    // Avoid default port and potential conflicts when running unit tests
    private final static int PORT = 8081;

    NitricFunction handlerA = new NitricFunction() {
        public NitricResponse handle(NitricRequest request) {
            return null;
        }
    };

    NitricFunction handlerB = new NitricFunction() {
        public NitricResponse handle(NitricRequest request) {
            return null;
        }
    };

    @Test public void test_config() {
        var faas = new Faas();

        assertEquals(8080, faas.port);
        assertEquals("0.0.0.0", faas.hostname);
        assertTrue(faas.pathFunctions.isEmpty());

        faas.setHostname("localhost");
        assertEquals("localhost", faas.hostname);

        faas.setPort(PORT);
        assertEquals(PORT, faas.port);
    }

    @Test public void test_start_stop() {
        var faas = new Faas().setPort(PORT);
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
        assertEquals(1, faas.pathFunctions.size());
        assertEquals(handlerA, faas.pathFunctions.get("/"));

        faas.stop();
        assertNull(faas.httpServer);
    }

    @Test public void test_register() {

        var faas = new Faas().setPort(PORT);

        faas.register("/rest/", handlerA);

        assertEquals(1, faas.pathFunctions.size());
        assertEquals(handlerA, faas.pathFunctions.get("/rest/"));

        faas.register("/customers/", handlerB);
        assertEquals(2, faas.pathFunctions.size());
        assertEquals(handlerB, faas.pathFunctions.get("/customers/"));

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
