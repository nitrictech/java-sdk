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
import java.util.Collections;
import java.util.Map;

/**
 * Provides a HTTP request object.
 */
public class HttpRequest {

    final String method;
    final String path;
    final Map<String, String> headers;
    final Map<String, String> queryParams;
    final String mimeType;
    final byte[] data;

    // Constructors -----------------------------------------------------------

    /**
     * Create a new HttpRequest object.
     *
     * @param method the method of the HTTP request
     * @param path the path of the HTTP request
     * @param headers the headers of the HTTP request
     * @param queryParams the query parameters of the HTTP request
     * @param mimeType the request mime-type
     * @param data the HTTP body data
     */
    public HttpRequest(
        String method,
        String path,
        Map<String, String> headers,
        Map<String, String> queryParams,
        String mimeType,
        byte[] data
    ) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.queryParams = queryParams;
        this.mimeType = mimeType;
        this.data = data;
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
     * Return the trigger mime type.
     *
     * @return the trigger mime type
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Get the trigger data.
     *
     * @return the data of the trigger
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * Get the trigger data as UTF-8 encode text, or empty string if not defined.
     *
     * @return the trigger data as UTF-8 encode text, or empty string if not defined
     */
    public String getDataAsText() {
        return (getData() != null) ? new String(getData(), StandardCharsets.UTF_8) : "";
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
                dataSample = dataSample.substring(0, 40) + "...";
            }
        }

        return getClass().getSimpleName()
                + "[method=" + method
                + ", path=" + path
                + ", headers=" + headers
                + ", queryParams" + queryParams
                + ", mimeType=" + mimeType
                + ", data.length" + dataSample
                + "]";
    }

}