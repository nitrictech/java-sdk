package io.nitric.api.event;

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

import io.nitric.proto.event.v1.TopicGrpc;
import io.nitric.proto.event.v1.TopicListRequest;
import io.nitric.util.GrpcChannelProvider;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  Provides an Event API Topic client.
 * </p>
 *
 * <p>
 *  The example below illustrates the Topic API.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.Topic;
 *  import io.nitric.api.TopicClient;
 *  ...
 *
 *  var client = TopicClient.newBuilder().build();
 *
 *  List&lt;Topic&gt; topics = client.list();
 * </code></pre>
 *
 * @see Topic
 * @see Event
 * @see EventClient
 *
 * @since 1.0
 */
public class TopicClient {

    final TopicGrpc.TopicBlockingStub serviceStub;

    /*
     * Enforce builder pattern.
     */
    TopicClient(TopicClient.Builder builder) {
        this.serviceStub = builder.serviceStub;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * List the available event topics.
     *
     * @return the list of available topics
     */
    public List<Topic> list() {

        var request = TopicListRequest.newBuilder().build();

        var response = serviceStub.list(request);

        return response.getTopicsList()
                .stream()
                .map(topic -> Topic.build(topic.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Create an new TopicClient builder.
     *
     * @return new TopicClient builder
     */
    public static TopicClient.Builder newBuilder() {
        return new TopicClient.Builder();
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[serviceStub=" + serviceStub + "]";
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a TopicClient Builder.
     */
    public static class Builder {

        TopicGrpc.TopicBlockingStub serviceStub;

        /*
         * Enforce builder pattern.
         */
        Builder() {
        }

        /**
         * Set  the GRPC service stub.
         *
         * @param serviceStub the GRPC service stub to inject
         * @return the TopicClient builder
         */
        public TopicClient.Builder serviceStub(TopicGrpc.TopicBlockingStub serviceStub) {
            this.serviceStub = serviceStub;
            return this;
        }

        /**
         * @return build a new TopicClient
         */
        public TopicClient build() {
            if (serviceStub == null) {
                var channel = GrpcChannelProvider.getChannel();
                this.serviceStub = TopicGrpc.newBlockingStub(channel);
            }

            return new TopicClient(this);
        }
    }
}
