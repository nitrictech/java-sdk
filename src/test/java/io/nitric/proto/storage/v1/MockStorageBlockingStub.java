package io.nitric.proto.storage.v1;

import io.grpc.CallOptions;
import io.nitric.util.GrpcChannelProvider;

public class MockStorageBlockingStub extends StorageGrpc.StorageBlockingStub {

    protected MockStorageBlockingStub(io.grpc.Channel channel, CallOptions callOptions) {
        super(channel, callOptions);
    }

    public MockStorageBlockingStub() {
        this(GrpcChannelProvider.getChannel(), CallOptions.DEFAULT);
    }

    @Override
    public StorageReadResponse read(StorageReadRequest request) {
        return null;
    }

    @Override
    public StorageWriteResponse write(StorageWriteRequest request) {
        return null;
    }

    @Override
    public StorageDeleteResponse delete(StorageDeleteRequest request) {
        return null;
    }

}
