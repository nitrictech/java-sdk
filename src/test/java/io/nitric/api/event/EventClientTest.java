package io.nitric.api.event;

import io.nitric.proto.event.v1.EventPublishRequest;
import io.nitric.proto.event.v1.EventPublishResponse;
import io.nitric.proto.event.v1.MockEventBlockingStub;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EventClientTest {

    @Test public void test_build() {
        var client = EventClient.newBuilder().build();

        assertNotNull(client);
        assertNotNull(client.serviceStub);
    }

    @Test public void test_publish() {
        var mock = new MockEventBlockingStub() {
            @Override
            public EventPublishResponse publish(EventPublishRequest request) {
                assertNotNull(request);
                assertNotNull(request.getEvent());
                assertNotNull(request.getTopic());
                return null;
            }
        };
        var client = EventClient.newBuilder().serviceStub(mock).build();

        var event = Event.build(Map.of("name", "value"));

        client.publish("topic", event);

        try {
            client.publish(null, event);

        } catch (NullPointerException npe) {
            assertEquals("topic parameter is required", npe.getMessage());
        }

        try {
            client.publish("topic", null);

        } catch (NullPointerException npe) {
            assertEquals("event parameter is required", npe.getMessage());
        }
    }

}
