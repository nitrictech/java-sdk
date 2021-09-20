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

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Provides an EventMiddleware test case.
 */
public class EventMiddlewareTest {


    @Test
    public void test_next() {
        var middleware1 = new TestEventMiddleware();

        var middleware2 = new TestEventMiddleware();

        middleware1.setNext(middleware2);

        assertSame(middleware2, middleware1.getNext());
    }

    public static class TestEventMiddleware extends EventMiddleware {

        @Override
        public EventContext handle(EventContext context, EventMiddleware next) {
            return null;
        }
    }

}
