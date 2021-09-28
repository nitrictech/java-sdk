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

package io.nitric.faas;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.protobuf.ByteString;

import io.nitric.faas.event.EventContext;
import io.nitric.faas.event.EventHandler;
import io.nitric.faas.event.EventMiddleware;
import io.nitric.faas.http.HttpContext;
import io.nitric.faas.http.HttpHandler;
import io.nitric.faas.http.HttpMiddleware;
import org.junit.jupiter.api.Test;

import io.nitric.proto.faas.v1.HeaderValue;
import io.nitric.proto.faas.v1.HttpTriggerContext;
import io.nitric.proto.faas.v1.TopicTriggerContext;
import io.nitric.proto.faas.v1.TriggerRequest;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Provides a TriggerProcessorTest unit test.
 */
public class TriggerProcessorTest {

    @Test
    public void test_setters() {
        var processor = new TriggerProcessor();

        var eventHandler = new MyEventHandler();
        processor.setEventHandler(eventHandler);
        assertSame(eventHandler, processor.eventHandler);

        var eventMiddleware = new MyEventMiddleware();
        List<EventMiddleware> eventMiddlewares = List.of(eventMiddleware);
        processor.setEventMiddlewares(eventMiddlewares);
        assertEquals(1, processor.eventMiddlewares.size());
        assertSame(eventMiddleware, processor.eventMiddlewares.get(0));

        var httpHandler = new MyHttpHandler();
        processor.setHttpHandler(httpHandler);
        assertSame(httpHandler, processor.httpHandler);

        var httpMiddleware = new MyHttpMiddleware();
        List<HttpMiddleware> httpMiddlewares = List.of(httpMiddleware);
        processor.setHttpMiddlewares(httpMiddlewares);
        assertEquals(1, processor.httpMiddlewares.size());
        assertSame(httpMiddleware, processor.httpMiddlewares.get(0));
    }

    @Test
    public void test_processHttpTrigger() {

        // Null Trigger
        try {
            new TriggerProcessor().processHttpTrigger(null);
            fail();
        } catch (IllegalArgumentException iae) {
        }

        // Test Handler
        final var handlerName = MyHttpHandler.class.getSimpleName();

        var triggerProcessor1 = new TriggerProcessor();
        triggerProcessor1.setHttpHandler(new MyHttpHandler());

        var request1 = TriggerRequest.newBuilder()
                .setHttp(HttpTriggerContext.newBuilder().setMethod("GET"))
                .build();

        var res1 = triggerProcessor1.processHttpTrigger(request1);

        assertNotNull(res1);
        assertTrue(res1.hasHttp());
        assertFalse(res1.hasTopic());

        assertEquals(1, res1.getHttp().getHeadersMap().size());
        assertEquals(1, res1.getHttp().getHeadersMap().get(handlerName).getValueCount());

        assertEquals(404, res1.getHttp().getStatus());
        assertEquals(handlerName, res1.getData().toStringUtf8());

        // Test Middleware & No Handler
        final var middlewareName = MyHttpMiddleware.class.getSimpleName();

        var triggerProcessor2 = new TriggerProcessor();
        triggerProcessor2.setHttpMiddlewares(List.of(new MyHttpMiddleware(), new MyHttpMiddleware()));

        var request2 = TriggerRequest.newBuilder()
                .setHttp(HttpTriggerContext.newBuilder().setMethod("POST"))
                .build();

        var res2 = triggerProcessor2.processHttpTrigger(request2);

        assertNotNull(res2);
        assertTrue(res2.hasHttp());
        assertFalse(res2.hasTopic());

        assertEquals(1, res2.getHttp().getHeadersMap().size());
        assertEquals(2, res2.getHttp().getHeadersMap().get(middlewareName).getValueCount());

        // TODO: resolve status code handling
        assertEquals(200, res2.getHttp().getStatus());
        assertEquals(middlewareName, res2.getData().toStringUtf8());

        // Test Middleware and Handler
        var triggerProcessor3 = new TriggerProcessor();
        triggerProcessor3.setHttpHandler(new MyHttpHandler());
        triggerProcessor3.setHttpMiddlewares(List.of(new MyHttpMiddleware(), new MyHttpMiddleware()));

        var request3 = TriggerRequest.newBuilder()
                .setHttp(HttpTriggerContext.newBuilder().setMethod("PUT"))
                .build();

        var res3 = triggerProcessor3.processHttpTrigger(request3);

        assertNotNull(res3);
        assertTrue(res3.hasHttp());
        assertFalse(res3.hasTopic());

        assertEquals(2, res3.getHttp().getHeadersMap().size());
        assertEquals(1, res3.getHttp().getHeadersMap().get(handlerName).getValueCount());
        assertEquals(2, res3.getHttp().getHeadersMap().get(middlewareName).getValueCount());

        assertEquals(404, res3.getHttp().getStatus());
        assertEquals(handlerName, res3.getData().toStringUtf8());
    }

    @Test
    public void test_processTopicTrigger() {
    }

    @Test
    public void test_process() {
    }

    @Test
    public void test_buildEventMiddlewareChain() {
    }

    @Test
    public void test_buildHttpMiddlewareChain() {
    }

    // Inner Classes ----------------------------------------------------------

    public static class MyEventHandler implements EventHandler {
        @Override
        public EventContext handle(EventContext context) {
            context.getResponse().data(getClass().getSimpleName());
            return context;
        }
    }

    public static class MyEventMiddleware extends EventMiddleware {
        @Override
        public EventContext handle(EventContext context, EventMiddleware next) {
            context.getResponse().data(getClass().getSimpleName());

            return next.handle(context, next.getNext());
        }
    }

    public static class MyHttpHandler implements HttpHandler {
        @Override
        public HttpContext handle(HttpContext context) {
            context.getResponse()
                    .status(404)
                    .addHeader(getClass().getSimpleName(), toString())
                    .data(getClass().getSimpleName());
            return context;
        }
    }

    public static class MyHttpMiddleware extends HttpMiddleware {
        @Override
        public HttpContext handle(HttpContext context, HttpMiddleware next) {
            context.getResponse()
                    .addHeader(getClass().getSimpleName(), toString())
                    .data(getClass().getSimpleName());

            return next.handle(context, next.getNext());
        }
    }
}
