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

/**
 * InvalidArgumentException
 *
 * The client specified an invalid argument.
 * This indicates arguments that are problematic regardless of the state of the system (e.g., a malformed file name).
 */
public class InvalidArgumentException extends ApiException {

    /**
     * InvalidArgumentException constructor
     */
    public InvalidArgumentException(String message) {
        super(message);
    }

    /**
     * InvalidArgumentException constructor
     */
    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
