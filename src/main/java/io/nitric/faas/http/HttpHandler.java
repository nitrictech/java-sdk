package io.nitric.faas.http;

/**
 * <p>
 *     Provide a HTTP request function handler. The <code>HttpHandler</code> interface supports pure function
 *     development with an immutable request parameter.
 * </p>
 *
 * <p>
 *     The example below provides a simple Hello World function handler.
 * </p>
 *
 * <code>
 * public class HelloWorld implements HttpHandler {
 *
 *     public HttpResponse handle(HttpRequest request) {
 *         return HttpResponse.build(200, "Hello World");
 *     }
 * }
 * </code>
 *
 * <p>
 *     These functions return an immutable <code>HttpResponse</code> objects created using the static builder methods.
 * </p>
 *
 * @see HttpRequest
 * @see HttpHandler
 * @since 1.0
 */
public interface HttpHandler {

    /**
     * Handle the function request.
     *
     * @param request the function request
     * @return the function response
     */
    HttpResponse handle(HttpRequest request);

}