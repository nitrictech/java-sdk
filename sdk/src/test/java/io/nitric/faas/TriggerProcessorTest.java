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

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.nitric.api.NitricException;
import io.nitric.api.NotFoundException;
import io.nitric.faas.event.EventContext;
import io.nitric.faas.event.EventHandler;
import io.nitric.faas.event.EventMiddleware;
import io.nitric.faas.http.HttpContext;
import io.nitric.faas.http.HttpHandler;
import io.nitric.faas.http.HttpMiddleware;
import io.nitric.proto.error.v1.ErrorDetails;
import io.nitric.proto.error.v1.ErrorScope;
import io.nitric.proto.faas.v1.HttpTriggerContext;
import io.nitric.proto.faas.v1.TopicTriggerContext;
import io.nitric.proto.faas.v1.TriggerRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Provides a TriggerProcessorTest unit test.
 */
public class TriggerProcessorTest {

    private final static String HTTP_ERROR_MSG = "Error occurred see logs for details.";

    @Test
    public void test_setters() {
        var processor = new TriggerProcessor();

        var eventMiddleware = new TestEventMiddleware();
        List<EventMiddleware> eventMiddlewares = List.of(eventMiddleware);
        processor.setEventMiddlewares(eventMiddlewares);
        assertEquals(1, processor.eventMiddlewares.size());
        assertSame(eventMiddleware, processor.eventMiddlewares.get(0));

        var httpMiddleware = new TestHttpMiddleware();
        List<HttpMiddleware> httpMiddlewares = List.of(httpMiddleware);
        processor.setHttpMiddlewares(httpMiddlewares);
        assertEquals(1, processor.httpMiddlewares.size());
        assertSame(httpMiddleware, processor.httpMiddlewares.get(0));
    }

    @Test
    public void test_buildEventMiddlewareChain() {
        var processor = new TriggerProcessor();

        try {
            processor.buildEventMiddlewareChain();
            fail();
        } catch (IllegalStateException ise) {
        }

        var eventMiddleware1 = new TestEventMiddleware();
        var eventMiddleware2 = new TestEventMiddleware();

        List<EventMiddleware> eventMiddlewares = List.of(eventMiddleware1, eventMiddleware2);

        processor.setEventMiddlewares(eventMiddlewares);

        var first = processor.buildEventMiddlewareChain();
        assertSame(eventMiddleware1, ((TriggerProcessor.EventMiddlewareWrapper) first).target);
        assertSame(eventMiddleware2, ((TriggerProcessor.EventMiddlewareWrapper) first.getNext()).target);
        assertSame(EventMiddleware.FINAL_MIDDLEWARE, first.getNext().getNext());
    }

    @Test
    public void test_buildHttpMiddlewareChain() {
        var processor = new TriggerProcessor();

        try {
            processor.buildHttpMiddlewareChain();
            fail();
        } catch (IllegalStateException ise) {
        }

        var httpMiddleware1 = new TestHttpMiddleware();
        var httpMiddleware2 = new TestHttpMiddleware();

        List<HttpMiddleware> httpMiddlewares = List.of(httpMiddleware1, httpMiddleware2);

        processor.setHttpMiddlewares(httpMiddlewares);

        var first = processor.buildHttpMiddlewareChain();
        assertSame(httpMiddleware1, ((TriggerProcessor.HttpMiddlewareWrapper) first).target);
        assertSame(httpMiddleware2, ((TriggerProcessor.HttpMiddlewareWrapper) first.getNext()).target);
        assertSame(HttpMiddleware.FINAL_MIDDLEWARE, first.getNext().getNext());
    }

