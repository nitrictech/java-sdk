/*-
 * #%L
 * Nitric Java SDK
 * %%
 * Copyright (C) 2021 Nitric Technologies Pty Ltd
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

package io.nitric.api.event;

import java.util.List;
import java.util.stream.Collectors;

import io.nitric.api.exception.ApiException;
import io.nitric.proto.event.v1.EventServiceGrpc;
import io.nitric.proto.event.v1.EventServiceGrpc.EventServiceBlockingStub;
import io.nitric.proto.event.v1.TopicListRequest;
import io.nitric.proto.event.v1.TopicListResponse;
import io.nitric.proto.event.v1.TopicServiceGrpc;
import io.nitric.proto.event.v1.TopicServiceGrpc.TopicServiceBlockingStub;
import io.nitric.util.Contracts;
import io.nitric.util.GrpcChannelProvider;
import io.nitric.util.ProtoUtils;

/**
 * <p>
 *  Provides an Event API client.
 * </p>
 *
 * <p>
 *  The example below illustrates the Event API.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.Event;
 *  import io.nitric.api.Events;
 *
 *  // Create an order completed event
 *  var payload = Map.of("id", id, "status", "completed");
 *  var event = Event.build(payload);
 *
 *  // Publish the event to the orders topic
 *  Events.topic("orders").publish(event);
 *
 *  // Get the list of available topics
 *  List&lt;Topic&gt; topics = Events.topics();
 * </code></pre>
 *
 * @see Event
 * @see Topic
 */
public class Events {

    static EventServiceGrpc.EventServiceBlockingStub eventServiceStub;
    static TopicServiceGrpc.TopicServiceBlockingStub topicServiceStub;

    /*
     * Enforce package builder patterns.
     */
    private Events() {
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return a new event topic with the given name.
     *
     * @param name the event topic name (required)
     * @return a new event topic with the given name
     */
    public static Topic topic(String name) {
        Contracts.requireNonBlank(name, "name");

        return new Topic(name);
    }

    /**
     * List the available event topics.
     *
     * @return the list of available topics
     */
    public static List<Topic> topics() {

        var request = TopicListRequest.newBuilder().build();

        TopicListResponse response = null;
        try {
            response = getTopicServiceStub().list(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw ApiException.fromGrpcServiceException(sre);
        }

        return response.getTopicsList()
                .stream()
                .map(topic -> new Topic(topic.getName()))
                .collect(Collectors.toList());
    }

    // Package Private Methods ------------------------------------------------

    static EventServiceBlockingStub getEventServiceStub() {
        if (eventServiceStub == null) {
            var channel = GrpcChannelProvider.getChannel();
            eventServiceStub = EventServiceGrpc.newBlockingStub(channel);
        }
        return eventServiceStub;
    }

    static void setEventServiceStub(EventServiceBlockingStub stub) {
        eventServiceStub = stub;
    }

    static TopicServiceBlockingStub getTopicServiceStub() {
        if (topicServiceStub == null) {
            var channel = GrpcChannelProvider.getChannel();
            topicServiceStub = TopicServiceGrpc.newBlockingStub(channel);
        }
        return topicServiceStub;
    }

    static void setTopicServiceStub(TopicServiceBlockingStub stub) {
        topicServiceStub = stub;
    }

}
