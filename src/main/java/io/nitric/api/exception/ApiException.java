package io.nitric.api.exception;

import io.grpc.StatusRuntimeException;


/**
 *
 */
public abstract class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ApiException fromGrpcServiceException(StatusRuntimeException e) {
       switch (e.getStatus().getCode()) {
           case CANCELLED:
               return new CancelledException(e.getMessage(), e);
           case INVALID_ARGUMENT:
               return new InvalidArgumentException(e.getMessage(), e);
           case DEADLINE_EXCEEDED:
               return new DeadlineExceededException(e.getMessage(), e);
           case NOT_FOUND:
               return new NotFoundException(e.getMessage(), e);
           case ALREADY_EXISTS:
               return new AlreadyExistsException(e.getMessage(), e);
           case PERMISSION_DENIED:
               return new PermissionDeniedException(e.getMessage(), e);
           case RESOURCE_EXHAUSTED:
               return new ResourceExhaustedException(e.getMessage(), e);
           case FAILED_PRECONDITION:
               return new FailedPreconditionException(e.getMessage(), e);
           case ABORTED:
               return new AbortedException(e.getMessage(), e);
           case OUT_OF_RANGE:
               return new OutOfRangeException(e.getMessage(), e);
           case UNIMPLEMENTED:
               return new UnimplementedException(e.getMessage(), e);
           case INTERNAL:
               return new InternalException(e.getMessage(), e);
           case UNAVAILABLE:
               return new UnavailableException(e.getMessage(), e);
           case DATA_LOSS:
               return new DataLossException(e.getMessage(), e);
           case UNAUTHENTICATED:
               return new UnauthenticatedException(e.getMessage(), e);
           default:
               return new UnknownException(e.getMessage(), e);

       }
    }
}
