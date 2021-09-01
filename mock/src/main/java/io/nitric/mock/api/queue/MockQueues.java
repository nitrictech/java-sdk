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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.mockito.Mockito;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.nitric.api.queue.FailedTask;
import io.nitric.api.queue.Queues;
import io.nitric.api.queue.ReceivedTask;
import io.nitric.proto.queue.v1.NitricTask;
import io.nitric.proto.queue.v1.QueueCompleteResponse;
import io.nitric.proto.queue.v1.QueueReceiveResponse;
import io.nitric.proto.queue.v1.QueueSendBatchResponse;
import io.nitric.proto.queue.v1.QueueServiceGrpc;
import io.nitric.util.Contracts;
import io.nitric.util.ProtoUtils;

/**
 * <p>
 *  Provides a Nitric Queue Service API Mockito helper class.
 * </p>
 */
public class MockQueues {

    QueueServiceGrpc.QueueServiceBlockingStub mock;

    /**
     * Create a new MockQueues object.
     */
    public MockQueues() {
        mock = Mockito.mock(QueueServiceGrpc.QueueServiceBlockingStub.class);
        Queues.setServiceStub(mock);
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the Mockito QueueService stub.
     *
     * @return the Mockito QueueService stub
     */
    public QueueServiceGrpc.QueueServiceBlockingStub getMock() {
        return mock;
    }

    /**
     * Specify what to return when the QueueService SentBatch method is invoked.
     * Please note internally this method uses the QueueService SentBatch method
     * rather than QueueService SentTask method.
     *
     * @param failedTask the optional failed task response
     * @return the MockQueues object
     */
    public MockQueues whenSendTask(FailedTask failedTask) {
        var failedTaskList = new ArrayList<FailedTask>();
        if (failedTask != null) {
            failedTaskList.add(failedTask);
        }

        whenSendBatch(failedTaskList);

        return this;
    }

    /**
     * Specify the error to throw when the QueueService SentBatch method is invoked.
     * Please note internally this method uses the QueueService SentBatch method
     * rather than QueueService SentTask method.
     *
     * @param status the GRPC error status (required)
     * @return the MockQueues object
     */
    public MockQueues whenSendTaskError(Status status) {
        Contracts.requireNonNull(status, "status");

        Mockito.when(mock.sendBatch(Mockito.any())).thenThrow(
                new StatusRuntimeException(status)
        );

        return this;
    }

    /**
     * Specify what to return when the QueueService SentBatch method is invoked.
     *
     * @param failedTasks the response list of optinally failed tasks (required)
     * @return the MockQueues object
     */
    public MockQueues whenSendBatch(List<FailedTask> failedTasks) {
        Contracts.requireNonNull(failedTasks, "failedTasks");

        final var sendBatchResponse = QueueSendBatchResponse.newBuilder();

        if (failedTasks != null) {
            failedTasks.forEach(ft -> {

                var task = ft.getTask();

                var nt = NitricTask.newBuilder()
                    .setId(task.getId())
                    .setLeaseId(UUID.randomUUID().toString())
                    .setPayloadType(task.getPayloadType())
                    .setPayload(ProtoUtils.toStruct(task.getPayload()))
                    .build();

                var failedTask = io.nitric.proto.queue.v1.FailedTask
                    .newBuilder()
                    .setTask(nt)
                    .setMessage("Failed to queue task")
                    .build();

                sendBatchResponse.addFailedTasks(failedTask);
            });
        }

        Mockito.when(mock.sendBatch(Mockito.any())).thenReturn(
            sendBatchResponse.build()
        );

        return this;
    }

    /**
     * Specify the error to throw when the QueueService SentBatch method is invoked.
     *
     * @param status the GRPC error status (required)
     * @return the MockQueues object
     */
    public MockQueues whenSendBatchError(Status status) {
        Contracts.requireNonNull(status, "status");

        Mockito.when(mock.sendBatch(Mockito.any())).thenThrow(
                new StatusRuntimeException(status)
        );

        return this;
    }

    /**
     * Specify what to return when the QueueService Receive method is invoked.
     *
     * @param receivedTasks the response list of received tasks (required)
     * @return the MockQueues object
     */
    public MockQueues whenReceive(List<ReceivedTask> receivedTasks) {
        Contracts.requireNonNull(receivedTasks, "receivedTasks");

        final var receiveResponse = QueueReceiveResponse.newBuilder();

        receivedTasks.forEach(rt -> {

            var payload = ProtoUtils.toStruct(rt.getPayload());

            var nt = NitricTask.newBuilder()
                .setId(rt.getId())
                .setLeaseId(UUID.randomUUID().toString())
                .setPayloadType(rt.getPayloadType())
                .setPayload(payload)
                .build();

            receiveResponse.addTasks(nt);
        });

        Mockito.when(mock.receive(Mockito.any())).thenReturn(
            receiveResponse.build()
        );

        return this;
    }

    /**
     * Specify the error to throw when the QueueService Receive method is invoked.
     *
     * @param status the GRPC error status (required)
     * @return the MockQueues object
     */
    public MockQueues whenReceiveError(Status status) {
        Contracts.requireNonNull(status, "status");

        Mockito.when(mock.receive(Mockito.any())).thenThrow(
                new StatusRuntimeException(status)
        );

        return this;
    }

    /**
     * Specify what to return when the QueueService Complete method is invoked.
     *
     * @return the MockQueues object
     */
    public MockQueues whenComplete() {
        Mockito.when(mock.complete(Mockito.any())).thenReturn(
            QueueCompleteResponse.newBuilder().build()
        );

        return this;
    }

    /**
     * Specify the error to throw when the QueueService Complete method is invoked.
     *
     * @param status the GRPC error status
     * @return the MockQueues object
     */
    public MockQueues whenCompleteError(Status status) {
        Contracts.requireNonNull(status, "status");

        Mockito.when(mock.complete(Mockito.any())).thenThrow(
                new StatusRuntimeException(status)
        );

        return this;
    }

}
