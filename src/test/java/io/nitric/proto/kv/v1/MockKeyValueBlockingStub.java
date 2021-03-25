package io.nitric.proto.kv.v1;

import io.grpc.CallOptions;
import io.nitric.util.GrpcChannelProvider;

public class MockKeyValueBlockingStub extends KeyValueGrpc.KeyValueBlockingStub {

    protected MockKeyValueBlockingStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
        super(channel, callOptions);
    }

    public MockKeyValueBlockingStub() {
        this(GrpcChannelProvider.getChannel(), CallOptions.DEFAULT);
    }

    @Override
    protected KeyValueGrpc.KeyValueBlockingStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
        return null;
    }

    @Override
    public KeyValueGetResponse get(KeyValueGetRequest request) {
        return null;
    }

    @Override
    public KeyValuePutResponse put(KeyValuePutRequest request) {
        return null;
    }

    @Override
    public KeyValueDeleteResponse delete(KeyValueDeleteRequest request) {
        return null;
    }

}
