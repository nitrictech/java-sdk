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

package io.nitric.mock.api.event;

import java.util.List;

import org.mockito.Mockito;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.nitric.api.event.Events;
import io.nitric.proto.event.v1.EventPublishResponse;
import io.nitric.proto.event.v1.EventServiceGrpc;
import io.nitric.proto.event.v1.NitricTopic;
import io.nitric.proto.event.v1.TopicListResponse;
import io.nitric.proto.event.v1.TopicServiceGrpc;
import io.nitric.util.Contracts;


/**
 * <p>
 *  Provides a Nitric Event Service API Mockito helper class.
 * </p>
 */
public class MockEvents {

    EventServiceGrpc.EventServiceBlockingStub eventMock;
    TopicServiceGrpc.TopicServiceBlockingStub topicMock;

    /**
     * Create a new MockEvents object.
     */
    public MockEvents() {
        eventMock = Mockito.mock(EventServiceGrpc.EventServiceBlockingStub.class);
        topicMock = Mockito.mock(TopicServiceGrpc.TopicServiceBlockingStub.class);
        Events.setEventServiceStub(eventMock);
        Events.setTopicServiceStub(topicMock);
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the Mockito EventService stub.
     *
     * @return the Mockito EventService stub
     */
    public EventServiceGrpc.EventServiceBlockingStub getEventMock() {
        return eventMock;
    }

    /**
     * Return the Mockito TopicService stub.
     *
     * @return the Mockito TopicService stub
     */
    public TopicServiceGrpc.TopicServiceBlockingStub getTopicMock() {
        return topicMock;
    }

    /**
     * Specify what to return when the TopicService List method is invoked.
     *
     * @param topics the result topic list (required)
     * @return the MockEvents object
     */
    public MockEvents whenList(List<String> topics) {
        Contracts.requireNonNull(topics, "topics");

        final var response = TopicListResponse.newBuilder();
        topics.forEach(topic -> {
            response.addTopics(NitricTopic.newBuilder().setName(topic).build());
        });

        Mockito.when(topicMock.list(Mockito.any())).thenReturn(
            response.build()
        );

        return this;
    }

    /**
     * Specify the error to throw when the TopicService List method is invoked.
     *
     * @param status the GRPC error status (required)
     * @return the MockEvents object
     */
    public MockEvents whenListError(Status status) {
        Contracts.requireNonNull(status, "status");

        Mockito.when(topicMock.list(Mockito.any())).thenThrow(
                new StatusRuntimeException(status)
        );

        return this;
    }

    /**
     * Enable the EventService Publish method for unit testing.
     *
     * @return the MockEvents object
     */
    public MockEvents whenPublish() {
        Mockito.when(eventMock.publish(Mockito.any())).thenReturn(
            EventPublishResponse.newBuilder().build()
        );

        return this;
    }

    /**
     * Specify the error to throw when the TopicService List method is invoked.
     *
     * @param status the GRPC error status (required)
     * @return the MockEvents object
     */
    public MockEvents whenPublishError(Status status) {
        Contracts.requireNonNull(status, "status");

        Mockito.when(eventMock.publish(Mockito.any())).thenThrow(
                new StatusRuntimeException(status)
        );

        return this;
    }

}
