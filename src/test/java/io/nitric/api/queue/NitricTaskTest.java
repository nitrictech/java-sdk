package io.nitric.api.queue;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class NitricTaskTest {

    @Test
    public void test_build() {
        Map<String, Object> payload = Map.of("name", "value");

        var task = NitricTask.newBuilder()
                .id("id")
                .leaseId("leaseId")
                .payloadType("payloadType")
                .payload(payload)
                .build();

        assertNotNull(task);
        assertEquals("id", task.getId());
        assertEquals("leaseId", task.getLeaseId());
        assertEquals("payloadType", task.getPayloadType());
        assertEquals(payload, task.getPayload());
        assertEquals("NitricTask[id=id, leaseId=leaseId, payloadType=payloadType, payload={name=value}]", task.toString());

        try {
            NitricTask.newBuilder().build();
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("payload parameter is required", npe.getMessage());
        }
    }
}
