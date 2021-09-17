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

/**
 * Provides a Event Context object.
 */
public class EventContext {

    final EventRequest request;
    final EventResponse response;

    /**
     * Provides a Event context object.
     *
     * @param request the event request
     * @param response the event response
     */
    public EventContext(EventRequest request, EventResponse response) {
        this.request = request;
        this.response = response;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the Event request object.
     *
     * @return the Event request object
     */
    public EventRequest getRequest() {
        return request;
    }

    /**
     * Return the Event response object.
     *
     * @return the Event response object
     */
    public EventResponse getResponse() {
        return response;
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
            + "[request=" + request
            + ", response=" + response
            + "]";
    }

}
