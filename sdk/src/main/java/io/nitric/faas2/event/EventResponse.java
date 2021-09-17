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

import io.nitric.util.Contracts;

/**
 * Provides a Event response class.
 */
public class EventResponse {

    boolean success = true;
    byte[] data;

    // Constructors -----------------------------------------------------------

    /**
     * Create a new EventResponse object.
     */
    public EventResponse() {
    }

    /**
     * Create a new EventResponse object.
     */
    public EventResponse(EventResponse response) {
        success = response.isSuccess();
        data = response.data;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return The success status of the event processing
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Set the status for processing this topic trigger, false will cause the event to be re-queued.
     *
     * @param success the status for processing this topic trigger, false will cause the event to be re-queued
     * @return this EventResponse object
     */
    public EventResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    /**
     * Return the response data, or null if not defined.
     *
     * @return the response data, or null if not defined
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Get the data contained in the response as UTF-8 encode text, or null if not define.
     *
     * @return the response data as UTF-8 encoded text, or null if not defined
     */
    public String getDataAsText() {
        return (data != null) ? new String(data, StandardCharsets.UTF_8) : null;
    }

    /**
     * Set the data for this response.
     *
     * @param data The data as an array of bytes
     * @return this EventResponse object
     */
    public EventResponse setData(byte[] data) {
        this.data = data;
        return this;
    }

    /**
     * Set the data for this response as UTF-8 encoded text.
     *
     * @param text the UTF-8 encode text to set as the data
     * @return this EventResponse object
     */
    public EventResponse setDataAsText(String text) {
        Contracts.requireNonNull(text, "text");
        this.data = text.getBytes(StandardCharsets.UTF_8);
        return this;
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
            + "[success=" + success
            + ", data=" + dataSample
            + "]";
    }

}
