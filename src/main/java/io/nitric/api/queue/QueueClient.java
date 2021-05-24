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

import io.nitric.proto.queue.v1.QueueCompleteRequest;
import io.nitric.proto.queue.v1.QueueGrpc;
import io.nitric.proto.queue.v1.QueueReceiveRequest;
import io.nitric.proto.queue.v1.QueueSendBatchRequest;
import io.nitric.proto.queue.v1.QueueSendRequest;
import io.nitric.util.GrpcChannelProvider;
import io.nitric.util.ProtoUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  Provides a Queue API client.
 * </p>
 *
 * <p>
 *  The example below illustrates using the Queue API.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.queue.QueueClient;
 *  import io.nitric.api.queue.Task;
 *  ...
 *
 *  String orderId = ...
 *  String serialNumber = ...
 *
 *  var payload = Map.of("orderId", orderId, "serialNumber", serialNumber);
 *  var task = Task.build(payload);
 *
 *  // Send a task to the 'shipping' queue client
 *  var client = QueueClient.build("shipping");
 *  client.send(task);
 *
 *  // Receive a list of tasks from the 'shipping' queue
 *  List&lt;Task&gt; tasks = client.receive(100);
 * </code></pre>
 *
 * @see Task
 * @see FailedTask
 *
 * @since 1.0
 */
public class QueueClient {

    final String queue;
    final QueueGrpc.QueueBlockingStub serviceStub;

    /*
     * Enforce builder pattern.
     */
    QueueClient(Builder builder) {
        this.queue = builder.queue;
        this.serviceStub = builder.serviceStub;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Send the given task to the client queue.
     *
     * @param task the task to send to the queue (required)
     */
    public void send(Task task) {
        Objects.requireNonNull(task, "task parameter is required");

        var protoTask = toProtoTask(task);

        var request = QueueSendRequest.newBuilder()
                .setQueue(queue)
                .setTask(protoTask)
                .build();

        serviceStub.send(request);
    }

    /**
     * Send the given tasks to the client queue in a batch, and return any tasks which failed to send.
     *
     * @param tasks the list of task to send as a batch (required)
     * @return the list of tasks which failed to send, or an empty list if all were successfully sent
     */
    public List<FailedTask> sendBatch(List<Task> tasks) {
        Objects.requireNonNull(tasks, "tasks parameter is required");

        if (tasks.isEmpty()) {
            return Collections.emptyList();
        }

        var requestBuilder = QueueSendBatchRequest.newBuilder().setQueue(queue);
        for (Task task : tasks) {
            requestBuilder.addTasks(toProtoTask(task));
        }
        var request = requestBuilder.build();

        var response = serviceStub.sendBatch(request);

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
    public List<Task> receive(int limit) {
        var request = QueueReceiveRequest.newBuilder()
                .setQueue(queue)
                .setDepth(limit)
                .build();

        var response = serviceStub.receive(request);

        return response.getTasksList()
                .stream()
                .map(task -> toApiTask(task))
                .collect(Collectors.toList());
    }

    /**
     * Complete the task specified by the given lease id.
     *
     * @param leaseId the lease id of the task to complete (required)
     */
    public void complete(String leaseId) {
        Objects.requireNonNull(leaseId, "leaseId parameter is required");

        var request = QueueCompleteRequest.newBuilder()
                .setQueue(queue)
                .setLeaseId(leaseId)
                .build();

        serviceStub.complete(request);
    }

    /**
     * Create an new QueueClient builder.
     *
     * @return new QueueClient builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Return a new QueueClient with the specified queue name.
     *
     * @param queue the queue name (required)
     * @return a new QueueClient with the specified queue name
     */
    public static QueueClient build(String queue) {
        return newBuilder().queue(queue).build();
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[queue=" + queue + ", serviceStub=" + serviceStub + "]";
    }

    // Package Private Methods ------------------------------------------------

    io.nitric.proto.queue.v1.NitricTask toProtoTask(Task task) {
        var struct = ProtoUtils.toStruct(task.payload);

        var taskBuilder = io.nitric.proto.queue.v1.NitricTask.newBuilder().setPayload(struct);
        if (task.getId() != null) {
            taskBuilder.setId(task.getId());
        }
        if (task.getLeaseId() != null) {
            taskBuilder.setLeaseId(task.getLeaseId());
        }
        if (task.getPayloadType() != null) {
            taskBuilder.setPayloadType(task.getPayloadType());
        }

        return taskBuilder.build();
    }

    Task toApiTask(io.nitric.proto.queue.v1.NitricTask task) {
        return Task.newBuilder()
                .id(task.getId())
                .leaseId(task.getLeaseId())
                .payloadType(task.getPayloadType())
                .payload(ProtoUtils.toMap(task.getPayload()))
                .build();
    }

    FailedTask toApiFailedTask(io.nitric.proto.queue.v1.FailedTask protoFailedTask) {
        io.nitric.proto.queue.v1.NitricTask protoTask = protoFailedTask.getTask();

        var task = Task.newBuilder()
                .id(protoTask.getId())
                .leaseId(protoTask.getLeaseId())
                .payloadType(protoTask.getPayloadType())
                .payload(ProtoUtils.toMap(protoTask.getPayload()))
                .build();

        return FailedTask.newBuilder()
                .task(task)
                .message(protoFailedTask.getMessage())
                .build();
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a QueueClient Builder.
     */
    public static class Builder {

        String queue;
        QueueGrpc.QueueBlockingStub serviceStub;

        /*
         * Enforce builder pattern.
         */
        Builder() {
        }

        /**
         * Set the queue name.
         *
         * @param queue the queue name (required)
         * @return the builder object
         */
        public Builder queue(String queue) {
            this.queue = queue;
            return this;
        }

        /**
         * Set  the GRPC service stub.
         *
         * @param serviceStub the GRPC service stub to inject
         * @return the builder object
         */
        public Builder serviceStub(QueueGrpc.QueueBlockingStub serviceStub) {
            this.serviceStub = serviceStub;
            return this;
        }

        /**
         * @return build a new QueueClient
         */
        public QueueClient build() {
            Objects.requireNonNull(queue, "queue parameter is required");
            if (serviceStub == null) {
                var channel = GrpcChannelProvider.getChannel();
                this.serviceStub = QueueGrpc.newBlockingStub(channel);
            }

            return new QueueClient(this);
        }
    }

}
