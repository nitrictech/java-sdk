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
 * UnavailableException
 *
 * The service is currently unavailable.
 * This is most likely a transient condition, which can be corrected by retrying with a backoff.
 * Note that it is not always safe to retry non-idempotent operations.
 */
public class UnavailableException extends ApiException {

    /**
     * UnavailableException constructor
     */
    public UnavailableException(String message) {
        super(message);
    }

    /**
     * UnavailableException constructor
     */
    public UnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
