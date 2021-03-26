package io.nitric.api.event;

import java.util.Objects;

/**
 * Provides an Event API topic class.
 *
 * @since 1.0
 */
public class Topic {

    final String name;

    /*
     * Enforce builder pattern.
     */
    Topic(Builder builder) {
        this.name = builder.name;
    }

    /**
     * @return the topic name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + "]";
    }

    /**
     * @return a new Topic builder object
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Create a new Topic for the given name.
     *
     * @param name the topic name (required)
     * @return a new Topic for the given name
     */
    public static Topic build(String name) {
        return new Builder().name(name).build();
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a Topic builder.
     *
     * @since 1.0
     */
    public static class Builder {

        String name;

        /*
         * Enforce builder pattern.
         */
        Builder() {
        }

        /**
         * Set the topic name.
         *
         * @param name the topic name
         * @return the builder object
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return a new Event object
         */
        public Topic build() {
            Objects.requireNonNull(name, "name parameter is required");
            return new Topic(this);
        }
    }

}
