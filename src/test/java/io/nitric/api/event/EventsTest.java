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

import io.nitric.proto.event.v1.EventPublishResponse;
import io.nitric.proto.event.v1.EventServiceGrpc;
import io.nitric.proto.event.v1.EventServiceGrpc.EventServiceBlockingStub;
import io.nitric.proto.event.v1.NitricTopic;
import io.nitric.proto.event.v1.TopicListRequest;
import io.nitric.proto.event.v1.TopicListResponse;
import io.nitric.proto.event.v1.TopicServiceGrpc;
import io.nitric.proto.event.v1.TopicServiceGrpc.TopicServiceBlockingStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EventsTest {

    @Test
    public void test_eventServiceStub() {
        Events.setEventServiceStub(null);
        assertNotNull(Events.getEventServiceStub());

        var mock = Mockito.mock(EventServiceBlockingStub.class);

        Events.setEventServiceStub(mock);
        assertEquals(mock, Events.getEventServiceStub());
    }

    @Test
    public void test_topicServiceStub() {
        assertNotNull(Events.getTopicServiceStub());

        var mock = Mockito.mock(TopicServiceBlockingStub.class);

        Events.setTopicServiceStub(mock);
        assertEquals(mock, Events.getTopicServiceStub());
    }

    @Test
    public void test_topic() {
        Topic topic = Events.topic("orders");

        assertNotNull(topic);
        assertEquals("orders", topic.name);
        assertEquals("orders", topic.getName());
        assertEquals("Topic[name=orders]", topic.toString());

        try {
            Events.topic(" ");
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-blank name", iae.getMessage());
        }
    }

    @Test
    public void test_topics() {
        var mock = Mockito.mock(TopicServiceGrpc.TopicServiceBlockingStub.class);

        Mockito.when(mock.list(TopicListRequest.newBuilder().build())).thenReturn(
                TopicListResponse.newBuilder()
                    .addTopics(NitricTopic.newBuilder().setName("topic1").build())
                    .addTopics(NitricTopic.newBuilder().setName("topic2").build())
                    .build()
        );
        Events.setTopicServiceStub(mock);

        var list = Events.topics();
        assertNotNull(list);
        assertEquals(2, list.size());

        var nitricTopic = list.get(0);
        assertEquals("topic1", nitricTopic.getName());

        nitricTopic = list.get(1);
        assertEquals("topic2", nitricTopic.getName());
    }

    @Test
    public void test_topic_publish() {
        var mock = Mockito.mock(EventServiceGrpc.EventServiceBlockingStub.class);

        Mockito.when(mock.publish(Mockito.any())).thenReturn(
                EventPublishResponse.newBuilder().setId("test-id").build()
        );
        Events.setEventServiceStub(mock);

        var event = Event.build(Map.of("name", "value"));
        Events.topic("topic").publish(event);

        try {
            Events.topic("topic").publish(null);
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-null event", iae.getMessage());
        }
    }

}
