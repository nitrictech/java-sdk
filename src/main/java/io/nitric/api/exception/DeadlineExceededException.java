package io.nitric.api.exception;

public class DeadlineExceededException extends ApiException {
    public DeadlineExceededException(String message) {
        super(message);
    }

    public DeadlineExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
