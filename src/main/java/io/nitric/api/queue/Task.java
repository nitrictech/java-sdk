package io.nitric.api.queue;

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
 * <p>
 *  Provides a Queue API task class.
 * </p>
 *
 * @see FailedTask
 *
 * @since 1.0
 */
public class Task {

    final String id;
    final String leaseId;
    final String payloadType;
    final Map<String, Object> payload;

    /*
     * Enforce builder pattern.
     */
    Task(Builder builder) {
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
     * Return the lease id unique to the pop request, this must be used to complete, extend the lease or release the
     * task.
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
     * @return a new Task builder
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
     * Provides a Task builder class.
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
         * @return a new Task
         */
        public Task build() {
            Objects.requireNonNull(payload, "payload parameter is required");
            return new Task(this);
        }
    }
}
