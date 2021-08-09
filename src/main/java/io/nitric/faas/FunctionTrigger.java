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

import io.nitric.proto.faas.v1.TriggerRequest;
import io.nitric.util.Contracts;

/**
 * <p>
 *  Provides a Nitric FaaS Trigger class.
 * </p>
 *
 * @see Faas
 * @see AbstractTriggerContext
 * @see Response
 */
public class FunctionTrigger implements Trigger {

    private final byte[] data;
    private final String mimeType;
    private final AbstractTriggerContext context;

    // Constructors -----------------------------------------------------------

    /*
     * Enforce builder pattern.
     */
    private FunctionTrigger(byte[] data, String mimeType, AbstractTriggerContext context) {
        this.data = data;
        this.mimeType = mimeType;
        this.context = context;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the context which raised the trigger.
     *
     * @return the context which raised the trigger
     */
    @Override
    public AbstractTriggerContext getContext() {
        return this.context;
    }

    /**
     * Get the trigger data.
     *
     * @return the data of the trigger
     */
    @Override
    public byte[] getData() {
        return this.data;
    }

    /**
     * @return Retrieve the mimeType of the trigger
     */
    @Override
    public String getMimeType() {
        return this.mimeType;
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        String dataSample = "null";
        if (getData() != null) {
            dataSample = new String(getData(), StandardCharsets.UTF_8);
            if (dataSample.length() > 40) {
                dataSample = dataSample.substring(0, 42) + "...";
            }
        }
        return getClass().getSimpleName()
            + "[context=" + getContext()
            + ", mimeType=" + getMimeType()
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
        Contracts.requireNonNull(trigger, "trigger");

        var ctx = AbstractTriggerContext.buildTriggerContext(trigger);

        return new FunctionTrigger(
            trigger.getData().toByteArray(),
            trigger.getMimeType(),
            ctx
        );
    }

}
