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

package io.nitric.faas;

import java.nio.charset.StandardCharsets;

import io.nitric.util.Contracts;

/**
 * <p>
 *  Provides a Nitric FaaS Trigger inferface.
 * </p>
 *
 * @see Faas
 * @see AbstractTriggerContext
 * @see Response
 */
public interface Trigger {

    // Public Methods ---------------------------------------------------------

    /**
     * Return the context which raised the trigger.
     *
     * @return the context which raised the trigger
     */
    AbstractTriggerContext getContext();

    /**
     * Return the trigger data.
     *
     * @return Retrieve the data of the trigger
     */
    byte[] getData();

    /**
     * Get the trigger data as UTF-8 encode text, or null if not define
     *
     * @return the trigger data as UTF-8 encode text, or null if not define
     */
    default String getDataAsText() {
        return (getData() != null) ? new String(getData(), StandardCharsets.UTF_8) : null;
    }

    /**
     * Return the mime type of the trigger.
     *
     * @return Retrieve the mimeType of the trigger
     */
    String getMimeType();

    /**
     * Creates a default response object dependent on the context of the request.
     *
     * @return A default response with context matching this request
     */
    default Response buildResponse() {
        return buildResponse((byte[]) null);
    }

    /**
     * Creates a default response object dependent on the context of the request.
     *
     * @param data the response data bytes (required)
     * @return A default response with context matching this request containing the provided data
     */
    default Response buildResponse(byte[] data) {
        AbstractResponseContext responseCtx = null;

        if (getContext().isHttp()) {
            responseCtx = new HttpResponseContext();
        } else if (getContext().isTopic()) {
            responseCtx = new TopicResponseContext();
        }

        return new Response(data, responseCtx);
    }

    /**
     * Creates a default response object dependent on the context of the request.
     *
     * @param data the response text data (required)
     * @return A default response with context matching this request containing the provided data
     */
    default Response buildResponse(String data) {
        Contracts.requireNonBlank(data, "data");
        return buildResponse(data.getBytes(StandardCharsets.UTF_8));
    }

}