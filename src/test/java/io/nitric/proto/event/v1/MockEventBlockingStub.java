package io.nitric.proto.event.v1;

import io.grpc.CallOptions;
import io.nitric.util.GrpcChannelProvider;

public class MockEventBlockingStub extends EventGrpc.EventBlockingStub {

    protected MockEventBlockingStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
        super(channel, callOptions);
    }

    public MockEventBlockingStub() {
        this(GrpcChannelProvider.getChannel(), CallOptions.DEFAULT);
    }

    @Override
    public EventPublishResponse publish(EventPublishRequest request) {
        return null;
    }
}
