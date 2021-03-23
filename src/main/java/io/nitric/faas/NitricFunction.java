package io.nitric.faas;

/**
 * <p>
 *     Provides a Nitric function handler. The <code>NitricFunction</code> interface supports pure function
 *     development with immutable request and response objects.
 * </p>
 *
 * <p>
 *     The example below provides a simple Hello World function.
 * </p>
 *
 * <code>
 * public class HelloWorld implements NitricFunction {
 *
 *     public NitricResponse handle(NitricEvent request) {
 *         return NitricResponse.build("Hello World");
 *     }
 * }
 * </code>
 *
 * <p>
 *     These functions return an immutable <code>NitricResponse</code> objects created using the static builder methods.
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