package io.nitric.http;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <p>
 *  Provides an immutable HTTP response class. This class provides static convenience methods for quickly creating
 *  a response and a <code>HttpResponse.Builder</code> class for comprehensive control.
 * </p>
 *
 * <p>
 *  The HTTP examples below use the static <code>HttpResponse</code> build methods:
 * </p>
 *
 * <pre>
*     // 404 - Not Found response
*     return HttpResponse.build(404);
*
*     // 200 - JSON message
*     var json = "{ \"status\": \"online\" }";
*     return HttpResponse.build(200, json);
*
*     // 418 - Error message
*     return HttpResponse.build(418, "Im a tea pot");
 * </pre>
 *
 * <p>
 *  The example below uses the <code>HttpResponse.Builder</code> class:
 * </p>
 *
 * <pre>
 *    byte[] data = Files.readAllBytes(path);
 *
 *    return HttpResponse.newBuilder()
 *               .header("Content-Type", "application/jar")
 *               .body(data)
 *               .build();
 * </pre>
 *
 * @see HttpRequest
 * @see HttpHandler
 * @since 1.0
 */
public class HttpResponse {

    private static final String CONTENT_TYPE = "Content-Type";

    private final int status;
    private final Map<String, List<String>> headers;
    private final byte[] body;

    /*
     * Package Private constructor to enforce response builder pattern.
     */
    private HttpResponse(Builder builder) {
        this.status = builder.status;
        this.headers = Collections.unmodifiableMap(builder.headers);
        this.body = builder.body;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return the HTTP response status, e.g. 200 for HTTP OK
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
        return getClass().getSimpleName() +
                "[status=" + status +
                ", headers=" + headers +
                ", body.length=" + ((body != null) ? body.length : 0) +
                "]";
    }

    /**
     * @return a new HTTP response builder class.
     */
    public static HttpResponse.Builder newBuilder() {
        return new HttpResponse.Builder();
    }

    /**
     * Return a new HTTP response object from the given body text.
     *
     * @param body the HTTP response body
     * @return a new HttpResponse object
     */
    public static HttpResponse build(String body) {
        return newBuilder().bodyText(body).build();
    }

    /**
     * Return a new HTTP response object from the given status.
     *
     * @param status the HTTP response status
     * @return a new HttpResponse object
     */
    public static HttpResponse build(int status) {
        return newBuilder().status(status).build();
    }

    /**
     * Return a new HttpResponse object from the given Nitric status and body text.
     *
     * @param status the response status
     * @param body the body text
     * @return a new HttpResponse object
     */
    public static HttpResponse build(int status, String body) {
        return newBuilder().status(status).bodyText(body).build();
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a HTTP response builder class.
     *
     * @since 1.0
     */
    public static class Builder {

        int status;
        Map<String, List<String>> headers;
        byte[] body;

        /*
         * Package Private constructor to enforce response builder pattern.
         */
        Builder() {
        }

        /**
         * Set the HTTP response status, e.g. 200 for HTTP OK.
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
         *
         * @param headers the function response headers
         * @return the response builder instance
         */
        public Builder headers(Map<String, List<String>> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Set the HTTP response body.
         *
         * @param body the response body
         * @return the response builder instance.
         */
        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        /**
         * Set the HTTP response body text (UTF-8) encoded.
         *
         * @param body the response body text
         * @return the response builder instance.
         */
        public Builder bodyText(String body) {
            this.body = (body != null) ? body.getBytes(StandardCharsets.UTF_8) : null;
            return this;
        }

        /**
         * Return a new HTTP response object.
         *
         * @return a new HTTP response object
         */
        public HttpResponse build() {

            headers = (headers != null) ? headers : new HashMap<>();

            // If content type not defined, then attempt to detect and add content type
            if (getHeaderValue(CONTENT_TYPE, headers) == null) {
                var contentType = detectContentType(body);
                if (contentType != null) {
                    headers.put(CONTENT_TYPE, Arrays.asList(contentType));
                }
            }

            return new HttpResponse(this);
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