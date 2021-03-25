package io.nitric.api.event;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class NitricEventTest {

    @Test
    public void test_build() {
        Map<String, Object> payload = Map.of("name", "value");

        var event = NitricEvent.newBuilder()
                .id("id")
                .payloadType("payloadType")
                .payload(payload)
                .build();

        assertNotNull(event);
        assertEquals("id", event.getId());
        assertEquals("payloadType", event.getPayloadType());
        assertEquals(payload, event.getPayload());
        assertEquals("NitricEvent[id=id, payloadType=payloadType, payload={name=value}]", event.toString());

        event = NitricEvent.build(payload);
        assertNotNull(event);
        assertNull(event.getId());
        assertNull(event.getPayloadType());
        assertEquals(payload, event.getPayload());
        assertEquals("NitricEvent[id=null, payloadType=null, payload={name=value}]", event.toString());

        try {
            NitricEvent.newBuilder().build();
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("payload parameter is required", npe.getMessage());
        }

        try {
            NitricEvent.build(null);
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("payload parameter is required", npe.getMessage());
        }
    }
}
