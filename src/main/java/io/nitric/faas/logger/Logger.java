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

package io.nitric.faas.logger;

/**
 * Provides a pluggable Logger interface for the Faas server.
 */
public interface Logger {

    /**
     * Log the given information level message and arguments.
     *
     * @param format the error message format
     * @param args the message arguments
     */
    void info(String format, Object...args);

    /**
     * Log the given error message and arguments.
     *
     * @param format the error message format
     * @param args the message arguments
     */
    void error(String format, Object...args);

    /**
     * Log the given exception, error message and arguments.
     *
     * @param error the exception
     * @param format the error message format
     * @param args the message arguments
     */
    void error(Throwable error, String format, Object...args);

}
