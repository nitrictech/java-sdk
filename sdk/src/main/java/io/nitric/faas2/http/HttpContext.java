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

package io.nitric.faas2.http;

import io.nitric.util.Contracts;

/**
 * Provides a Http Context object.
 */
public class HttpContext {

    final HttpRequest request;
    final HttpResponse response;

    /**
     * Provides a HTTP context object.
     *
     * @param request the HTTP request (required)
     * @param response the HTTP response (required)
     */
    public HttpContext(HttpRequest request, HttpResponse response) {
        Contracts.requireNonNull(request, "request");
        Contracts.requireNonNull(response, "response");

        this.request = request;
        this.response = response;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the HTTP request object.
     *
     * @return the HTTP request object
     */
    public HttpRequest getRequest() {
        return request;
    }

    /**
     * Return the HTTP response object.
     *
     * @return the HTTP response object
     */
    public HttpResponse getResponse() {
        return response;
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
            + "[request=" + request
            + ", response=" + response
            + "]";
    }

}
