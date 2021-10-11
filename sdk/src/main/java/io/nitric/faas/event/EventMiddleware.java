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

package io.nitric.faas.event;

import io.nitric.util.Contracts;

/**
 * <p>
 * Provides a Event Handler middleware class.
 * </p>
 *
 * @see EventContext
 * @see EventHandler
 */
public abstract class EventMiddleware {

    /** The next EventMiddleware to execute. */
    protected EventMiddleware next;

    /**
     * Handle the Event Request and invoke the next handler in the chain.
     *
     * @param context the Event request/response context
     * @param next the next EventMiddleware handler to invoke in the chain
     * @return the context object returned by the next handler
     */
    public abstract EventContext handle(EventContext context, EventMiddleware next);

    /**
     * Return the next EventMiddleware to process request context. If no next
     * middleware configured, then this method will return the FinalMiddleware.
     *
     * @return the next EventMiddleware to process request context
     */
    public EventMiddleware getNext() {
        return (next != null) ? next : FinalMiddleware.FINAL_MIDDLEWARE;
    }

    /**
     * Set the next EventMiddleware to process request context.
     *
     * @param middleware the next EventMiddleware to process request context (required)
     */
    public void setNext(EventMiddleware middleware) {
        Contracts.requireNonNull(middleware, "middleware");

        this.next = middleware;
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a EventHandler to EventMiddleware adapter class.
     */
    public static class HandlerAdapter extends EventMiddleware {

        /** The EventHandler to adapt. */
        protected final EventHandler handler;

        /**
         * Create a new EventMiddleware adapter from the given EventHandler object.
         *
         * @param handler the EventHandler to adapt to middleware
         */
        public HandlerAdapter(EventHandler handler) {
            Contracts.requireNonNull(handler, "handler");

            this.handler = handler;
        }

        /**
         * Handle the Event Request and invoke the next handler in the chain.
         *
         * @param context the Event request/response context
         * @param next the next EventMiddleware handler to invoke in the chain
         * @return the context object returned by the next handler
         */
        @Override
        public EventContext handle(EventContext context, EventMiddleware next) {

            var response  = handler.handle(context);
            if (response == null) {
                var msg = "handler " + handler.getClass().getCanonicalName() + " returned null response object";
                throw new IllegalStateException(msg);
            }

            var ctx = new EventContext(context.getRequest(), response);

            return next.handle(ctx, next.getNext());
        }

        /**
         * Return the wrapped EventHandler object.
         *
         * @return the wrapped EventHandler object
         */
        public EventHandler getHandler() {
            return handler;
        }
    }

    /**
     * Provides the final EventMiddleware in the chain. This handler will simply return the context and not invoke the
     * next middleware.
     */
    public static class FinalMiddleware extends EventMiddleware {

        /** The final middleware in the chain. */
        public static final FinalMiddleware FINAL_MIDDLEWARE = new FinalMiddleware();

        /**
         * This method will simply return the context and not invoke the next middleware.
         *
         * @param context the Event request/response context
         * @param next the next EventMiddleware handler which will be ignored
         * @return the context invoked with
         */
        @Override
        public EventContext handle(EventContext context, EventMiddleware next) {
            return context;
        }
    }

}
