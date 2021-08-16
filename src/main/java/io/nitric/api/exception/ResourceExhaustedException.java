package io.nitric.api.exception;

public class ResourceExhaustedException extends ApiException {
    public ResourceExhaustedException(String message) {
        super(message);
    }

    public ResourceExhaustedException(String message, Throwable cause) {
        super(message, cause);
    }
}
