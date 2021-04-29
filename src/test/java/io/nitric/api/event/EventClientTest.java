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
