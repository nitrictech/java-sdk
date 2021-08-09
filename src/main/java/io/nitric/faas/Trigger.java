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
     * @return Retrieves the context that raised the trigger
     */
    public AbstractTriggerContext getContext();

    /**
     * @return Retrieve the data of the trigger
     */
    public byte[] getData();

    /**
     * @return Retrieve the mimeType of the trigger
     */
    public String getMimeType();

    /**
     * Creates a default response object dependent on the context of the request.
     *
     * @return A default response with context matching this request
     */
    public Response buildResponse();

    /**
     * Creates a default response object dependent on the context of the request.
     *
     * @param data the response data bytes (required)
     * @return A default response with context matching this request containing the provided data
     */
    public Response buildResponse(byte[] data);

    /**
     * Creates a default response object dependent on the context of the request.
     *
     * @param data the response text data (required)
     * @return A default response with context matching this request containing the provided data
     */
    public Response buildResponse(String data);

}