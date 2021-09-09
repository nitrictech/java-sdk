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

/**
 * <p>
 *  Provides a Nitric API service unavailable exception class. If this exception is thrown application code may
 *  try this operation again later if it is a transient error.
 * </p>
 */
public class UnavailableException extends NitricException {

    /**
     * Create a Nitric Service Unavailable Exception with the given code, message and cause.
     *
     * @param code the GRPC error code
     * @param message the error message
     * @param cause the error cause
     */
    public UnavailableException(Code code, String message, Throwable cause) {
        super(code, message, cause);
    }

}
