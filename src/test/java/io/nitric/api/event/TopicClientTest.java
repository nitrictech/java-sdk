package io.nitric.api.event;

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

import io.nitric.proto.event.v1.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TopicClientTest {

    @Test public void test_build() {
        var client = TopicClient.newBuilder().build();

        assertNotNull(client);
        assertNotNull(client.serviceStub);
    }

    @Test public void test_list() {
        var mock = Mockito.mock(TopicServiceGrpc.TopicServiceBlockingStub.class);

        Mockito.when(mock.list(TopicListRequest.newBuilder().build())).thenReturn(
                TopicListResponse.newBuilder()
                    .addTopics(NitricTopic.newBuilder().setName("topic1").build())
                    .addTopics(NitricTopic.newBuilder().setName("topic2").build())
                    .build()
        );

        var client = TopicClient.newBuilder().serviceStub(mock).build();

        var list = client.list();
        assertNotNull(list);
        assertEquals(2, list.size());

        var nitricTopic = list.get(0);
        assertEquals("topic1", nitricTopic.getName());

        nitricTopic = list.get(1);
        assertEquals("topic2", nitricTopic.getName());
    }

}
