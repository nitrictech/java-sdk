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

import io.nitric.util.Contracts;

/**
 * <p>
 *  Provides a Queue API Task class.
 * </p>
 *
 * @see FailedTask
 * @see ReceivedTask
 */
public class Task {

    final String id;
    final String payloadType;
    final Map<String, Object> payload;

    /*
     * Enforce builder pattern.
     */
    Task(String id, String payloadType, Map<String, Object> payload) {
        this.id = id;
        this.payloadType = payloadType;
        this.payload = payload;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the task id.
     *
     * @return the task id
     */
    public String getId() {
        return id;
    }

    /**
     * Return the task payload type.
     *
     * @return the task payload type
     */
    public String getPayloadType() {
        return payloadType;
    }

    /**
     * Return the task payload.
     *
     * @return the task payload
     */
    public Map<String, Object> getPayload() {
        return payload;
    }

    /**
     * Return a new Task builder.
     *
     * @return a new Task builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Return the string representation of this object.
     *
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

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a Task builder class.
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
            Contracts.requireNonNull(payload, "payload");
            return new Task(id, payloadType, payload);
        }
    }

}
