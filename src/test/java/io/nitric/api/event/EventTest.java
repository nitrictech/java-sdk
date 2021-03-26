package io.nitric.api.event;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

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