    @Test
    public void test_processHttpTrigger() {

        // Null Trigger
        try {
            new TriggerProcessor().processHttpTrigger(null);
            fail();
        } catch (IllegalArgumentException iae) {
        }

        // Test Handler & Middlewares
        final var handlerName = TestHttpHandler.class.getSimpleName();
        final var middlewareName = TestHttpHandler.class.getSimpleName();

        var handler = new TestHttpHandler();
        var middleware = new TestHttpMiddleware();

        var triggerProcessor1 = new TriggerProcessor();
        triggerProcessor1.setHttpMiddlewares(List.of(middleware, new HttpMiddleware.HandlerAdapter(handler)));

        var request1 = TriggerRequest.newBuilder()
            .setHttp(HttpTriggerContext.newBuilder().setMethod("GET"))
            .build();

        var res1 = triggerProcessor1.processHttpTrigger(request1);

        assertNotNull(res1);
        assertTrue(res1.hasHttp());
        assertFalse(res1.hasTopic());

        assertEquals(2, res1.getHttp().getHeadersMap().size());
        assertEquals(1, res1.getHttp().getHeadersMap().get(handlerName).getValueCount());
        assertEquals(1, res1.getHttp().getHeadersMap().get(middlewareName).getValueCount());

        assertEquals(404, res1.getHttp().getStatus());
        assertEquals(handlerName, res1.getData().toStringUtf8());
        assertTrue(middleware.invokedTime > 0);
        assertTrue(handler.invokedTime > 0);
        assertTrue(middleware.invokedTime < handler.invokedTime);

        // Test Null context result
        HttpMiddleware nullMiddleware = new NullHttpMiddleware();

        var triggerProcessor2 = new TriggerProcessor();
        triggerProcessor2.setHttpMiddlewares(List.of(middleware, nullMiddleware));

        var request2 = TriggerRequest.newBuilder()
                .setHttp(HttpTriggerContext.newBuilder().setMethod("PUT"))
                .build();

        var res2 = triggerProcessor2.processHttpTrigger(request2);

        assertNotNull(res2);
        assertTrue(res2.hasHttp());
        assertFalse(res2.hasTopic());

        assertEquals(500, res2.getHttp().getStatus());
        assertEquals(HTTP_ERROR_MSG, res2.getData().toStringUtf8());

        // Test error thrown middleware
        HttpMiddleware errorMiddleware = new ErrorHttpMiddleware();

        var triggerProcessor3 = new TriggerProcessor();
        triggerProcessor3.setHttpMiddlewares(List.of(middleware, errorMiddleware));

        var request3 = TriggerRequest.newBuilder()
                .setHttp(HttpTriggerContext.newBuilder().setMethod("DELETE").setPath("/customer"))
                .build();

        var res3 = triggerProcessor3.processHttpTrigger(request3);
        assertNotNull(res3);

        assertEquals(500, res3.getHttp().getStatus());
        assertEquals(HTTP_ERROR_MSG, res3.getData().toStringUtf8());

        // Test error thrown handler
        HttpMiddleware errorHandlerMiddleware = new HttpMiddleware.HandlerAdapter(new ErrorHttpHandler());

        var triggerProcessor4 = new TriggerProcessor();
        triggerProcessor4.setHttpMiddlewares(List.of(middleware, errorHandlerMiddleware));

        var request4 = TriggerRequest.newBuilder()
                .setHttp(HttpTriggerContext.newBuilder().setMethod("DELETE").setPath("/customer"))
                .build();

        var res4 = triggerProcessor4.processHttpTrigger(request4);
        assertNotNull(res4);

        assertEquals(500, res4.getHttp().getStatus());
        assertEquals(HTTP_ERROR_MSG, res4.getData().toStringUtf8());
    }

