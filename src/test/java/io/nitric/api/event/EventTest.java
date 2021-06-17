package io.nitric.api.event;

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


import java.util.Map;

import static org.junit.Assert.*;

public class EventTest {

    @Test
    public void test_build() {
        Map<String, Object> payload = Map.of("name", "value");

        var event = Event.newBuilder()
                .id("id")
                .payloadType("payloadType")
                .payload(payload)
                .build();

        assertNotNull(event);
        assertEquals("id", event.getId());
        assertEquals("payloadType", event.getPayloadType());
        assertEquals(payload, event.getPayload());
        assertEquals("Event[id=id, payloadType=payloadType, payload={name=value}]", event.toString());

        event = Event.build(payload);
        assertNotNull(event);
        assertNull(event.getId());
        assertNull(event.getPayloadType());
        assertEquals(payload, event.getPayload());
        assertEquals("Event[id=null, payloadType=null, payload={name=value}]", event.toString());

        try {
            Event.newBuilder().build();
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("payload parameter is required", npe.getMessage());
        }

        try {
            Event.build(null);
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("payload parameter is required", npe.getMessage());
        }
    }
}
