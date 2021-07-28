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

package io.nitric.api.queue;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.nitric.proto.queue.v1.QueueReceiveRequest;
import io.nitric.proto.queue.v1.QueueReceiveResponse;
import io.nitric.proto.queue.v1.QueueSendBatchRequest;
import io.nitric.proto.queue.v1.QueueSendBatchResponse;
import io.nitric.util.Contracts;
import io.nitric.util.ProtoUtils;

/**
 * Provides a Queue API queue class.
 *
 * <p>
 *  The example below illustrates the Queue API.
 * </p>
 *
 * <pre><code class="code">
 * import io.nitric.api.queue.Queues;
 * import io.nitric.api.queue.Task;
 * import io.nitric.api.queue.ReceivedTask;
 * ...
 *
 * String orderId = ...
 * String serialNumber = ...
 *
 * var payload = Map.of("orderId", orderId, "serialNumber", serialNumber);
 * var task = Task.build(payload);
 *
 * // Send a task to the 'shipping' queue
 * var queue = Queues.queue("shipping");
 * queue.send(task);
 *
 * // Receive a list of tasks from the 'shipping' queue
 * List&lt;ReceivedTask&gt; tasks = queue.receive(100);
 *
 * // Complete the first shipping task
 * var shippingTask = tasks.get(0);
 * shippingTask.complete();
 * </code></pre>
 *
 * @see Queues
 */
public class Queue {

    final String name;

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    Queue(String name) {
        Contracts.requireNonBlank(name, "name");
        this.name = name;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the queue name.
     *
     * @return the queue name
     */
    public String getName() {
        return name;
    }

    /**
     * Send the given task to the client queue.
     *
     * @param task the task to send to the queue (required)
     * @return null if task successfully sent, or a failed task otherwise
     */
    public FailedTask send(Task task) {
        Contracts.requireNonNull(task, "task");

        var taskList = Collections.singletonList(task);

        var results = sendBatch(taskList);

        if (results.isEmpty()) {
            return null;

        } else {
            return results.get(0);
        }
    }

    /**
     * Send the given tasks to the client queue in a batch, and return any tasks which failed to send.
     *
     * @param tasks the list of task to send as a batch (required)
     * @return the list of tasks which failed to send, or an empty list if all were successfully sent
     */
    public List<FailedTask> sendBatch(List<Task> tasks) {
        Contracts.requireNonNull(tasks, "tasks");

        if (tasks.isEmpty()) {
            return Collections.emptyList();
        }

        var requestBuilder = QueueSendBatchRequest.newBuilder().setQueue(name);
        for (Task task : tasks) {
            requestBuilder.addTasks(toProtoTask(task));
        }
        var request = requestBuilder.build();

        QueueSendBatchResponse response = null;
        try {
            response = Queues.getServiceStub().sendBatch(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw ProtoUtils.mapGrpcError(sre);
        }

        return response.getFailedTasksList()
                .stream()
                .map(failedTask -> toApiFailedTask(failedTask))
                .collect(Collectors.toList());
    }

    /**
     * Return receive a maximum specified number of tasks from the queue.
     *
     * @param limit the maximum number of tasks to receive from the queue
     * @return the tasks from the client queue
     */
    public List<ReceivedTask> receive(int limit) {
        var request = QueueReceiveRequest.newBuilder()
                .setQueue(name)
                .setDepth(limit)
                .build();

        QueueReceiveResponse response = null;
        try {
            response = Queues.getServiceStub().receive(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw ProtoUtils.mapGrpcError(sre);
        }

        return response.getTasksList()
                .stream()
                .map(task -> toApiTask(task))
                .collect(Collectors.toList());
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + "]";
    }

    // Package Private Methods ------------------------------------------------

    io.nitric.proto.queue.v1.NitricTask toProtoTask(Task task) {
        var struct = ProtoUtils.toStruct(task.payload);

        var taskBuilder = io.nitric.proto.queue.v1.NitricTask.newBuilder().setPayload(struct);
        if (task.getId() != null) {
            taskBuilder.setId(task.getId());
        }
        if (task.getPayloadType() != null) {
            taskBuilder.setPayloadType(task.getPayloadType());
        }

        return taskBuilder.build();
    }

    ReceivedTask toApiTask(io.nitric.proto.queue.v1.NitricTask task) {

        return new ReceivedTask(
            task.getId(),
            task.getPayloadType(),
            ProtoUtils.toMap(task.getPayload()),
            task.getLeaseId(),
            getName());
    }

    FailedTask toApiFailedTask(io.nitric.proto.queue.v1.FailedTask protoFailedTask) {
        io.nitric.proto.queue.v1.NitricTask protoTask = protoFailedTask.getTask();

        var task = new ReceivedTask(
            protoTask.getId(),
            protoTask.getPayloadType(),
            ProtoUtils.toMap(protoTask.getPayload()),
            protoTask.getLeaseId(),
            getName());

        return FailedTask.newBuilder()
                .task(task)
                .message(protoFailedTask.getMessage())
                .build();
    }

}
