package io.nitric.proto.kv.v1;

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
