package io.nitric.faas;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

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

import io.nitric.proto.faas.v1.TriggerRequest;

/**
 * <p>
 *  Provides a Nitric FaaS Trigger class.
 * </p>
 *
 * @see Faas
 * @see AbstractTriggerContext
 * @see Response
 */
public class Trigger {

    private final byte[] data;
    private final String mimeType;
    private final AbstractTriggerContext context;

    // Constructors -----------------------------------------------------------

    /*
     * Enforce builder pattern.
     */
    private Trigger(byte[] data, String mimeType, AbstractTriggerContext context) {
        this.data = data;
        this.mimeType = mimeType;
        this.context = context;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return Retrieves the context that raised the trigger
     */
    public AbstractTriggerContext getContext() {
        return this.context;
    }

    /**
     * @return Retrieve the data of the trigger
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * @return Retrieve the mimeType of the trigger
     */
    public String getMimeType() {
        return this.mimeType;
    }

    /**
     * Creates a default response object dependent on the context of the request.
     *
     * @return A default response with context matching this request
     */
    public Response buildResponse() {
        return this.buildResponse((byte[]) null);
    }

    /**
     * Creates a default response object dependent on the context of the request.
     *
     * @param data the response data bytes (required)
     * @return A default response with context matching this request containing the provided data
     */
    public Response buildResponse(byte[] data) {
        AbstractResponseContext responseCtx = null;

        if (this.context.isHttp()) {
            responseCtx = new HttpResponseContext();
        } else if (this.context.isTopic()) {
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
    public Response buildResponse(String data) {
        return this.buildResponse(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        String dataSample = "null";
        if (data != null) {
            dataSample = new String(data, StandardCharsets.UTF_8);
            if (dataSample.length() > 40) {
                dataSample = dataSample.substring(0, 42) + "...";
            }
        }
        return getClass().getSimpleName()
            + "[context=" + context
            + ", mimeType=" + mimeType
            + ", data=" + dataSample
            + "]";
    }

    // Protected Methods ------------------------------------------------------

    /**
     * Translates on on-wire trigger request to a Trigger to be passed to a NitricFunction.
     *
     * @return The translated trigger (required)
     */
    protected static Trigger buildTrigger(TriggerRequest trigger) {
        Objects.requireNonNull(trigger, "trigger parameter is required");

        var ctx = AbstractTriggerContext.buildTriggerContext(trigger);

        return new Trigger(
            trigger.getData().toByteArray(),
            trigger.getMimeType(),
            ctx
        );
    }

}
