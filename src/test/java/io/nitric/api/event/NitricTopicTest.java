package io.nitric.api.event;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NitricTopicTest {

    @Test
    public void test_build() {
        var topic = NitricTopic.newBuilder().name("topic").build();

        assertNotNull(topic);
        assertEquals("topic", topic.getName());
        assertEquals("NitricTopic[name=topic]", topic.toString());

        topic = NitricTopic.build("topic2");
        assertEquals("topic2", topic.getName());
        assertEquals("NitricTopic[name=topic2]", topic.toString());

        try {
            NitricTopic.newBuilder().build();
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("name parameter is required", npe.getMessage());
        }

        try {
            NitricTopic.build(null);
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("name parameter is required", npe.getMessage());
        }
    }

}
