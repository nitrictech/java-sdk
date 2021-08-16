package io.nitric.api.exception;

public class AbortedException extends ApiException {
    public AbortedException(String message) {
        super(message);
    }

    public AbortedException(String message, Throwable cause) {
        super(message, cause);
    }
}
