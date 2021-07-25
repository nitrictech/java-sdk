package io.nitric.faas;

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
 * import io.nitric.faas.Trigger;
 * import io.nitric.faas.Response;
 *
 * public class Handler implements NitricFunction {
 *
 *     &commat;Override
 *     public Response handle(Trigger trigger) {
 *         return trigger.buildResponse("Hello World");
 *     }
 *
 *     public static void main(String... args) {
 *         Faas.start(new Handler());
 *     }
 * }
 * </code></pre>
 *
 * @see Trigger
 * @see Response
 *
 * @since 1.0
 */
public interface NitricFunction {

    /**
     * Handle the function request.
     *
     * @param trigger the function trigger
     * @return the function response
     */
    Response handle(Trigger trigger);

}
