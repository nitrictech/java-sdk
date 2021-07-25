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

import io.nitric.util.Contracts;

/**
 * <p>
 *  Provides a Queue API failed task class.
 * </p>
 *
 * @see Task
 */
public class FailedTask {

    /** The task that failed to be pushed. */
    final Task task;

    /** A message describing the failure. */
    final String message;

    /*
     * Enforce builder pattern.
     */
    FailedTask(Builder builder) {
        this.task = builder.task;
        this.message = builder.message;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return the task which failed
     */
    public Task getTask() {
        return task;
    }

    /**
     * @return the task failure message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return a new FailedTask builder object
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[task=" + task + ", message=" + message + "]";
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a FailedTask builder class.
     *
     * @since 1.0
     */
    public static class Builder {

        Task task;
        String message;

        /*
         * Enforce builder pattern.
         */
        Builder() {
        }

        // Public Methods ------------------------------------------------------

        /**
         * Set the task which failed.
         *
         * @param task the task which failed (required)
         * @return the builder object
         */
        public Builder task(Task task) {
            this.task = task;
            return this;
        }

        /**
         * Set the task failure message.
         *
         * @param message the task failure message
         * @return the builder object
         */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * @return a new FailedTask object
         */
        public FailedTask build() {
            Contracts.requireNonNull(task, "task");
            return new FailedTask(this);
        }
    }
}
