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

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;

import io.nitric.util.Contracts;

/**
 * Provides a HTTP response class.
 */
public class HttpResponse {

    private int status = 200;
    private Map<String, String> headers = new HashMap<>();
    private byte[] data;

    // Constructors -----------------------------------------------------------

    /**
     * Create a new HttpResponse object.
     */
    public HttpResponse() {
    }

    /**
     * Create a new HttpResponse object copied from the given response.
     *
     * @param response the HttpResponse object to clone (required)
     */
    public HttpResponse(HttpResponse response) {
        Contracts.requireNonNull(response, "response");

        this.status = response.status;
        this.headers = new HashMap<>(response.headers);
        this.data = response.data;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return The HTTP Response status for this context
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status - The HTTP status to set for this context
     * @return this HttpResponse object for chaining
     */
    public HttpResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    /**
     * Return the map of HTTP response headers.
     *
     * @return the map of HTTP response headers
     */
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    /**
     * Add a header with the given name and value.
     *
     * @param name the header name to add (required)
     * @param value the header value to add (required)
     * @return this HttpResponse object for chaining
     */
    public HttpResponse addHeader(String name, String value) {
        Contracts.requireNonBlank(name, "name");
        Contracts.requireNonBlank(value, "value");

        headers.put(name, value);
        return this;
    }

    /**
     * @param headers The header map to set
     * @return this HttpResponse object for chaining
     */
    public HttpResponse setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    /**
     * Return the response data, or null if not defined.
     *
     * @return the response data, or null if not defined
     */
    public byte[] getData() {
        return data;
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
     * @return this HttpResponse object for chaining
     */
    public HttpResponse setData(byte[] data) {
        this.data = data;
        return this;
    }

    /**
     * Set the data for this response as UTF-8 encoded text.
     *
     * @param text the UTF-8 encode text to set as the data
     * @return this HttpResponse object for chaining
     */
    public HttpResponse setDataAsText(String text) {
        Contracts.requireNonNull(text, "text");
        this.data = text.getBytes(StandardCharsets.UTF_8);
        return this;
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
            + "[status=" + status
            + ", headers=" + headers
            + ", data=" + dataSample
            + "]";
    }
}
