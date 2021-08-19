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

package io.nitric.api;

import io.grpc.StatusRuntimeException;
import io.nitric.util.Contracts;

/**
 * <p>
 *  Provides a Nitric Service API exception class. NitricException objects may be thrown by the Service API when
 *  performing underlying GRPC service calls.
 * </p>
 *
 * @see NotFoundException
 * @see UnavailableException
 */
public class NitricException extends RuntimeException {

    /** The GRPC error code. */
    public enum Code {
        /** Operation was aborted. */
        ABORTED,
        /** Create an entity failed because one already exists. */
        ALREADY_EXISTS,
        /** Operation was cancelled (typically by the caller). */
        CANCELLED,
        /** Indicates unrecoverable data loss or corruption. */
        DATA_LOSS,
        /** Operation expired before completion. */
        DEADLINE_EXCEEDED,
        /** Operation was rejected because the system is not in a state required. */
        FAILED_PRECONDITION,
        /** Underlying system is broken. */
        INTERNAL,
        /** Indicates client specified an invalid argument. */
        INVALID_ARGUMENT,
        /** Requested entity was not found. */
        NOT_FOUND,
        /** Operation was attempted past the valid range. */
        OUT_OF_RANGE,
        /** Caller does not have permission to execute the specified operation. */
        PERMISSION_DENIED,
        /** System resource has been exhausted. */
        RESOURCE_EXHAUSTED,
        /** Service unavailable, generally transient maybe retried. */
        UNAVAILABLE,
        /** Request does not have valid authentication credentials. */
        UNAUTHENTICATED,
        /** Operation is not implemented or supported. */
        UNIMPLEMENTED,
        /** Errors raised by APIs that do not return enough error information may be converted to this error. */
        UNKNOWN
    }

    final Code code;

    // Constructors -----------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    NitricException(String message) {
        super(message);
        this.code = Code.UNKNOWN;
    }

    /*
     * Enforce package builder patterns.
     */
    NitricException(String message, Throwable cause) {
        super(message, cause);
        this.code = Code.UNKNOWN;
    }

    /**
     * Create a Nitric API Exception with the given code, message and cause.
     *
     * @param code the GRPC code
     * @param message the error message
     * @param cause the error cause
     */
    public NitricException(Code code, String message, Throwable cause) {
        super(message, cause);
        this.code = (code != null) ? code : Code.UNKNOWN;
    }

    // Public Methods ----------------------------------------------------------

    /**
     * Return the GRPC error code.
     *
     * @return the GRPC error code
     */
    public Code getCode() {
        return code;
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        var builder = new StringBuilder()
                .append(getClass().getName())
                .append(": ")
                .append(getCode());

        String message = getLocalizedMessage();
        if (message != null) {
            builder.append(": ").append(message);
        }

        return builder.toString();
    }

    /**
     * Create a new Nitric API Exception from the GRPC StatusRuntimeException.
     *
     * @param sre the GRPC service StatusRuntimeException (required)
     * @return a new Nitric API Exception
     */
    public static NitricException build(StatusRuntimeException sre) {
        Contracts.requireNonNull(sre, "sre");

        var code = fromGrpcCode(sre.getStatus().getCode());

        switch (sre.getStatus().getCode()) {
            case NOT_FOUND:
                return new io.nitric.api.NotFoundException(code, sre.getMessage(), sre);
            case UNAVAILABLE:
                return new io.nitric.api.UnavailableException(code, sre.getMessage(), sre);
           default:
               return new NitricException(code, sre.getMessage(), sre);
        }
    }

    // Package Private Methods ------------------------------------------------

    static Code fromGrpcCode(io.grpc.Status.Code code) {
        switch (code) {
            case ABORTED:
                return Code.ABORTED;
            case ALREADY_EXISTS:
                return Code.ALREADY_EXISTS;
            case CANCELLED:
                return Code.CANCELLED;
            case DATA_LOSS:
                return Code.DATA_LOSS;
            case DEADLINE_EXCEEDED:
                return Code.DEADLINE_EXCEEDED;
            case FAILED_PRECONDITION:
                return Code.FAILED_PRECONDITION;
            case INTERNAL:
                return Code.INTERNAL;
            case INVALID_ARGUMENT:
                return Code.INVALID_ARGUMENT;
            case NOT_FOUND:
                return Code.NOT_FOUND;
            case OUT_OF_RANGE:
                return Code.OUT_OF_RANGE;
            case PERMISSION_DENIED:
                return Code.PERMISSION_DENIED;
            case RESOURCE_EXHAUSTED:
                return Code.RESOURCE_EXHAUSTED;
            case UNAVAILABLE:
                return Code.UNAVAILABLE;
            case UNAUTHENTICATED:
                return Code.UNAUTHENTICATED;
            case UNIMPLEMENTED:
                return Code.UNIMPLEMENTED;
            case UNKNOWN:
                return Code.UNKNOWN;
            default:
                throw new UnsupportedOperationException("Unsupported GRPC Code: " + code);
        }
    }

}