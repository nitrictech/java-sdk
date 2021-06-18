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
 * TopicResponseContext
 * <p>Provides additional response metadata for a topic trigger</p>
 * @see TopicTriggerContext
 */
public class TopicResponseContext extends ResponseContext {
    private boolean success = true;

    /**
     * @return The success status of the event processing
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success Set the status for processing this topic trigger, false will cause the event to be re-queued
     */
    public TopicResponseContext setSuccess(boolean success) {
        this.success = success;
        return this;
    }

}
