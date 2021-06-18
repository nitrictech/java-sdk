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


import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *   Provides contextual response metadata for triggers raised by HTTP Requests
 * </p>
 *
 * @see HttpTriggerContext
 */
public class HttpResponseContext extends AbstractResponseContext {
    public int status = 200;
    public Map<String, String> headers = new HashMap<>();

    /**
     * @return The HTTP Response status for this context
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return The HTTP headers for this context
     */
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    /**
     * @param status - The HTTP status to set for this context
     * @return The instance of the context for chaining
     */
    public HttpResponseContext setStatus(int status) {
        this.status = status;
        return this;
    }

    /**
     * @param key   The key of the header to add
     * @param value The value of the header to add
     * @return The instance of the context for chaining
     */
    public HttpResponseContext addHeader(String key, String value) {
        this.headers.put(key, value);

        return this;
    }

    /**
     * @param headers The header map to set
     * @return The instance of the context for chaining
     */
    public HttpResponseContext setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    /**
     * @return String representation of HttpResponseContext
     */
    public String toString() {
        return getClass().getSimpleName()
                + "[status=" + status
                + ", headers=" + headers
                + "]";
    }
}
