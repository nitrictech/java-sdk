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

package io.nitric.faas2.event;

/**
 * <p>
 * Provides an Event Handler middleware class.
 * </p>
 *
 * <p>
 * Middleware handlers follow the 'Chain of Responsibility' design pattern whereby they
 * invoke the next handler which can handle the request. This pattern is equivalent
 * to the J2EE Servlet Filters or JavaScript front-end middleware design patterns.
 * </p>
 */
public interface EventMiddleware {

    /**
     * Handle the Event Request and invoke the next handler in the chain.
     *
     * @param context the Event request/response context
     * @param next the next EventMiddleware handler to invoke in the chain
     * @return the context object returned by the next handler
     */
    public EventContext handle(EventContext context, EventMiddleware next);

}