    @Test
    public void test_processTopicTrigger() {

        // Null Trigger
        try {
            new TriggerProcessor().processTopicTrigger(null);
            fail();
        } catch (IllegalArgumentException iae) {
        }

        // Test Handler & Middlewares
        final var handlerName = TestEventHandler.class.getSimpleName();
        final var middlewareName = TestEventHandler.class.getSimpleName();

        var handler = new TestEventHandler();
        var middleware = new TestEventMiddleware();

        var triggerProcessor1 = new TriggerProcessor();
        triggerProcessor1.setEventMiddlewares(List.of(middleware, new EventMiddleware.HandlerAdapter(handler)));

        var request1 = TriggerRequest.newBuilder()
                .setTopic(TopicTriggerContext.newBuilder().setTopic("orders"))
                .build();

        var res1 = triggerProcessor1.processTopicTrigger(request1);

        assertNotNull(res1);
        assertFalse(res1.hasHttp());
        assertTrue(res1.hasTopic());

        assertFalse(res1.getTopic().getSuccess());
        assertEquals(handlerName, res1.getData().toStringUtf8());

        assertTrue(middleware.invokedTime > 0);
        assertTrue(handler.invokedTime > 0);
        assertTrue(middleware.invokedTime < handler.invokedTime);

        // Test Null context result
        EventMiddleware nullMiddleware = new NullEventMiddleware();

        var triggerProcessor2 = new TriggerProcessor();
        triggerProcessor2.setEventMiddlewares(List.of(middleware, nullMiddleware));

        var request2 = TriggerRequest.newBuilder()
                .setTopic(TopicTriggerContext.newBuilder().setTopic("orders"))
                .build();

        var res2 = triggerProcessor2.processTopicTrigger(request2);
        assertNull(res2);

        // Test Error thrown
        EventMiddleware errorMiddleware = new ErrorEventMiddleware();

        var triggerProcessor3 = new TriggerProcessor();
        triggerProcessor3.setEventMiddlewares(List.of(middleware, errorMiddleware));

        var request3 = TriggerRequest.newBuilder()
                .setTopic(TopicTriggerContext.newBuilder().setTopic("orders"))
                .build();

        var res3 = triggerProcessor3.processTopicTrigger(request3);
        assertNull(res3);

        // Test error thrown handler
        EventMiddleware errorHandlerMiddleware = new EventMiddleware.HandlerAdapter(new ErrorEventHandler());

        var triggerProcessor4 = new TriggerProcessor();
        triggerProcessor4.setEventMiddlewares(List.of(middleware, errorHandlerMiddleware));

        var request4 = TriggerRequest.newBuilder()
                .setTopic(TopicTriggerContext.newBuilder().setTopic("orders"))
                .build();

        var res4 = triggerProcessor4.processTopicTrigger(request4);
        assertNull(res4);
    }

    @Test
    public void test_process() {
        var triggerProcessor = new TriggerProcessor();

        // Test null parameter
        try {
            triggerProcessor.process(null);
            fail();
        } catch (IllegalArgumentException iae) {
        }

        // Test no trigger type
        var request1 = TriggerRequest.newBuilder().build();
        try {
            triggerProcessor.process(request1);
            fail();
        } catch (UnsupportedOperationException uoe) {
            uoe.printStackTrace();
        }

        // Test no Http middleware
        var request2 = TriggerRequest.newBuilder()
                .setHttp(HttpTriggerContext.newBuilder().setMethod("DELETE").setPath("/customer"))
                .build();
        try {
            triggerProcessor.process(request2);
            fail();
        } catch (IllegalStateException iae) {
            assertEquals("No HTTP handler or middlewares have been registered", iae.getMessage());
        }

        // Test no Event middleware
        var request3 = TriggerRequest.newBuilder()
                .setTopic(TopicTriggerContext.newBuilder().setTopic("orders"))
                .build();
        try {
            triggerProcessor.process(request3);
            fail();
        } catch (IllegalStateException iae) {
            assertEquals("No Event handler or middlewares have been registered", iae.getMessage());
        }

        // Test Http Middleware
        var httpMiddleware = new TestHttpMiddleware();

        triggerProcessor.setHttpMiddlewares(List.of(httpMiddleware));

        var request4 = TriggerRequest.newBuilder()
                .setHttp(HttpTriggerContext.newBuilder().setMethod("GET"))
                .build();

        var res4 = triggerProcessor.process(request4);

        assertNotNull(res4);
        assertTrue(res4.hasHttp());
        assertFalse(res4.hasTopic());

        assertEquals(httpMiddleware.getClass().getSimpleName(), res4.getData().toStringUtf8());

        assertEquals(1, res4.getHttp().getHeadersMap().size());
        assertEquals(200, res4.getHttp().getStatus());
        assertTrue(httpMiddleware.invokedTime > 0);

        // Test Event Middleware
        var eventMiddleware = new TestEventMiddleware();

        triggerProcessor.setEventMiddlewares(List.of(eventMiddleware));

        var request5 = TriggerRequest.newBuilder()
                .setTopic(TopicTriggerContext.newBuilder().setTopic("orders"))
                .build();

        var res5 = triggerProcessor.process(request5);

        assertNotNull(res5);
        assertFalse(res5.hasHttp());
        assertTrue(res5.hasTopic());

        assertEquals(eventMiddleware.getClass().getSimpleName(), res5.getData().toStringUtf8());

        assertEquals(false, res5.getTopic().getSuccess());
        assertTrue(eventMiddleware.invokedTime > 0);
    }

