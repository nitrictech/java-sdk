package io.nitric.api.exception;

public class DataLossException extends ApiException {
    public DataLossException(String message) {
        super(message);
    }

    public DataLossException(String message, Throwable cause) {
        super(message, cause);
    }
}
