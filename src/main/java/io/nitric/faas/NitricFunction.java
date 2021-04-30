package io.nitric.faas;

/*-
 * #%L
 * Nitric Java SDK
 * %%
 * Copyright (C) 2021 Nitric Pty Ltd
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
 *  Provides a Nitric function handler. The <code>NitricFunction</code> interface supports pure function
 *  development with immutable request and response objects.
 * </p>
 *
 * <p>
 *  The example below provides a simple Hello World function.
 * </p>
 *
 * <pre><code class="code">
 * import io.nitric.faas.Faas;
 * import io.nitric.faas.NitricEvent;
 * import io.nitric.faas.NitricFunction;
 * import io.nitric.faas.NitricResponse;
 *
 * public class HelloWorld implements NitricFunction {
 *
 *     public NitricResponse handle(NitricEvent request) {
 *         return NitricResponse.build("Hello World");
 *     }
 *
 *     public static void main(String... args) {
 *         new Faas().start(new HelloWorld());
 *     }
 * }
 * </code></pre>
 *
 * <p>
 *  These functions return an immutable <code>NitricResponse</code> objects created using the static builder methods.
 * </p>
 *
 * @see NitricEvent
 * @see NitricResponse
 * @see NitricResponse.Builder
 *
 * @since 1.0
 */
public interface NitricFunction {

    /**
     * Handle the function request.
     *
     * @param request the function request
     * @return the function response
     */
    NitricResponse handle(NitricEvent request);

}
