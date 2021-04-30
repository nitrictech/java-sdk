package io.nitric.faas;

/*-
 * #%L
 * Nitric Java SDK
 * %%
 * Copyright (C) 2021 Nitric Pty Ltd
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
