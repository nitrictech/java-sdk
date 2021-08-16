package io.nitric.api.exception;

public class FailedPreconditionException extends ApiException {
    public FailedPreconditionException(String message) {
        super(message);
    }

    public FailedPreconditionException(String message, Throwable cause) {
        super(message, cause);
    }
}
