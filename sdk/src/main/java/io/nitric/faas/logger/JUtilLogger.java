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

import io.nitric.util.Contracts;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides a Java Util based Logger class.
 */
public class JUtilLogger implements io.nitric.faas.logger.Logger {

    final java.util.logging.Logger logger;

    /**
     * Create a new Java Util based Logger with the given name.
     *
     * @param name the logger name (required)
     */
    public JUtilLogger(String name) {
        Contracts.requireNonNull(name, "name");

        logger = Logger.getLogger(name);
    }

    /**
     * Log the given info level message and arguments.
     *
     * @param format the info message format
     * @param args the message arguments
     */
    @Override
    public void info(String format, Object...args) {
        String msg = String.format(format, args);
        logger.log(Level.INFO, msg);
    }

    /**
     * Log the given error message and arguments.
     *
     * @param format the error message format
     * @param args the message arguments
     */
    @Override
    public void error(String format, Object...args) {
        String msg = String.format(format, args);
        logger.log(Level.SEVERE, msg);
    }

    /**
     * Log the given exception, error message and arguments.
     *
     * @param error the exception
     * @param format the error message format
     * @param args the message arguments
     */
    @Override
    public void error(Throwable error, String format, Object...args) {
        String msg = String.format(format, args);
        logger.log(Level.SEVERE, msg, error);
    }
}
