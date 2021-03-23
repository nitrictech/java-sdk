package io.nitric.util;

import com.google.common.base.Strings;

/**
 * <p>
 *     Provides an Nitric Exception class which may include additional developer and operations diagnostics.
 * </p>
 *
 * @since 1.0
 */
public class NitricException extends RuntimeException {

    static final long serialVersionUID = 1;

    /**
     * Create a new NitricException with the given message.
     * @param message the error message
     */
    public NitricException(String message) {
        super(message);
    }

    /**
     * Create a new NitricException with the given message.
     * @param message the error message
     */
    public NitricException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @return a new NitricException builder
     */
    public static Builder newBuilder() {
        return new NitricException.Builder();
    }

    // Inner Classes ----------------------------------------------------------

    public static class Builder {

        String message;
        Throwable cause;
        String errorKey;
        String errorStruc;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder cause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        public Builder key(String key) {
            this.errorKey = key;
            return this;
        }

        public Builder struc(String struc) {
            this.errorStruc = struc;
            return this;
        }

        public NitricException build() {
            var errorMessage = message;

            // TODO: build message
            if (!Strings.isNullOrEmpty(errorKey)) {
                errorMessage = String.format("{ \"errorKey\": \"%s\" }", errorKey);
            }

            if (!Strings.isNullOrEmpty(errorStruc)) {
                errorMessage = String.format("{ \"errorStruc\": \"%s\" }", errorStruc);
            }

            if (cause != null) {
                return new NitricException(errorMessage, cause);

            } else {
                return new NitricException(errorMessage);
            }
        }
    }

}
