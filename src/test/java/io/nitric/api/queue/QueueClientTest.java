package io.nitric.api.queue;

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

import io.nitric.proto.queue.v1.FailedTask;
import io.nitric.proto.queue.v1.*;
import io.nitric.util.ProtoUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class QueueClientTest {

    @Test
    public void test_build() {
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

    @Test
    public void test_send() {
        var mock = new MockQueueBlockingStub() {
            @Override
            public QueueSendResponse send(QueueSendRequest request) {
                assertNotNull(request);
                assertNotNull(request.getQueue());
                assertNotNull(request.getTask());
                return null;
            }
        };
        var client = QueueClient.newBuilder().queue("queue").serviceStub(mock).build();

        Map<String, Object> payload = Map.of("status", "ready");
        var task = Task.newBuilder().payload(payload).build();

        client.send(task);

        try {
            client.send(null);
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("task parameter is required", npe.getMessage());
        }
    }

    @Test
    public void test_sendBatch() {
        var mock = new MockQueueBlockingStub() {
            @Override
            public QueueSendBatchResponse sendBatch(QueueSendBatchRequest request) {
                assertNotNull(request);
                assertNotNull(request.getQueue());
                assertNotNull(request.getTasksList());
                var tasks = request.getTasksList();

                QueueSendBatchResponse.Builder responseBuilder = QueueSendBatchResponse.newBuilder();
                for (int i = 0; i < tasks.size(); i++) {
                    if (i % 2 != 0) {
                        var failedTask = FailedTask.newBuilder()
                                .setTask(tasks.get(i))
                                .setMessage("we don't like odd tasks around here: " + i)
                                .build();
                        responseBuilder.addFailedTasks(failedTask);
                    }
                }

                return responseBuilder.build();
            }
        };

        var client = QueueClient.newBuilder().queue("queue").serviceStub(mock).build();

        // Test empty list
        var tasks = new ArrayList<Task>();
        var failedTasks = client.sendBatch(tasks);
        assertNotNull(failedTasks);
        assertTrue(failedTasks.isEmpty());

        // Test non empty list
        for (int i = 0; i < 10; i++) {
            Map<String, Object> payload = Map.of("status", "ready");
            var task = Task.newBuilder()
                    .id(String.valueOf(i))
                    .leaseId(String.valueOf(i * 10))
                    .payloadType("payloadType")
                    .payload(payload)
                    .build();
            tasks.add(task);
        }

        failedTasks = client.sendBatch(tasks);
        assertNotNull(failedTasks);
        for (io.nitric.api.queue.FailedTask failedTask : failedTasks) {
            assertNotNull(failedTask.getTask());
            assertNotNull(failedTask.getMessage());
        }

        try {
            client.sendBatch(null);
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("tasks parameter is required", npe.getMessage());
        }
    }

    @Test
    public void test_receive() {
        var mock = new MockQueueBlockingStub() {
            @Override
            public QueueReceiveResponse receive(QueueReceiveRequest request) {
                assertNotNull(request);
                assertNotNull(request.getQueue());
                assertEquals(10, request.getDepth());
                var struct = ProtoUtils.toStruct(Map.of("status", "ready"));
                var task = NitricTask.newBuilder()
                        .setId("id")
                        .setLeaseId("leaseId")
                        .setPayloadType("payloadType")
                        .setPayload(struct)
                        .build();
                return QueueReceiveResponse.newBuilder().addTasks(task).build();
            }
        };
        var client = QueueClient.newBuilder().queue("queue").serviceStub(mock).build();

        var tasks = client.receive(10);
        assertNotNull(tasks);
        assertEquals(1, tasks.size());

        var task = tasks.get(0);
        assertEquals("id", task.getId());
        assertEquals("leaseId", task.getLeaseId());
        assertEquals("payloadType", task.getPayloadType());
        assertEquals("{status=ready}", task.getPayload().toString());
    }

    @Test
    public void test_complete() {
        var mock = new MockQueueBlockingStub() {
            @Override
            public QueueCompleteResponse complete(QueueCompleteRequest request) {
                assertNotNull(request);
                assertNotNull(request.getQueue());
                assertNotNull(request.getLeaseId());
                return null;
            }
        };
        var client = QueueClient.newBuilder().queue("queue").serviceStub(mock).build();

        client.complete("leaseId");

        try {
            client.complete(null);
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("leaseId parameter is required", npe.getMessage());
        }
    }
}
