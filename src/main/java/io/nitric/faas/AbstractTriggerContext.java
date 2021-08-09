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

import io.nitric.proto.faas.v1.TriggerRequest;
import io.nitric.util.Contracts;

/**
 * <p>
 *   Provides an abstract Nitric FaaS trigger context class.
 * </p>
 *
 * <p>
 *  The example below illustrates unwrapping the FaaS trigger context object.
 * </p>
 *
 * <pre><code class="code">
 * package com.example;
 *
 * import io.nitric.faas.Faas;
 * import io.nitric.faas.Trigger;
 * import io.nitric.faas.TriggerContext;
 * import io.nitric.faas.NitricFunction;
 * import io.nitric.faas.Response;
 *
 * public class HelloWorld implements NitricFunction {
 *
 *     &commat;Override
 *     public Response handle(Trigger trigger) {
 *         if (trigger.getContext().isHttp()) {
 *             var httpContext = trigger.getContext().asHttp();
 *             // Extract HTTP context metadata
 *         } else if (trigger.getContext().isTopic()) {
 *             var topicContext = trigger.getContext().asTopic();
 *             // Extract Topic context metadata
 *         }
 *
 *         return trigger.buildResponse("Hello World");
 *     }
 *
 *     public static void main(String... args) {
 *         Faas.start(new HelloWorld());
 *     }
 * }
 * </code></pre>
 *
 * @see HttpTriggerContext
 * @see TopicTriggerContext
 */
public abstract class AbstractTriggerContext {

    /**
     * @return If the context is for a HTTP Trigger
     */
    public final boolean isHttp() {
        return this instanceof HttpTriggerContext;
    }

    /**
     * @return If the context is for a Topic Trigger
     */
    public final boolean isTopic() {
        return this instanceof TopicTriggerContext;
    }

    /**
     * Return the HTTP trigger context if valid tpe or throw UnsupportedOperationException otherwise.
     *
     * @return the HTTP trigger context if valid tpe or throw UnsupportedOperationException otherwise
     * @throws UnsupportedOperationException if not an HTTP trigger type
     */
    public final HttpTriggerContext asHttp() {
        if (this.isHttp()) {
            return (HttpTriggerContext) this;
        } else {
            throw new UnsupportedOperationException("not a HTTP trigger context: " + getClass().getSimpleName());
        }
    }

    /**
     * Return the Topic trigger context if valid tpe or throw UnsupportedOperationException otherwise.
     *
     * @return the Topic trigger context if valid tpe or throw UnsupportedOperationException otherwise.
     * @throws UnsupportedOperationException if not a Topic trigger type
     */
    public final TopicTriggerContext asTopic() {
        if (this.isTopic()) {
            return (TopicTriggerContext) this;
        } else {
            throw new UnsupportedOperationException("not a Topic trigger context: " + getClass().getSimpleName());
        }
    }

    /**
     * Create a new TriggerContext from the on-wire TriggerRequest from the Nitric membrane
     *
     * @return The translated TriggerContext or null if the TriggerRequest does not contain context
     */
    protected static AbstractTriggerContext buildTriggerContext(TriggerRequest trigger) {
        Contracts.requireNonNull(trigger, "trigger");

        if (trigger.hasHttp()) {
            return new HttpTriggerContext(
                trigger.getHttp().getMethod(),
                trigger.getHttp().getPath(),
                trigger.getHttp().getHeadersMap(),
                trigger.getHttp().getQueryParamsMap()
            );
        } else if (trigger.hasTopic()) {
            return new TopicTriggerContext(
                trigger.getTopic().getTopic()
            );
        } else {
            String msg = "not a supported Trigger context type: " + trigger.getClass().getSimpleName();
            throw new UnsupportedOperationException(msg);
        }
    }

}
