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

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.grpc.Status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.nitric.api.NitricException;
import io.nitric.api.event.Event;
import io.nitric.api.event.Events;

public class MockEventsTest {

    @Test
    public void test_init() {
        var me = new MockEvents();
        assertNotNull(me);
        assertNotNull(me.getEventMock());
        assertNotNull(me.getTopicMock());
    }

    @Test
    public void test_whenList() {
        var me = new MockEvents();

        me.whenList(Collections.emptyList());

        var topics1 = Events.topics();
        assertNotNull(topics1);
        assertTrue(topics1.isEmpty());

        Mockito.verify(me.getTopicMock(), Mockito.times(1)).list(Mockito.any());

        var topicList = List.of("topic-a", "topic-b", "topic-c");
        me.whenList(topicList);

        var topics2 = Events.topics();
        assertNotNull(topics2);
        assertEquals(3, topics2.size());
        assertEquals(topicList.get(0), topics2.get(0).getName());
        assertEquals(topicList.get(1), topics2.get(1).getName());
        assertEquals(topicList.get(2), topics2.get(2).getName());

        Mockito.verify(me.getTopicMock(), Mockito.times(2)).list(Mockito.any());
    }

    @Test
    public void test_whenListError() {
        var me = new MockEvents();

        me.whenListError(Status.INTERNAL);

        try {
            Events.topics();
            fail();
        } catch (NitricException ne) {
            Mockito.verify(me.getTopicMock(), Mockito.times(1)).list(Mockito.any());
        }
    }

    @Test
    public void test_whenPublish() {
        var me = new MockEvents();

        me.whenPublish();

        var event = Event.build(Map.of("key", "value"));
        Events.topic("topic").publish(event);

        Mockito.verify(me.getEventMock(), Mockito.times(1)).publish(Mockito.any());
    }

    @Test
    public void test_whenPublishError() {
        var me = new MockEvents();

        me.whenPublishError(Status.INTERNAL);

        var event = Event.build(Map.of("key", "value"));
        var topic = Events.topic("topic");
        try {
            topic.publish(event);
            fail();
        } catch (NitricException ne) {
            Mockito.verify(me.getEventMock(), Mockito.times(1)).publish(Mockito.any());
        }
    }

}
