package io.nitric.proto.event.v1;

import io.grpc.CallOptions;
import io.nitric.util.GrpcChannelProvider;

public class MockTopicBlockingStub extends TopicGrpc.TopicBlockingStub {

    protected MockTopicBlockingStub(io.grpc.Channel channel, CallOptions callOptions) {
        super(channel, callOptions);
    }

    public MockTopicBlockingStub() {
        this(GrpcChannelProvider.getChannel(), CallOptions.DEFAULT);
    }

    @Override
    public TopicListResponse list(TopicListRequest request) {
        return null;
    }

}
