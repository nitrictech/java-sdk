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

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import io.nitric.proto.error.v1.ErrorDetails;
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
    final String message;
    final String errorCause;
    final String service;
    final String plugin;
    final String args;

    // Constructors -----------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    NitricException(String message) {
        super(message);
        this.code = Code.UNKNOWN;
        this.errorCause = null;
        this.message = message;
        this.service = null;
        this.plugin = null;
        this.args = null;
    }

    /*
     * Enforce package builder patterns.
     */
    NitricException(String message, Throwable cause) {
        super(message, cause);
        this.code = Code.UNKNOWN;
        this.message = message;
        this.errorCause = null;
        this.service = null;
        this.plugin = null;
        this.args = null;
    }

    /*
     * Enforce package builder patterns.
     */
    NitricException(Code code, String message, StatusRuntimeException cause, ErrorDetails errorDetails) {
        super(message, cause);
        this.code = (code != null) ? code : Code.UNKNOWN;

        if (errorDetails != null) {
            this.message = (errorDetails.getMessage() != null) ? errorDetails.getMessage() : message;
            this.errorCause = errorDetails.getCause();
            this.service = errorDetails.getService();
            this.plugin = errorDetails.getPlugin();
            this.args = errorDetails.getArgs();
        } else {
            this.message = message;
            this.errorCause = null;
            this.service = null;
            this.plugin = null;
            this.args = null;
        }
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
     * Return the error message.
     *
     * @return the error message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Return the cause of the error.
     *
     * @return the cause of the error.
     */
    public String getErrorCause() {
        return errorCause;
    }

    /**
     * Return the API service invoked, e.g. 'Service.Method'.
     *
     * @return the name of the API service invoked.
     */
    public String getService() {
        return service;
    }

    /**
     * Return the name of the service plugin invoked, e.g. 'PluginService.Method'.
     *
     * @return the name of the service plugin invoked.
     */
    public String getPlugin() {
        return plugin;
    }

    /**
     * Return the plugin method arguments. Please note only non-sensitive data should be returned.
     *
     * @return the plugin method arguments
     */
    public String getArgs() {
        return args;
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
                .append("[");

        final var indent = "\n    ";
        builder.append(indent + "code: " + getCode());
        builder.append(indent + "message: " + getMessage());
        if (getErrorCause() != null) {
            builder.append(indent + "cause: " + getErrorCause());
        }
        if (getService() != null) {
            builder.append(indent + "service: " + getService());
        }
        if (getPlugin() != null) {
            builder.append(indent + "plugin: " + getPlugin());
        }
        if (getArgs() != null) {
            builder.append(indent + "args: " + getArgs());
        }
        builder.append("\n]");

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

        ErrorDetails errorDetails = null;
        com.google.rpc.Status status = StatusProto.fromThrowable(sre);
        for (Any any : status.getDetailsList()) {
            if (any.is(ErrorDetails.class)) {
                try {
                    errorDetails = any.unpack(ErrorDetails.class);
                } catch (InvalidProtocolBufferException ipbe) {
                    ipbe.printStackTrace();
                }
                break;
            }
        }

        switch (sre.getStatus().getCode()) {
            case NOT_FOUND:
                return new io.nitric.api.NotFoundException(code, sre.getMessage(), sre, errorDetails);
            case UNAVAILABLE:
                return new io.nitric.api.UnavailableException(code, sre.getMessage(), sre, errorDetails);
           default:
               return new NitricException(code, sre.getMessage(), sre, errorDetails);
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