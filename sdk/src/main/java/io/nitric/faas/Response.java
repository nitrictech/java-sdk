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

import com.google.protobuf.ByteString;

import io.nitric.proto.faas.v1.HeaderValue;
import io.nitric.proto.faas.v1.HttpResponseContext;
import io.nitric.proto.faas.v1.TopicResponseContext;
import io.nitric.proto.faas.v1.TriggerResponse;
import io.nitric.util.Contracts;

/**
 * <p>
 *  Provides a Nitric function response class.
 * </p>
 *
 * @see NitricFunction
 */
public class Response {

    private final AbstractResponseContext context;
    private byte[] data;

    // Constructors -----------------------------------------------------------

    /**
     * Create a new Nitric FaaS response.
     *
     * @param data The data for the response
     * @param context The context of the response
     */
    protected Response(byte[] data, AbstractResponseContext context) {
        this.data = data;
        this.context = context;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Gets the context for this response.
     *
     * @return The abstract ResponseContext this can be unwrapped with asHttp() or asTopic()
     */
    public AbstractResponseContext getContext() {
        return this.context;
    }

    /**
     * Get the data contained in the response.
     *
     * @return The response data as bytes
     */
    public byte[] getData() {
        return this.data;
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
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Set the data for this response as UTF-8 encoded text.
     *
     * @param text the UTF-8 encode text to set as the data
     */
    public void setDataAsText(String text) {
        Contracts.requireNonNull(text, "text");
        this.data = text.getBytes(StandardCharsets.UTF_8);
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
                dataSample = dataSample.substring(0, 42) + "...";
            }
        }
        return getClass().getSimpleName()
            + "[context=" + context
            + ", data=" + dataSample
            + "]";
    }

    // Protected Methods ------------------------------------------------------

    /**
     * Translates a Response for on-wire transport to the membrane.
     *
     * @return The proto object for the Nitric Response
     */
    protected TriggerResponse toGrpcTriggerResponse() {
        var trBuilder = TriggerResponse.newBuilder();

        trBuilder.setData(ByteString.copyFrom(this.data));

        if (this.context.isHttp()) {
            var httpCtx = this.context.asHttp();
            var httpCtxBuilder = HttpResponseContext.newBuilder();

            httpCtxBuilder.setStatus(httpCtx.getStatus());

            httpCtx.getHeaders().entrySet().forEach(e -> {
                httpCtxBuilder.putHeaders(e.getKey(), HeaderValue.newBuilder().addValue(e.getValue()).build());
            });

            trBuilder.setHttp(httpCtxBuilder);

        } else if (this.context.isTopic()) {
            var topicCtx = this.context.asTopic();
            var topicCtxBuilder = TopicResponseContext.newBuilder();

            topicCtxBuilder.setSuccess(topicCtx.isSuccess());

            trBuilder.setTopic(topicCtxBuilder);
        }

        return trBuilder.build();
    }
}