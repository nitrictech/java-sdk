package io.nitric.api.exception;

public class UnavailableException extends ApiException {

    public UnavailableException(String message) {
        super(message);
    }

    public UnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
