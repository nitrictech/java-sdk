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

/**
 * <p>
 *  Provides pluggable Logger support for Faas.
 * </p>
 *
 * <p>
 *  The default Faas Logger is <code>JUtilLogger</code> which provides an adapter to the Java Util Logging library
 *  which supports Native compilation with GraalVM. Developers can create a Logger adapters for their preferred
 *  library on SIEM logging service.
 * </p>
 *
 * <h3>Example Logging Adaptor</h3>
 *
 * <p>
 *  An trivial System.out Logger adaptor example is provided below.
 * </p>
 *
 * <pre><code class="code">
 * public class SystemLogger implements Logger {
 *
 *     &#64;Override
 *     public void info(String format, Object... args) {
 *         System.out.printf("INFO: " + format + "\n", args);
 *     }
 *
 *     &#64;Override
 *     public void error(String format, Object... args) {
 *         System.err.printf("ERROR: " + format + "\n", args);
 *     }
 *
 *     &#64;Override
 *     public void error(Throwable error, String format, Object... args) {
 *         System.out.printf("ERROR: " + format + "\n", args);
 *         error.printStackTrace();
 *     }
 * }
 * </code></pre>
 *
 * <p>
 *  The Faas is then configure with our custom SystemLogger.
 * </p>
 *
 * <pre><code class="code">
 * public class ReadHandler implements HttpHandler {
 *     ...
 *
 *     public static void main(String[] args) {
 *         var handler = new ReadHandler();
 *         var logger = new SystemLogger();
 *
 *         new Faas()
 *             .http(handler)
 *             .logger(logger)
 *             .start();
 *     }
 * }
 * </code></pre>
 */
package io.nitric.faas.logger;