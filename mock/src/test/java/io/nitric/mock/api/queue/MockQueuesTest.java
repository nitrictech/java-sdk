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

 package io.nitric.mock.api.queue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.grpc.Status;
import io.nitric.api.NitricException;
import io.nitric.api.queue.FailedTask;
import io.nitric.api.queue.Queues;
import io.nitric.api.queue.ReceivedTask;
import io.nitric.api.queue.Task;

public class MockQueuesTest {

    @Test
    public void test_init() {
        var mq = new MockQueues();
        assertNotNull(mq);
        assertNotNull(mq.getMock());
    }

    @Test
    public void test_whenSendTask() {
        var mq = new MockQueues();

        var task = Task.newBuilder()
            .id("id")
            .payloadType("application/json")
            .payload(Map.of("status", "ready"))
            .build();
        var failedTask1 = FailedTask.newBuilder()
            .task(task)
            .message("Failed to queue task")
            .build();

        mq.whenSendTask(failedTask1);

        var queue = Queues.queue("queue");
        var failedTask2 = queue.send(task);
        assertNotNull(failedTask2);
        assertEquals(failedTask1.getTask().getId(), failedTask2.getTask().getId());

        Mockito.verify(mq.getMock(), Mockito.times(1)).sendBatch(Mockito.any());

        mq.whenSendTask(null);

        var failedTask3 = queue.send(task);
        assertNull(failedTask3);

        Mockito.verify(mq.getMock(), Mockito.times(2)).sendBatch(Mockito.any());
    }

    @Test
    public void test_whenSendTaskError() {
        var mq = new MockQueues();

        var task = Task.newBuilder()
            .id("id")
            .payloadType("application/json")
            .payload(Map.of("status", "ready"))
            .build();

        mq.whenSendTaskError(Status.INTERNAL);

        var queue = Queues.queue("queue");
        try {
            queue.send(task);
            fail();
        } catch (NitricException ne) {
            Mockito.verify(mq.getMock(), Mockito.times(1)).sendBatch(Mockito.any());
        }
    }

    @Test
    public void test_whenSendBatch() {
        var mq = new MockQueues();

        var task = Task.newBuilder()
            .id("id")
            .payloadType("application/json")
            .payload(Map.of("status", "ready"))
            .build();
        var taskList = List.of(task);

        var failedTask1 = FailedTask.newBuilder()
            .task(task)
            .message("Failed to queue task")
            .build();
        var failedTaskList1 = List.of(failedTask1);

        mq.whenSendBatch(failedTaskList1);

        var queue = Queues.queue("queue");
        var failedTaskList2 = queue.sendBatch(taskList);

        assertNotNull(failedTaskList2);
        assertEquals(1, failedTaskList2.size());
        assertEquals(task.getId(), failedTaskList2.get(0).getTask().getId());

        Mockito.verify(mq.getMock(), Mockito.times(1)).sendBatch(Mockito.any());

        mq.whenSendBatch(Collections.emptyList());

        var failedTaskList3 = queue.sendBatch(taskList);

        assertNotNull(failedTaskList3);
        assertTrue(failedTaskList3.isEmpty());

        Mockito.verify(mq.getMock(), Mockito.times(2)).sendBatch(Mockito.any());
    }

    @Test
    public void test_whenSendBatchError() {
        var mq = new MockQueues();

        mq.whenSendBatchError(Status.INTERNAL);

        var task = Task.newBuilder()
            .id("id")
            .payloadType("application/json")
            .payload(Map.of("status", "ready"))
            .build();
        var taskList = List.of(task);

        var queue = Queues.queue("queue");
        try {
            queue.sendBatch(taskList);
            fail();
        } catch (NitricException ne) {
            Mockito.verify(mq.getMock(), Mockito.times(1)).sendBatch(Mockito.any());
        }
    }

    @Test
    public void test_whenReceive() {
        var mq = new MockQueues();

        var task = ReceivedTask.newReceivedTaskBuilder()
            .id("id")
            .payloadType("application/json")
            .payload(Map.of("status", "ready"))
            .leaseId(UUID.randomUUID().toString())
            .queue("queue")
            .build();
        var taskList = List.of(task);

        mq.whenReceive(taskList);

        var queue = Queues.queue("queue");
        var taskList2 = queue.receive(1);

        assertNotNull(taskList2);
        assertEquals(1, taskList2.size());
        assertEquals(task.getId(), taskList2.get(0).getId());

        Mockito.verify(mq.getMock(), Mockito.times(1)).receive(Mockito.any());
    }

    @Test
    public void test_whenReceiveError() {
        var mq = new MockQueues();

        mq.whenReceiveError(Status.INTERNAL);

        var queue = Queues.queue("queue");
        try {
            queue.receive(1);
            fail();
        } catch (NitricException ne) {
            Mockito.verify(mq.getMock(), Mockito.times(1)).receive(Mockito.any());
        }
    }

    @Test
    public void test_whenComplete() {
        var mq = new MockQueues();

        mq.whenComplete();

        var task = ReceivedTask.newReceivedTaskBuilder()
            .id("id")
            .payloadType("application/json")
            .payload(Map.of("status", "ready"))
            .leaseId(UUID.randomUUID().toString())
            .queue("queue")
            .build();

        task.complete();

        Mockito.verify(mq.getMock(), Mockito.times(1)).complete(Mockito.any());
    }

    @Test
    public void test_whenCompleteError() {
        var mq = new MockQueues();

        mq.whenCompleteError(Status.INTERNAL);

        var task = ReceivedTask.newReceivedTaskBuilder()
            .id("id")
            .payloadType("application/json")
            .payload(Map.of("status", "ready"))
            .leaseId(UUID.randomUUID().toString())
            .queue("queue")
            .build();

        try {
            task.complete();
            fail();
        } catch (NitricException ne) {
            Mockito.verify(mq.getMock(), Mockito.times(1)).complete(Mockito.any());
        }
    }

}
