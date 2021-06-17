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
 */
public abstract class ResponseContext {

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
