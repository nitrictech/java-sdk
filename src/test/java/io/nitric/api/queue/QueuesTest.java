package io.nitric.api.queue;

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

import io.nitric.proto.queue.v1.FailedTask;
import io.nitric.proto.queue.v1.QueueServiceGrpc.QueueServiceBlockingStub;
import io.nitric.proto.queue.v1.*;
import io.nitric.util.ProtoUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class QueuesTest {

    @Test
    public void test_serviceStub() {
        assertNotNull(Queues.getServiceStub());

        var mock = Mockito.mock(QueueServiceBlockingStub.class);

        Queues.setServiceStub(mock);
        assertEquals(mock, Queues.getServiceStub());
    }

    @Test
    public void test_queue() {
        Queue queue = Queues.queue("orders");

        assertNotNull(queue);
        assertEquals("orders", queue.name);
        assertEquals("orders", queue.getName());
        assertEquals("Queue[name=orders]", queue.toString());

        try {
            Queues.queue(" ");
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-blank name", iae.getMessage());
        }
    }

    @Test
    public void test_send() {
        var mock = Mockito.mock(QueueServiceGrpc.QueueServiceBlockingStub.class);
        Mockito.when(mock.sendBatch(Mockito.any())).thenReturn(
            QueueSendBatchResponse.newBuilder().build()
        );
        Queues.setServiceStub(mock);

        var queue = Queues.queue("queue");

        Map<String, Object> payload = Map.of("status", "ready");
        var task = Task.newBuilder().payload(payload).build();

        try {
            queue.send(null);
            fail();
        } catch (IllegalArgumentException iae) {
            Mockito.verify(mock, Mockito.times(0)).sendBatch(Mockito.any());
            assertEquals("provide non-null task", iae.getMessage());
        }

        queue.send(task);
        Mockito.verify(mock, Mockito.times(1)).sendBatch(Mockito.any());
    }

    @Test
    public void test_sendBatch_none() {
        Queues.queue("queue").sendBatch(new ArrayList<>());
    }

    @Test
    public void test_failed_task_translation() {
        var mock = Mockito.mock(QueueServiceGrpc.QueueServiceBlockingStub.class);

        var failedTasks = QueueSendBatchResponse.newBuilder();
        failedTasks.addFailedTasks(0, FailedTask
                .newBuilder()
                .setTask(NitricTask.newBuilder().setId("1234").build())
                .setMessage("Failed to queue task")
                .build());

        Mockito.when(mock.sendBatch(Mockito.any())).thenReturn(
            failedTasks.build()
        );

        Queues.setServiceStub(mock);

        var queues = Queues.queue("queue");
        var taskList = new ArrayList<Task>();
        taskList.add(Task.newBuilder().id("1234").payload(Map.of("test", "test")).build());
        var response = queues.sendBatch(taskList);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(response.get(0).message, "Failed to queue task");
    }

    @Test
    public void test_sendBatch_empty() {
        try {
            Queues.queue("queue").sendBatch(null);
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-null tasks", iae.getMessage());
        }
    }

    @Test
    public void test_receive() {
        var mock = Mockito.mock(QueueServiceGrpc.QueueServiceBlockingStub.class);
        Mockito.when(mock.receive(Mockito.any())).thenReturn(
                QueueReceiveResponse
                        .newBuilder()
                        .addTasks(
                            NitricTask
                                .newBuilder()
                                .setId("id")
                                .setLeaseId("leaseId")
                                .setPayloadType("payloadType")
                                .setPayload(ProtoUtils.toStruct(Map.of("status", "ready")))
                                .build()
                        )
                        .build()
        );
        Queues.setServiceStub(mock);

        var queues = Queues.queue("queue");

        var tasks = queues.receive(10);
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
        var mock = Mockito.mock(QueueServiceGrpc.QueueServiceBlockingStub.class);
        Mockito.when(mock.complete(Mockito.any())).thenReturn(
                QueueCompleteResponse
                    .newBuilder()
                    .build()
        );
        Queues.setServiceStub(mock);

        var queues = Queues.queue("queue");

        try {
            queues.complete(null);
            fail();

        } catch (IllegalArgumentException iae) {
            Mockito.verify(mock, Mockito.times(0)).complete(Mockito.any());
            assertEquals("provide non-null leaseId", iae.getMessage());
        }

        try {
            queues.complete(" ");
            fail();

        } catch (IllegalArgumentException iae) {
            Mockito.verify(mock, Mockito.times(0)).complete(Mockito.any());
            assertEquals("provide non-blank leaseId", iae.getMessage());
        }

        queues.complete("leaseId");
    }

}
