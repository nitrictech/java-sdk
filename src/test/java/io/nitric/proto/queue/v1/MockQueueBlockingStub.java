package io.nitric.proto.queue.v1;

import io.grpc.CallOptions;
import io.nitric.proto.event.v1.EventGrpc;
import io.nitric.proto.event.v1.EventPublishRequest;
import io.nitric.proto.event.v1.EventPublishResponse;
import io.nitric.util.GrpcChannelProvider;

public class MockQueueBlockingStub extends QueueGrpc.QueueBlockingStub {

    protected MockQueueBlockingStub(io.grpc.Channel channel, CallOptions callOptions) {
        super(channel, callOptions);
    }

    public MockQueueBlockingStub() {
        this(GrpcChannelProvider.getChannel(), CallOptions.DEFAULT);
    }

    @Override
    public QueueSendResponse send(QueueSendRequest request) {
        return null;
    }

    @Override
    public QueueSendBatchResponse sendBatch(QueueSendBatchRequest request) {
        return null;
    }

    @Override
    public QueueReceiveResponse receive(QueueReceiveRequest request) {
        return null;
    }

    @Override
    public QueueCompleteResponse complete(QueueCompleteRequest request) {
        return null;
    }

}
