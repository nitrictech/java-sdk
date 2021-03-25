package io.nitric.api.queue;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  Provides a Queue API task class.
 * </p>
 *
 * @see FailedTask
 *
 * @since 1.0
 */
public class NitricTask {

    final String id;
    final String leaseId;
    final String payloadType;
    final Map<String, Object> payload;

    /*
     * Enforce builder pattern.
     */
    NitricTask(Builder builder) {
        this.id = builder.id;
        this.leaseId = builder.leaseId;
        this.payloadType = builder.payloadType;
        this.payload = builder.payload;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return the task id
     */
    public String getId() {
        return id;
    }

    /**
     * Return the lease id unique to the pop request, this must be used to complete, extend the lease or release the task.
     *
     * @return the task lease id, unique to the pop request.
     */
    public String getLeaseId() {
        return leaseId;
    }

    /**
     * @return the task payload type
     */
    public String getPayloadType() {
        return payloadType;
    }

    /**
     * @return the task payload
     */
    public Map<String, Object> getPayload() {
        return payload;
    }

    /**
     * @return a new NitricTask builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[id=" + id
                + ", leaseId=" + leaseId
                + ", payloadType=" + payloadType
                + ", payload=" + payload
                + "]";
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a NitricTask builder class.
     *
     * @since 1.0
     */
    public static class Builder {

        String id;
        String leaseId;
        String payloadType;
        Map<String, Object> payload;

        /*
         * Enforce builder pattern.
         */
        Builder() {
        }

        // Public Methods -----------------------------------------------------

        /**
         * Set the task id.
         *
         * @param id the task id
         * @return the builder object
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Set the task lease id.
         *
         * @param leaseId the task lease id
         * @return the builder object
         */
        public Builder leaseId(String leaseId) {
            this.leaseId = leaseId;
            return this;
        }

        /**
         * Set the task payload type.
         *
         * @param payloadType the task payload type
         * @return the builder object
         */
        public Builder payloadType(String payloadType) {
            this.payloadType = payloadType;
            return this;
        }

        /**
         * Set the task payload.
         *
         * @param payload the task payload (required)
         * @return the builder object
         */
        public Builder payload(Map<String, Object> payload) {
            this.payload = payload;
            return this;
        }

        /**
         * @return a new NitricTask
         */
        public NitricTask build() {
            Objects.requireNonNull(payload, "payload parameter is required");
            return new NitricTask(this);
        }
    }
}
