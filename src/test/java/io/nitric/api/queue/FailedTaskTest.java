package io.nitric.api.queue;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FailedTaskTest {

    @Test
    public void test_build() {
        Map<String, Object> payload = Map.of("name", "value");

        var task = Task.newBuilder()
                .payload(payload)
                .build();

        assertNotNull(task);

        var failedTask = FailedTask.newBuilder()
                .task(task)
                .message("message")
                .build();

        assertNotNull(failedTask);
        assertEquals(task, failedTask.getTask());
        assertEquals("message", failedTask.getMessage());
        assertEquals("FailedTask[task=Task[id=null, leaseId=null, payloadType=null, payload={name=value}], message=message]",
                failedTask.toString());

        try {
            FailedTask.newBuilder().build();
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("task parameter is required", npe.getMessage());
        }
    }
}
