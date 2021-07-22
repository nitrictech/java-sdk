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

import java.util.Map;
import java.util.Objects;

/**
 * Provides an Event message class.
 */
public class Event {

    final String id;
    final String payloadType;
    final Map<String, Object> payload;

    // Constructor ------------------------------------------------------------

    /*
     * Enforce builder pattern.
     */
    Event(Builder builder) {
        this.id = builder.id;
        this.payloadType = builder.payloadType;
        this.payload = builder.payload;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return the event id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the event payload type
     */
    public String getPayloadType() {
        return payloadType;
    }

    /**
     * @return the event payload
     */
    public Map<String, Object> getPayload() {
        return payload;
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[id=" + id
                + ", payloadType=" + payloadType
                + ", payload=" + payload
                + "]";
    }

    /**
     * @return a new Event builder object
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Create a new Event from the given payload.
     *
     * @param payload the event payload (required)
     * @return a new event object
     */
    public static Event build(Map<String, Object> payload) {
        return newBuilder().payload(payload).build();
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a Event builder.
     *
     * @since 1.0
     */
    public static class Builder {

        String id;
        String payloadType;
        Map<String, Object> payload;

        /*
         * Enforce builder pattern.
         */
        Builder() {
        }

        /**
         * Set the event id.
         *
         * @param id the event id
         * @return the builder object
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Set the event payload type.
         *
         * @param payloadType the event payloadType
         * @return the builder object
         */
        public Builder payloadType(String payloadType) {
            this.payloadType = payloadType;
            return this;
        }

        /**
         * Set the event payload.
         *
         * @param payload the event payload
         * @return the builder object
         */
        public Builder payload(Map<String, Object> payload) {
            this.payload = payload;
            return this;
        }

        /**
         * @return a new Event object
         */
        public Event build() {
            Objects.requireNonNull(payload, "payload parameter is required");
            return new Event(this);
        }
    }

}
