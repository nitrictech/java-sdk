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

package io.nitric.util;

/**
 * Provides 'Design by Contract' utility functions class.
 */
public final class Contracts {

    /**
     * Ensure the specified parameter value is not null.
     *
     * @param paramValue the parameter value to test
     * @param paramName the name of the parameter
     */
    public static void requireNonNull(Object paramValue, String paramName) {
        if (paramValue == null) {
            throw new IllegalArgumentException("provide non-null " + paramName);
        }
    }

    /**
     * Ensure the specified parameter value is not blank.
     *
     * @param paramValue the parameter value to test
     * @param paramName the name of the parameter
     */
    public static void requireNonBlank(String paramValue, String paramName) {
        if (paramValue == null) {
            throw new IllegalArgumentException("provide non-null " + paramName);
        }
        if (paramValue.isBlank()) {
            throw new IllegalArgumentException("provide non-blank " + paramName);
        }
    }

}