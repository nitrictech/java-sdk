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
 *      Event://www.apache.org/licenses/LICENSE-2.0
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
 * Provides a EventHandler to EventMiddleware adapter class.
 */
public class EventMiddlewareAdapter extends EventMiddleware {

    /** The EventHandler to adapt. */
    protected final EventHandler handler;

    /**
     * Create a new EventMiddleware adapter from the given EventHandler object.
     *
     * @param handler the EventHandler to adapt to middleware
     */
    public EventMiddlewareAdapter(EventHandler handler) {
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

        var ctx  = handler.handle(context);

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
