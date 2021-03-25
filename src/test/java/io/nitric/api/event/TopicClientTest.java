package io.nitric.api.event;

import io.nitric.proto.event.v1.MockTopicBlockingStub;
import io.nitric.proto.event.v1.NitricTopic;
import io.nitric.proto.event.v1.TopicListRequest;
import io.nitric.proto.event.v1.TopicListResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TopicClientTest {

    @Test public void test_build() {
        var client = TopicClient.newBuilder().build();

        assertNotNull(client);
        assertNotNull(client.serviceStub);
    }

    @Test public void test_list() {
        var mock = new MockTopicBlockingStub() {
            @Override
            public TopicListResponse list(TopicListRequest request) {
                return TopicListResponse.newBuilder()
                        .addTopics(NitricTopic.newBuilder().setName("topic1").build())
                        .addTopics(NitricTopic.newBuilder().setName("topic2").build())
                        .build();
            }
        };
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
