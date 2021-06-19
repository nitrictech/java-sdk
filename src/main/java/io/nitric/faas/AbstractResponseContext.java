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


/**
 * ResponseContext
 *
 * <pre><code class="code">
 * package com.example;
 *
 * import io.nitric.faas.Faas;
 * import io.nitric.faas.Trigger;
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
 *         var = response trigger.buildResponse("Hello World");
 *
 *         if (response.getContext().isHttp()) {
 *             // Augment response with additional context
 *             var httpContext = response.getContext.asHttp();
 *             ...
 *         } else if (response.getContext().isTopic()) {
 *             // Augment response with additional context
 *             var topicContext = response.getContext.asTopic();
 *             ...
 *         }
 *
 *         return response;
 *     }
 *
 *     public static void main(String... args) {
 *         Faas.start(new HelloWorld());
 *     }
 * }
 * </code></pre>
 */
public abstract class AbstractResponseContext {

    /**
     * @return true if this is HttpResponseContext otherwise false
     */
    public boolean isHttp() {
        return this instanceof HttpResponseContext;
    }

    /**
     * @return true if this is TopicResponseContext otherwise false
     */
    public boolean isTopic() {
        return this instanceof TopicResponseContext;
    }

    /**
     * @return this as HttpResponseContext or null if it isHttp() is false
     */
    public HttpResponseContext asHttp() {
        if (this.isHttp()) {
            return (HttpResponseContext) this;
        }
        return null;
    }

    /**
     * @return this as TopicResponseContext or null if it isTopic() is false
     */
    public TopicResponseContext asTopic() {
        if (this.isTopic()) {
            return (TopicResponseContext) this;
        }
        return null;
    }
}
