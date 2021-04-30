package io.nitric.http;

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
 *  Provides a Nitric HTTP function handler. The <code>HttpHandler</code> interface supports pure function
 *  development with immutable request and response objects.
 * </p>
 *
 * <p>
 *  The example below provides a simple Hello World HTTP handler.
 * </p>
 *
 * <pre><code class="code">
 * import io.nitric.http.HttpHandler;
 * import io.nitric.http.HttpRequest;
 * import io.nitric.http.HttpResponse;
 * ...
 *
 * public class HelloWorld implements HttpHandler {
 *
 *     public HttpResponse handle(HttpRequest request) {
 *         return HttpResponse.build("Hello World");
 *     }
 * }
 * </code></pre>
 *
 * <p>
 *  These functions return an immutable <code>HttpResponse</code> objects created using the static builder methods.
 * </p>
 *
 * @see HttpRequest
 * @see HttpResponse
 * @see HttpResponse.Builder
 *
 * @since 1.0
 */
public interface HttpHandler {

    /**
     * Handle the HTTP request.
     *
     * @param request the HTTP request
     * @return the HTTP response
     */
    HttpResponse handle(HttpRequest request);

}
