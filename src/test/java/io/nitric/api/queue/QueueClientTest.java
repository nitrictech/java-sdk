package io.nitric.api.queue;

import io.nitric.api.kv.KeyValueClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueueClientTest {

    @Test public void test_build() {
        var client = QueueClient.build("queue");

        assertNotNull(client);
        assertEquals("queue", client.queue);
        assertNotNull(client.serviceStub);

        try {
            QueueClient.newBuilder().build();
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertEquals("queue parameter is required", npe.getMessage());
        }
    }

}
