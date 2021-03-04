package io.nitric.faas.http;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <p>
 *     Provides an immutable HTTP response class. This class provides static convenience methods for quickly creating
 *     a response and a <code>HttpResponse.Builder</code> class for comprehensive control.
 * </p>
 *
 * <p>
 *     The examples below use the static build methods:
 * </p>
 *
 * <code>
*     // 404 - Not Found response
*     return HttpResponse.build(404);
*
*     // 200 - JSON message
*     var json = "{ \"status\": \"online\" }";
*     return HttpResponse.build(200, json);
*
*     // 418 - Error message
*     return HttpResponse.build(418, "Im a tea pot");
 * </code>
 *
 * <p>
 *     The example below uses the <code>HttpResponse.Builder</code> class:
 * </p>
 *
 * <code>
 *    byte[] data = Files.readAllBytes(path);
 *
 *    return HttpResponse.newBuilder()
 *               .contentType("application/jar")
 *               .body(data)
 *               .build();
 * </code>
 *
 * @see HttpRequest
 * @see HttpHandler
 * @since 1.0
 */
public class HttpResponse {

    private static final String CONTENT_TYPE = "Content-Type";

    private final int statusCode;
    private final Map<String, List<String>> headers;
    private final byte[] body;

    // Private constructor to enforce response builder pattern.
    private HttpResponse(int statusCode,
                         Map<String, List<String>> headers,
                         byte[] body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return the response HTTP status code, e.g. 200
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @return an immutable map of HTTP response headers
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * Return the named HTTP header or null if not found.
     * If the header has multiple values the first value will be returned.
     *
     * @param name the name of the HTTP header
     * @return the named HTTP header or null if not found
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
     * @return the HTTP response body length, or -1 if no body present.
     */
    public int getBodyLength() {
        return (body != null) ? body.length : -1;
    }

    /**
     * @return the string representation of this object
     */
    public String toString() {
        return getClass().getSimpleName() +
                "[statusCode=" + statusCode +
                ", headers=" + headers +
                ", body.length=" + ((body != null) ? body.length : 0) +
                "]";
    }

    /**
     * @return a new HttpResponse builder class.
     */
    public static HttpResponse.Builder newBuilder() {
        return new HttpResponse.Builder();
    }

    /**
     * Return a new HttpResponse object from the given HTTP status code.
     *
     * @param status the HTTP status code
     * @return a new HttpResponse object
     */
    public static HttpResponse build(int status) {
        return newBuilder().statusCode(status).build();
    }

    /**
     * Return a new HttpResponse object from the given HTTP status code and body text.
     *
     * @param status the HTTP status code
     * @param bodyText the body text
     * @return a new HttpResponse object
     */
    public static HttpResponse build(int status, String bodyText) {
        return newBuilder().statusCode(status).bodyText(bodyText).build();
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a HTTP response builder class.
     *
     * @since 1.0
     */
    public static class Builder {

        private int statusCode = 200;
        private Map<String, List<String>> headers;
        private byte[] body;

        // Private constructor to enforce response builder pattern.
        private Builder() {
        }

        /**
         * Set the response HTTP status code, e.g. 200.
         * @param statusCode the HTTP method
         * @return the response builder instance
         */
        public Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        /**
         * Set the response HTTP headers.
         * @param headers the response HTTP headers
         * @return the response builder instance
         */
        public Builder headers(Map<String, List<String>> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Set the response 'ContentType' header value.
         * @param value the 'ContentType' header value
         * @return the response builder instance
         */
        public Builder contentType(String value) {
            if (headers == null) {
                headers = new HashMap<String, List<String>>();
            }
            headers.put(CONTENT_TYPE, Arrays.asList(value));
            return this;
        }

        // TODO: multi-part file posts

        /**
         * Set the response body.
         * @param body the response body
         * @return the response builder instance.
         */
        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        /**
         * Set the response body text (UTF-8) encoded.
         * @param body the response body text
         * @return the response builder instance.
         */
        public Builder bodyText(String body) {
            this.body = (body != null) ? body.getBytes(StandardCharsets.UTF_8) : null;
            return this;
        }

        /**
         * Return a new HTTP response object. The default status code is 200 and
         * Content-Type is 'text/json; charset=UTF-8' if no headers are defined.
         *
         * @return a new HTTP response
         */
        public HttpResponse build() {

            Map<String, List<String>> responseHeaders = (headers != null) ? new HashMap<>(headers) : new HashMap<>();

            // If content type not defined, then attempt to detect and add content type
            if (getHeaderValue(CONTENT_TYPE, responseHeaders) == null) {
                var contentType = detectContentType(body);
                if (contentType != null) {
                    responseHeaders.put(CONTENT_TYPE, Arrays.asList(contentType));
                }
            }

            return new HttpResponse(statusCode, Collections.unmodifiableMap(responseHeaders), body);
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
