package io.nitric.api.exception;

public class UnknownException extends ApiException {
    public UnknownException(String message) {
        super(message);
    }

    public UnknownException(String message, Throwable cause) {
        super(message, cause);
    }
}
