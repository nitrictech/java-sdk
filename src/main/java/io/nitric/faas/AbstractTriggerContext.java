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

import io.nitric.proto.faas.v1.TriggerRequest;

import java.util.Objects;

/**
 * <p>
 *   Abstract representation of NitricTrigger context
 * </p>
 *
 * <p>
 *  The example below demonstrates unwrapping TriggerContext.
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
     * @return The Context as a HttpRequestTriggerContext or null if is not a trigger from a http request
     */
    public final HttpTriggerContext asHttp() {
        if (this.isHttp()) {
            return (HttpTriggerContext) this;
        }
        // TODO: Determine if we would rather throw an error here?
        return null;
    }

    /**
     * @return The Context as a TopicTriggerContext or null if is not a trigger for a topic
     */
    public final TopicTriggerContext asTopic() {
        if (this.isTopic()) {
            return (TopicTriggerContext) this;
        }
        // TODO: Determine if we would rather throw an error here?
        return null;
    }

    /**
     * Create a new TriggerContext from the on-wire TriggerRequest from the Nitric membrane
     *
     * @return The translated TriggerContext or null if the TriggerRequest does not contain context
     */
    protected static AbstractTriggerContext buildTriggerContext(TriggerRequest trigger) {
        Objects.requireNonNull(trigger, "trigger parameter is required");

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
        }

        throw new UnsupportedOperationException("TriggerRequest does not contain valid context");
    }
}
