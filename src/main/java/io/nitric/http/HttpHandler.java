package io.nitric.http;

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
 * <pre>
 * public class HelloWorld implements HttpHandler {
 *
 *     public HttpResponse handle(HttpRequest request) {
 *         return HttpResponse.build("Hello World");
 *     }
 * }
 * </pre>
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