package io.nitric.faas;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <p>
 *  Provides an immutable Nitric Event class.
 * </p>
 *
 * @see NitricFunction
 * @see NitricResponse
 * @see NitricResponse.Builder
 *
 * @since 1.0
 */
public class NitricEvent {

    private final NitricContext context;
    private final byte[] payload;

    // Private constructor to enforce request builder pattern.
    private NitricEvent(NitricContext context, byte[] payload) {
        this.context = context;
        this.payload = payload;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return the event context
     */
    public NitricContext getContext() {
        return context;
    }

    /**
     * @return the event payload
     */
    public byte[] getPayload() {
        return payload;
    }

    /**
     * @return the event payload as text (UTF-8 encoded)
     */
    public String getPayloadText() {
        return (payload != null) ? new String(payload, StandardCharsets.UTF_8) : null;
    }

    /**
     * @return the string representation of this object
     */
    public String toString() {
        return getClass().getSimpleName() +
                "[context=" + context +
                ", payload.length=" + ((payload != null) ? payload.length : 0) +
                "]";
    }

    /**
     * @return a new NitricEvent builder class.
     */
    public static NitricEvent.Builder newBuilder() {
        return new NitricEvent.Builder();
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides the Nitric event context.
     */
    public static class NitricContext {

        /** The event requestId. */
        public final String requestID;

        /** The event source type. */
        public final String sourceType;

        /** The event source. */
        public final String source;

        /** The event payload type. */
        public final String payloadType;

        /**
         * Create a new Nitric Event Context object.
         *
         * @param requestID the event requestID
         * @param sourceType the event sourceType
         * @param source the event source
         * @param payloadType the event payload type
         */
        public NitricContext(String requestID, String sourceType, String source, String payloadType) {
            this.requestID = requestID;
            this.sourceType = sourceType;
            this.source = source;
            this.payloadType = payloadType;
        }

        /**
         * @return the string representation of this object.
         */
        public String toString() {
            return getClass().getSimpleName() +
                    "[requestId=" + requestID
                    + ",sourceType=" + sourceType
                    + ",source=" + source
                    + ",payloadType=" + payloadType
                    + "]";
        }
    }

    /**
     * Provides a NitricEvent builder class.
     *
     * @since 1.0
     */
    public static class Builder {

        private Map<String, List<String>> headers;
        private byte[] payload;

        // Private constructor to enforce request builder pattern.
        private Builder() {
        }

        /**
         * Set the event headers.
         * @param headers the event headers
         * @return the event builder instance
         */
        public Builder headers(Map<String, List<String>> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Set the event payload.
         * @param payload the event payload
         * @return the event builder instance.
         */
        public Builder payload(byte[] payload) {
            this.payload = payload;
            return this;
        }

        /**
         * @return a new Nitric Event.
         */
        public NitricEvent build() {
            var requestID = getHeaderValue("x-nitric-request-id", headers);
            var sourceType = getHeaderValue("x-nitric-source-type", headers);
            var source = getHeaderValue("x-nitric-source", headers);
            var payloadType = getHeaderValue("x-nitric-payload-type", headers);

            var context = new NitricContext(requestID, sourceType, source, payloadType);

            return new NitricEvent(context, payload);
        }

        // Private Methods ------------------------------------------------------------

        private static String getHeaderValue(String name, Map<String, List<String>> headers) {
            if (headers == null || headers.isEmpty()) {
                return null;
            }

            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(name)) {
                    return entry.getValue().get(0);
                }
            }

            return null;
        }
    }

}