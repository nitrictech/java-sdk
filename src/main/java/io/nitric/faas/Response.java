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

import com.google.protobuf.ByteString;
import io.nitric.proto.faas.v1.TriggerResponse;
import io.nitric.proto.faas.v1.TopicResponseContext;
import io.nitric.proto.faas.v1.HttpResponseContext;

/**
 * Response model for a NitricFunction
 */
public class Response {
    private byte[] data;
    private final ResponseContext context;

    /**
     * Create a new Nitric Response
     *
     * @param data The data for the response
     * @param context The context of the response
     */
    protected Response(byte[] data, ResponseContext context) {
        this.data = data;
        this.context = context;
    }

    /**
     * Retrieve the data contained in the response
     *
     * @return The response data as bytes
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * Set the data for this response
     *
     * @param data The data as an array of bytes
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Gets the context for this response
     *
     * @return The abstract ResponseContext this can be unwrapped with asHttp() or asTopic()
     */
    public ResponseContext getContext() {
        return this.context;
    }

    /**
     * Translates a Response for on-wire transport to the membrane
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
            httpCtxBuilder.putAllHeaders(httpCtx.getHeaders());

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
