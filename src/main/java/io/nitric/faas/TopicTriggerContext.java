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
 *
 */
public class TopicTriggerContext extends TriggerContext {

    private final String topic;

    /**
     * Creates a new TopicTriggerContext
     *
     * @param topic The name of the topic that raised the trigger
     */
    public TopicTriggerContext(String topic) {
        this.topic = topic;
    }

    /**
     * Retrieve the topic name for this trigger
     *
     * @return The name of the topic that raised the trigger
     */
    public String getTopic() {
        return topic;
    }
}
