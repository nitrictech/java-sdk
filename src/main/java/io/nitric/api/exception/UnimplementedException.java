package io.nitric.api.exception;

public class UnimplementedException extends ApiException {
    public UnimplementedException(String message) {
        super(message);
    }

    public UnimplementedException(String message, Throwable cause) {
        super(message, cause);
    }
}
