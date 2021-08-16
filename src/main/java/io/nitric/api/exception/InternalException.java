package io.nitric.api.exception;

public class InternalException extends ApiException {
    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
