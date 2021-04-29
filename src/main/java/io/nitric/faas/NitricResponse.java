package io.nitric.faas;

/*-
 * #%L
 * Nitric Java SDK
 * %%
 * Copyright (C) 2021 Nitric Pty Ltd
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

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  Provides an immutable function response class. This class provides static convenience methods for quickly creating
 *  a response and a <code>NitricResponse.Builder</code> class for comprehensive control.
 * </p>
 *
 * <p>
 *  The example below uses the <code>NitricResponse.Builder</code> class:
 * </p>
 *
 * <pre>
 * import io.nitric.faas.NitricResponse;
 * ...
 *
 * byte[] data = Files.readAllBytes(path);
 *
 * return NitricResponse.build(data);
 * </pre>
 *
 * @see NitricEvent
 * @see NitricFunction
 *
 * @since 1.0
 */
public class NitricResponse {

    private static final String CONTENT_TYPE = "Content-Type";

    private final int status;
    private final Map<String, String> headers;
    private final byte[] body;

    /*
     * Private constructor to enforce response builder pattern.
     */
    private NitricResponse(Builder builder) {
        this.status = builder.status;
        this.headers = Collections.unmodifiableMap(builder.headers);
        this.body = builder.body;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return the function response status, e.g. 200 for HTTP OK
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return an immutable map of function response headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * @return the response body
     */
    public byte[] getBody() {
        return body;
    }

    /**
     * @return the response body as text (UTF-8 encoded)
     */
    public String getBodyText() {
        return (body != null) ? new String(body, StandardCharsets.UTF_8) : null;
    }

    /**
     * @return the function response body length, or -1 if no body present.
     */
    public int getBodyLength() {
        return (body != null) ? body.length : -1;
    }

    /**
     * @return the string representation of this object
     */
    public String toString() {
        return getClass().getSimpleName()
                + "[status=" + status
                + ", headers=" + headers
                + ", body.length=" + ((body != null) ? body.length : 0)
                + "]";
    }

    /**
     * @return a new function response builder class.
     */
    public static NitricResponse.Builder newBuilder() {
        return new NitricResponse.Builder();
    }

    /**
     * Return a new function response object from the given body text.
     *
     * @param body the function response body
     * @return a new NitricResponse object
     */
    public static NitricResponse build(String body) {
        return newBuilder().bodyText(body).build();
    }

    /**
     * Return a new function response object from the given status.
     *
     * @param status the function response status
     * @return a new NitricResponse object
     */
    public static NitricResponse build(int status) {
        return newBuilder().status(status).build();
    }

    /**
     * Return a new NitricResponse object from the given Nitric status and body text.
     *
     * @param status the response status
     * @param body the body text
     * @return a new NitricResponse object
     */
    public static NitricResponse build(int status, String body) {
        return newBuilder().status(status).bodyText(body).build();
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a Nitric function response builder class.
     *
     * @since 1.0
     */
    public static class Builder {

        int status;
        Map<String, String> headers = new HashMap<>();
        byte[] body;

        /*
         * Private constructor to enforce response builder pattern.
         */
        Builder() {
        }

        /**
         * Set the function response status.
         *
         * @param status the response status
         * @return the response builder instance
         */
        public Builder status(int status) {
            this.status = status;
            return this;
        }

        /**
         * Set the response header name and value.
         *
         * @param name the header name (required)
         * @param value the header value (required)
         * @return the response builder instance
         */
        public Builder header(String name, String value) {
            Objects.requireNonNull(name, "name is required");
            Objects.requireNonNull(value, "value is required");

            headers.put(name, value);
            return this;
        }

        /**
         * Set the function response headers.
         *
         * @param headers the function response headers
         * @return the response builder instance
         */
        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Set the function response body.
         *
         * @param body the response body
         * @return the response builder instance.
         */
        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        /**
         * Set the function response body text (UTF-8) encoded.
         *
         * @param body the response body text
         * @return the response builder instance.
         */
        public Builder bodyText(String body) {
            this.body = (body != null) ? body.getBytes(StandardCharsets.UTF_8) : null;
            return this;
        }

        /**
         * @return a new function response object
         */
        public NitricResponse build() {
            return new NitricResponse(this);
        }
    }

}
