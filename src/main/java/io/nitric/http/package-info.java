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
 *  Provides the HTTP Server (FaaS) classes.
 * </p>
 *
 * <p>
 *  The example below illustrates the HTTP API.
 * </p>
 *
 * <pre><code class="code">
 * package com.example;
 *
 * import io.nitric.http.HttpHandler;
 * import io.nitric.http.HttpRequest;
 * import io.nitric.http.HttpResponse;
 * import io.nitric.http.HttpServer;
 *
 * public class Handler implements HttpHandler {
 *
 *     &commat;Override
 *     public HttpResponse handle(HttpRequest request) {
 *         return HttpResponse.build("Hello World");
 *     }
 *
 *     public static void main(String... args) {
 *         HttpServer().start(new Handler());
 *     }
 * }
 * </code></pre>
 */
package io.nitric.http;