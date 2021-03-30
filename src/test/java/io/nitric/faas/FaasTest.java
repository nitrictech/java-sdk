package io.nitric.faas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FaasTest {

    // Avoid default port and potential conflicts when running unit tests
    private final static int PORT = 8081;

    NitricFunction handlerA = new NitricFunction() {
        public NitricResponse handle(NitricEvent request) {
            return null;
        }
    };

    @Test public void test_config() {
        var faas = new Faas();

        assertEquals(8080, faas.port);
        assertEquals("127.0.0.1", faas.hostname);

        faas.hostname("localhost");
        assertEquals("localhost", faas.hostname);

        faas.port(PORT);
        assertEquals(PORT, faas.port);
    }

    @Test public void test_start_stop() {
        var faas = new Faas().port(PORT);
        assertNull(faas.httpServer);

        faas.start(handlerA);
        assertNotNull(faas.httpServer);

        // Ensure server cant be started twice
        try {
            faas.start(handlerA);
            assertFalse(true);

        } catch (IllegalStateException ise) {
        }

        faas.httpServer.stop(2);
        faas.httpServer = null;

        faas.start(handlerA);

        assertNotNull(faas.httpServer);

        faas.httpServer.stop(2);
    }

}
