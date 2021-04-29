package io.nitric.proto.queue.v1;

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
