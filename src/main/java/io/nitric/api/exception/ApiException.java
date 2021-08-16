/*-
 * #%L
 * Nitric Java SDK
 * %%
 * Copyright (C) 2021 Nitric Technologies Pty Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package io.nitric.api.exception;

import io.grpc.StatusRuntimeException;


/**
 * ApiException
 *
 * Parent exception for nitric API related exceptions
 */
public abstract class ApiException extends RuntimeException {

    /**
     * ApiException constructor
     */
    public ApiException(String message) {
        super(message);
    }

    /**
     * ApiException constructor
     */
    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * fromGrpcServiceException
     *
     * Converts a grpc StatusRuntimeException to an ApiException
     *
     * @param e - StatusRuntimeException returned by gRPC call
     * @return - ApiException
     */
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
