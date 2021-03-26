package io.nitric.api.queue;

import java.util.Objects;

/**
 * <p>
 *  Provides a Queue API failed task class.
 * </p>
 *
 * @see Task
 *
 * @since 1.0
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
            Objects.requireNonNull(task, "task parameter is required");
            return new FailedTask(this);
        }
    }
}
