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
import io.nitric.proto.error.v1.ErrorDetails;

/**
 * Provides a Nitric service InvalidArgument exception class.
 */
public class InvalidArgumentException extends NitricException {

    /**
     * Create a new service InvalidArgumentException with the given information.
     *
     * @param code the GRPC status code
     * @param message the error message
     * @param cause the error root cause
     * @param ed the structured GRPC error details
     */
    public InvalidArgumentException(Code code, String message, StatusRuntimeException cause, ErrorDetails ed) {
        super(code, message, cause, ed);
    }

}
