package io.nitric.faas;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <p>
 *     Provides an immutable function response class. This class provides static convenience methods for quickly creating
 *     a response and a <code>NitricResponse.Builder</code> class for comprehensive control.
 * </p>
 *
 * <p>
 *     The HTTP examples below use the static <code>NitricResponse</code> build methods:
 * </p>
 *
 * <code>
*     // 404 - Not Found response
*     return NitricResponse.build(404);
*
*     // 200 - JSON message
*     var json = "{ \"status\": \"online\" }";
*     return NitricResponse.build(200, json);
*
*     // 418 - Error message
*     return NitricResponse.build(418, "Im a tea pot");
 * </code>
 *
 * <p>
 *     The example below uses the <code>NitricResponse.Builder</code> class:
 * </p>
 *
 * <code>
 *    byte[] data = Files.readAllBytes(path);
 *
 *    return NitricResponse.newBuilder()
 *               .header("Content-Type", "application/jar")
 *               .body(data)
 *               .build();
 * </code>
 *
 * @see NitricRequest
 * @see NitricFunction
 * @since 1.0
 */
public class NitricResponse {

    private static final String CONTENT_TYPE = "Content-Type";

    private final int status;
    private final Map<String, List<String>> headers;
    private final byte[] body;

    // Private constructor to enforce response builder pattern.
    private NitricResponse(int status,
                           Map<String, List<String>> headers,
                           byte[] body) {
        this.status = status;
        this.headers = headers;
        this.body = body;
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
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * Return the named response header or null if not found.
     * If the header has multiple values the first value will be returned.
     *
     * @param name the name of the Nitric header
     * @return the named function header or null if not found
     */
    public String getHeader(String name) {
        var values = headers.get(name);
        return (values != null) ? values.get(0) : null;
    }

    /**
     * @return the response body
     */
    public byte[] getBody() {
        return body;
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
        return getClass().getSimpleName() +
                "[status=" + status +
                ", headers=" + headers +
                ", body.length=" + ((body != null) ? body.length : 0) +
                "]";
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

        private int status;
        private Map<String, List<String>> headers;
        private byte[] body;

        // Private constructor to enforce response builder pattern.
        private Builder() {
        }

        /**
         * Set the function response status, e.g. 200 for HTTP OK.
         * @param status the response status
         * @return the response builder instance
         */
        public Builder status(int status) {
            this.status = status;
            return this;
        }

        /**
         * Set the response header name and value.
         * @param name the header name (required)
         * @param value the header value (required)
         * @return the response builder instance
         */
        public Builder header(String name, String value) {
            Objects.requireNonNull(name, "header name is required");
            Objects.requireNonNull(name, "header value is required");

            if (headers == null) {
                headers = new HashMap<String, List<String>>();
            }
            headers.put(name, Arrays.asList(value));
            return this;
        }

        /**
         * Set the function response headers.
         * @param headers the function response headers
         * @return the response builder instance
         */
        public Builder headers(Map<String, List<String>> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Set the function response body.
         * @param body the response body
         * @return the response builder instance.
         */
        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        /**
         * Set the function response body text (UTF-8) encoded.
         * @param body the response body text
         * @return the response builder instance.
         */
        public Builder bodyText(String body) {
            this.body = (body != null) ? body.getBytes(StandardCharsets.UTF_8) : null;
            return this;
        }

        /**
         * Return a new function response object.
         *
         * @return a new function response object
         */
        public NitricResponse build() {

            Map<String, List<String>> responseHeaders = (headers != null) ? new HashMap<>(headers) : new HashMap<>();

            // If content type not defined, then attempt to detect and add content type
            if (getHeaderValue(CONTENT_TYPE, responseHeaders) == null) {
                var contentType = detectContentType(body);
                if (contentType != null) {
                    responseHeaders.put(CONTENT_TYPE, Arrays.asList(contentType));
                }
            }

            return new NitricResponse(status, Collections.unmodifiableMap(responseHeaders), body);
        }

        // Private Methods ----------------------------------------------------

        private String detectContentType(byte[] body) {

            if (body != null && body.length > 1) {
                var bodyText = new String(body, StandardCharsets.UTF_8);

                if ((bodyText.startsWith("{") && bodyText.endsWith("}"))
                    || (bodyText.startsWith("[") && bodyText.endsWith("]"))) {
                    return "text/json; charset=UTF-8";
                }
                if (bodyText.startsWith("<?xml") && bodyText.endsWith(">")) {
                    return "text/xml; charset=UTF-8";
                }

                return "text/html; charset=UTF-8";
            }

            return null;
        }

        private String getHeaderValue(String name, Map<String, List<String>> headers) {

            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(name)) {
                    return entry.getValue().get(0);
                }
            }

            return null;
        }
    }

}