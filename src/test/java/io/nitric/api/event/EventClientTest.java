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

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EventClientTest {

    @Test public void test_build() {
        var client = EventClient.newBuilder().build();

        assertNotNull(client);
        assertNotNull(client.serviceStub);
    }

    @Test
    public void test_publish() {
        var mock = Mockito.mock(EventServiceGrpc.EventServiceBlockingStub.class);

        Mockito.when(mock.publish(Mockito.any())).thenReturn(
                EventPublishResponse.newBuilder().setId("test-id").build()
        );

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
