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

import java.util.Collections;
import java.util.Map;

/**
 * <p>
 *  Provides contextual metadata for a trigger raised by a HTTP request.
 * </p>
 */
public class HttpTriggerContext extends AbstractTriggerContext {

    private final String method;
    private final String path;
    private final Map<String, String> headers;
    private final Map<String, String> queryParams;

    // Constructors -----------------------------------------------------------

    /**
     * Create a new HttpRequestTriggerContext.
     *
     * @param method The method of the HTTP request for the raised trigger
     * @param path The path of the HTTP request for the raised trigger
     * @param headers The headers of the HTTP request for the raised trigger
     * @param queryParams The query parameters of the HTTP request for the raised trigger
     */
    protected HttpTriggerContext(
        String method,
        String path,
        Map<String, String> headers,
        Map<String, String> queryParams
    ) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.queryParams = queryParams;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return The method of the HTTP Request that raised this trigger
     */
    public String getMethod() {
        return this.method;
    }

    /**
     * @return The path of the HTTP Request that raised this trigger
     */
    public String getPath() {
        return this.path;
    }

    /**
     * @return The headers of the HTTP Request that raised this trigger
     */
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    /**
     * @return The query parameters of the HTTP Request that raised this trigger
     */
    public Map<String, String> getQueryParams() {
        return Collections.unmodifiableMap(this.queryParams);
    }

    /**
     * @return String representation of HttpResponseContext
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[method=" + method
                + ", path=" + path
                + ", headers=" + headers
                + ", queryParams" + queryParams
                + "]";
    }
}
