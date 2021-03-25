package io.nitric.api.queue;

import io.nitric.proto.queue.v1.*;
import io.nitric.util.GrpcChannelProvider;
import io.nitric.util.ProtoUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  Provides a Queue API client.git 
 * </p>
 *
 * <p>
 *  The example below illustrates the Queue API.
 * </p>
 *
 * <pre>
 *  // TODO...
 * </pre>
 *
 * @see NitricTask
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
    public void send(NitricTask task) {
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
    public List<FailedTask> sendBatch(List<NitricTask> tasks) {
        Objects.requireNonNull(tasks, "tasks parameter is required");

        if (tasks.isEmpty()) {
            return Collections.emptyList();
        }

        var requestBuilder = QueueSendBatchRequest.newBuilder().setQueue(queue);
        for (NitricTask task : tasks) {
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
    public List<NitricTask> receive(int limit) {
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

    io.nitric.proto.queue.v1.NitricTask toProtoTask(NitricTask task) {
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

    NitricTask toApiTask(io.nitric.proto.queue.v1.NitricTask task) {
        return NitricTask.newBuilder()
                .id(task.getId())
                .leaseId(task.getLeaseId())
                .payloadType(task.getPayloadType())
                .payload(ProtoUtils.toMap(task.getPayload()))
                .build();
    }

    FailedTask toApiFailedTask(io.nitric.proto.queue.v1.FailedTask protoFailedTask) {
        io.nitric.proto.queue.v1.NitricTask protoTask = protoFailedTask.getTask();

        var task = NitricTask.newBuilder()
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
         * Set  the GRPC service stub for mock testing.
         *
         * @param serviceStub the GRPC service stub to inject
         * @return the builder object
         */
        Builder serviceStub(QueueGrpc.QueueBlockingStub serviceStub) {
            this.serviceStub = serviceStub;
            return this;
        }

        /**
         * @return build a new QueueClient
         */
        public QueueClient build() {
            Objects.requireNonNull(queue, "queue parameter not specified");
            if (serviceStub == null) {
                var channel = GrpcChannelProvider.getChannel();
                this.serviceStub = QueueGrpc.newBlockingStub(channel);
            }

            return new QueueClient(this);
        }
    }

}
