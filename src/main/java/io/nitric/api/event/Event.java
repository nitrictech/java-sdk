package io.nitric.api.event;

import java.util.Map;
import java.util.Objects;

/**
 * Provides an Event API event class.
 *
 * @since 1.0
 */
public class Event {

    final String id;
    final String payloadType;
    final Map<String, Object> payload;

    /*
     * Enforce builder pattern.
     */
    Event(Builder builder) {
        this.id = builder.id;
        this.payloadType = builder.payloadType;
        this.payload = builder.payload;
    }

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
        return getClass().getSimpleName() +
                "[id=" + id
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
     * @return
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