package io.nitric.api.exception;

public class OutOfRangeException extends ApiException {

    public OutOfRangeException(String message) {
        super(message);
    }

    public OutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
