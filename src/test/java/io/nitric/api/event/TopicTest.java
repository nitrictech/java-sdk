package io.nitric.api.event;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TopicTest {

    @Test
    public void test_build() {
        var topic = Topic.newBuilder().name("topic").build();

        assertNotNull(topic);
        assertEquals("topic", topic.getName());
        assertEquals("Topic[name=topic]", topic.toString());

        topic = Topic.build("topic2");
        assertEquals("topic2", topic.getName());
        assertEquals("Topic[name=topic2]", topic.toString());

        try {
            Topic.newBuilder().build();
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("name parameter is required", npe.getMessage());
        }

        try {
            Topic.build(null);
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("name parameter is required", npe.getMessage());
        }
    }

}
