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

import java.util.Objects;

/**
 * Provides an Topic class.
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