    // Package Private Methods ------------------------------------------------

    static NitricException createNitricException() {
        var sre = new StatusRuntimeException(Status.NOT_FOUND);

        var es1 = ErrorScope.newBuilder()
                .setService("DocumentService")
                .setPlugin("DynamoPlugin")
                .putArgs("Collection", "orders")
                .putArgs("Id", "1839-0328-5621")
                .build();

        var ed1 = ErrorDetails.newBuilder()
                .setMessage("document not found")
                .setCause("order item not found")
                .setScope(es1)
                .build();

        return new NotFoundException(NitricException.Code.NOT_FOUND, "", sre, ed1);
    }

    // Inner Classes ----------------------------------------------------------

    public static class TestEventHandler implements EventHandler {
        long invokedTime;

        @Override
        public EventContext handle(EventContext context) {
            invokedTime = System.currentTimeMillis();
            try {
                Thread.sleep(1);
            } catch (InterruptedException iae) {
            }
            context.getResponse().data(getClass().getSimpleName());
            return context;
        }
    }

    public static class TestEventMiddleware extends EventMiddleware {
        long invokedTime;

        @Override
        public EventContext handle(EventContext context, EventMiddleware next) {
            invokedTime = System.currentTimeMillis();
            try {
                Thread.sleep(1);
            } catch (InterruptedException iae) {
            }
            context.getResponse().data(getClass().getSimpleName());
            return next.handle(context, next.getNext());
        }
    }

    public static class NullEventMiddleware extends EventMiddleware {
        public EventContext handle(EventContext context, EventMiddleware next) {
            return null;
        }
    }

    public static class ErrorEventHandler implements EventHandler {
        public EventContext handle(EventContext context) {
            throw createNitricException();
        }
    }

    public static class ErrorEventMiddleware extends EventMiddleware {
        public EventContext handle(EventContext context, EventMiddleware next) {
            throw createNitricException();
        }
    }

    public static class TestHttpHandler implements HttpHandler {
        long invokedTime;

        public HttpContext handle(HttpContext context) {
            invokedTime = System.currentTimeMillis();
            try {
                Thread.sleep(1);
            } catch (InterruptedException iae) {
            }
            context.getResponse()
                    .status(404)
                    .addHeader(getClass().getSimpleName(), toString())
                    .data(getClass().getSimpleName());
            return context;
        }
    }

    public static class TestHttpMiddleware extends HttpMiddleware {
        long invokedTime;

        public HttpContext handle(HttpContext context, HttpMiddleware next) {
            invokedTime = System.currentTimeMillis();
            try {
                Thread.sleep(1);
            } catch (InterruptedException iae) {
            }
            context.getResponse()
                    .addHeader(getClass().getSimpleName(), toString())
                    .data(getClass().getSimpleName());

            return next.handle(context, next.getNext());
        }
    }

    public static class NullHttpMiddleware extends HttpMiddleware {
        public HttpContext handle(HttpContext context, HttpMiddleware next) {
            return null;
        }
    }

    public static class ErrorHttpHandler implements HttpHandler {
        public HttpContext handle(HttpContext context) {
            throw createNitricException();
        }
    }

    public static class ErrorHttpMiddleware extends HttpMiddleware {
        public HttpContext handle(HttpContext context, HttpMiddleware next) {
            throw createNitricException();
        }
    }

}
