package io.nitric.api.exception;

public class CancelledException extends ApiException {
    public CancelledException(String message) {
        super(message);
    }

    public CancelledException(String message, Throwable cause) {
        super(message, cause);
    }
}
