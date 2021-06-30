package io.nitric.faas;

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

/**
 * <p>
 *   Provides contextual response metadata for triggers raised by Topic events
 * </p>
 */
public final class TopicTriggerContext extends AbstractTriggerContext {

    private final String topic;

    // Constructors -----------------------------------------------------------

    /**
     * Creates a new TopicTriggerContext
     *
     * @param topic The name of the topic that raised the trigger
     */
    protected TopicTriggerContext(String topic) {
        this.topic = topic;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Retrieve the topic name for this trigger
     *
     * @return The name of the topic that raised the trigger
     */
    public String getTopic() {
        return topic;
    }

    /**
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[topic=" + topic + "]";
    }

}
