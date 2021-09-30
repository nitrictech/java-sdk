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
 *  Provides the FaaS server and micro-framework classes. The FaaS provides a pluggable micro-framework for building
 *  HTTP and Event Topic handler functions and middleware.
 * </p>
 *
 * <p>
 *  This framework uses the Middleware design pattern for building chainable middleware and function handlers.
 *  The Middleware pattern is popularized with JavaScript frameworks such as Express.
 * </p>
 *
 * <p>
 *  The Middleware design pattern is also known as the 'Chain of Responsibility' whereby a chain of middleware can
 *  process a request with each middleware calling the next handler in the chain. This is equivalent to the J2EE Servlet
 *  Filters API.
 * </p>
 *
 * <p>
 * Using Middleware you can compose http and event processing pipelines in which middleware for perform operations
 * like authentication before your handler function is called. Middleware can terminate the processing of a request
 * at any stage by simply not calling the next handler in the chain.
 * </p>
 *
 * <h3>Simple Handler Example</h3>
 *
 * <p>
 *  A simple HelloWorld example is provided below with a HttpHandler function is run inside a <code>Faas</code> server
 *  process kicked off in the HelloWorld <code>main()</code> method.
 * </p>
 *
 * <pre><code class="code">
 * import io.nitric.faas.Faas;
 * import io.nitric.faas.http.HttpContext;
 * import io.nitric.faas.http.HttpHandler;
 *
 * public class HelloWorld implements HttpHandler {
 *
 *     &#64;Override
 *     public HttpContext handle(HttpContext context) {
 *         context.getResponse()
 *            .addHeader("Content-Type", "text/plain")
 *            .data("hello world!");
 *
 *         return context;
 *     }
 *
 *     public static void main(String[] args) {
 *         var handler = new HelloWorld();
 *
 *         new Faas().http(handler).start();
 *     }
 * }
 * </code></pre>
 *
 * <h3>Error Handling Example</h3>
 *
 * <p>
 *  The example below provides a more real world example where we are using a reading a "customers" collection
 *  document. If the document is not found then we return a HTTP 404 response.  Note in this example we are injecting
 *  documents service dependency into the handler constructor. This is to support unit testing where we may inject a
 *  mock documents service.
 * </p>
 *
 * <pre><code class="code">
 * import io.nitric.api.NotFoundException;
 * import io.nitric.api.document.Documents;
 * import io.nitric.faas.Faas;
 * import io.nitric.faas.http.HttpContext;
 * import io.nitric.faas.http.HttpHandler;
 *
 * public class ReadHandler implements HttpHandler {
 *
 *     final Documents documents;
 *
 *     public ReadHandler(Documents documents) {
 *         this.documents = documents;
 *     }
 *
 *     &#64;Override
 *     public HttpContext handle(HttpContext context) {
 *         var paths = context.getRequest().getPath().split("/");
 *         var id = paths[paths.length - 1];
 *
 *         try {
 *             var json = documents.collection("customers")
 *                 .doc(id)
 *                 .getJson();
 *
 *             context.getResponse()
 *                 .addHeader("Content-Type", "application/json")
 *                 .data(json);
 *
 *         } catch (NotFoundException nfe) {
 *             context.getResponse()
 *                 .status(404)
 *                 .data("Document not found: " + id);
 *         }
 *
 *         return context;
 *     }
 *
 *     public static void main(String[] args) {
 *         var documents = new Documents();
 *         var handler = new ReadHandler(documents);
 *
 *         new Faas().http(handler).start();
 *     }
 * }
 * </code></pre>
 *
 * <h3>Middleware Example</h3>
 *
 * <p>
 *   With this example we are creating a simple logging middleware which will log the duration of the handler's
 *   execution.
 * </p>
 *
 * <pre><code class="code">
 * import io.nitric.faas.http.HttpContext;
 * import io.nitric.faas.http.HttpMiddleware;
 *
 * public class LoggerMiddleware extends HttpMiddleware {
 *
 *     private Logger logger = Logger.getLogger(LoggerMiddleware.class);
 *
 *     &#64;Override
 *     public HttpContext handle(HttpContext context, HttpMiddleware next) {
 *        var start = System.currentTimeMillis();
 *
 *        var ctx = next.handle(context, next.getNext());
 *
 *        var duration = System.currentTimeMillis() - start;
 *        logger.info("HTTP %s %s -> %s handled in %s ms\n",
 *                    ctx.getRequest().getMethod(),
 *                    ctx.getRequest().getPath(),
 *                    next.getClass().getSimpleName(),
 *                    duration);
 *
 *        return ctx;
 *     }
 * }
 * </code></pre>
 *
 * <p>
 *   The <code>LoggerMiddleware</code> is then configured in the <code>Faas</code> ahead of the http handler to
 *   ensure its called first.
 * </p>
 *
 * <pre><code class="code">
 * public class ReadHandler implements HttpHandler {
 *     ...
 *
 *     public static void main(String[] args) {
 *         var middleware = new LoggerMiddleware();
 *         var documents = new Documents();
 *         var handler = new ReadHandler(documents);
 *
 *         new Faas()
 *             .http(middleware)
 *             .http(handler)
 *             .start();
 *     }
 * }
 * </code></pre>
 */
package io.nitric.faas;