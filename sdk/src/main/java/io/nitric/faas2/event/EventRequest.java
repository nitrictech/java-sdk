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

package io.nitric.faas2.event;

import java.nio.charset.StandardCharsets;

/**
 * Provides a Event request object.
 */
public class EventRequest {

    final String topic;
    final String mimeType;
    final byte[] data;

    // Constructors -----------------------------------------------------------

    /**
     * Create a new EventRequest object.
     *
     * @param topic the event topic
     * @param data the event request data
     */
    public EventRequest(String topic, String mimeType, byte[] data) {
        this.topic = topic;
        this.mimeType = mimeType;
        this.data = data;
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
     * Return the trigger mime type.
     *
     * @return the trigger mime type
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Get the trigger data.
     *
     * @return the data of the trigger
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * Get the trigger data as UTF-8 encode text, or empty string if not defined.
     *
     * @return the trigger data as UTF-8 encode text, or empty string if not defined
     */
    public String getDataAsText() {
        return (getData() != null) ? new String(getData(), StandardCharsets.UTF_8) : "";
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        String dataSample = "null";
        if (data != null) {
            dataSample = getDataAsText();
            if (dataSample.length() > 40) {
                dataSample = dataSample.substring(0, 40) + "...";
            }
        }
        return getClass().getSimpleName()
            + "[topic=" + topic
            + ", mimeType=" + mimeType
            + ", data=" + dataSample
            + "]";
    }

}