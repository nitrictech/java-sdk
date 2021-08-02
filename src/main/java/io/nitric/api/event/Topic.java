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

import io.nitric.proto.event.v1.EventPublishRequest;
import io.nitric.util.Contracts;
import io.nitric.util.ProtoUtils;

/**
 * <p>
 *  Provides an Topic class.
 * </p>
 *
 * <p>
 *  The example below illustrates the Event API.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.Event;
 *  import io.nitric.api.Events;
 *
 *  // Create an order completed event
 *  var payload = Map.of("id", id, "status", "completed");
 *  var event = Event.build(payload);
 *
 *  // Publish the event to the orders topic
 *  Events.topic("orders").publish(event);
 * </code></pre>
 *
 * @see Events
 */
public class Topic {

    final String name;

    /*
     * Enforce builder pattern.
     */
    Topic(String name) {
        Contracts.requireNonBlank(name, "name");
        this.name = name;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the topic name.
     *
     * @return the topic name
     */
    public String getName() {
        return name;
    }

    /**
     * Publish the given event to this topic.
     *
     * @param event the even to publish (required)
     */
    public void publish(Event event) {
        Contracts.requireNonNull(event, "event");

        var struct = ProtoUtils.toStruct(event.payload);

        var eventBuilder = io.nitric.proto.event.v1.NitricEvent.newBuilder().setPayload(struct);
        if (event.getId() != null) {
            eventBuilder.setId(event.getId());
        }
        if (event.getPayloadType() != null) {
            eventBuilder.setPayloadType(event.getPayloadType());
        }
        var protoEvent = eventBuilder.build();

        var request = EventPublishRequest.newBuilder()
                .setTopic(this.name)
                .setEvent(protoEvent)
                .build();

        try {
            Events.getEventServiceStub().publish(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw ProtoUtils.mapGrpcError(sre);
        }
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + "]";
    }

}