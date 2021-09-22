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

import com.google.protobuf.ByteString;

import org.junit.jupiter.api.Test;

import io.nitric.proto.faas.v1.HeaderValue;
import io.nitric.proto.faas.v1.HttpTriggerContext;
import io.nitric.proto.faas.v1.TopicTriggerContext;
import io.nitric.proto.faas.v1.TriggerRequest;

/**
 * Provides Mashaller Unit Test.
 */
public class MarshallerTest {

    final static String LONG_DATA = "Another two syncons at the Robertstown sub-station should be switched on in the coming week, and while...";

    @Test
    public void test_from_grpc_http() {
        var triggerContext = HttpTriggerContext.newBuilder()
                .setMethod("GET")
                .setPath("/test/");

        triggerContext.putHeaders("x-nitric-test", HeaderValue.newBuilder().addValue("test").build());
        triggerContext.putQueryParams("id", "test");

        var triggerRequest = TriggerRequest.newBuilder()
                .setData(ByteString.copyFrom("Hello World", StandardCharsets.UTF_8))
                .setMimeType("text/plain")
                .setHttp(triggerContext.build())
                .build();

        var ctx = Marshaller.toHttpContext(triggerRequest);
        assertNotNull(ctx);

        var req = ctx.getRequest();
        assertEquals("Hello World", req.getDataAsText());
        assertEquals("text/plain", req.getMimeType());
        assertEquals("GET", req.getMethod());
        assertEquals("/test/", req.getPath());
        assertEquals("test", req.getHeaders().get("x-nitric-test").get(0));
        assertEquals(req.getQueryParams().get("id"), "test");

        assertEquals("Request[method=GET, path=/test/, headers={x-nitric-test=[test]}, queryParams{id=test}, mimeType=text/plain, data=Hello World]",
                     req.toString());
     }

    @Test
    public void test_http_trigger_response() {
        var triggerContext = HttpTriggerContext
                .newBuilder()
                .setMethod("GET")
                .setPath("/test/");

        triggerContext.putHeaders("x-nitric-test", HeaderValue.newBuilder().addValue("test").build());
        triggerContext.putQueryParams("id", "test");

        var triggerRequest = TriggerRequest.newBuilder()
                .setData(ByteString.copyFrom(LONG_DATA, StandardCharsets.UTF_8))
                .setMimeType("text/plain")
                .setHttp(triggerContext.build())
                .build();

        var ctx = Marshaller.toHttpContext(triggerRequest);
        assertNotNull(ctx);

        var req = ctx.getRequest();

        assertEquals(LONG_DATA, req.getDataAsText());

        // Response without data
        var response = Marshaller.toHttpTriggerResponse(ctx.getResponse());
        assertNotNull(response);
        assertEquals(0, response.getData().toByteArray().length);
        assertEquals(200, response.getHttp().getStatus());
        assertTrue(response.hasHttp());
        assertFalse(response.hasTopic());

        // response with data
        ctx.getResponse().status(500);
        ctx.getResponse().data(LONG_DATA);

        var response2 = Marshaller.toHttpTriggerResponse(ctx.getResponse());
        assertNotNull(response2);
        assertEquals(LONG_DATA, response2.getData().toStringUtf8());
        assertEquals(500, response2.getHttp().getStatus());
    }

    @Test
    public void test_from_grpc_topic() {
        var triggerContext = TopicTriggerContext.newBuilder()
                .setTopic("topic");

        var triggerRequest = TriggerRequest.newBuilder()
                .setData(ByteString.copyFrom("Hello World", StandardCharsets.UTF_8))
                .setMimeType("text/plain")
                .setTopic(triggerContext.build())
                .build();

        var ctx = Marshaller.toEventContext(triggerRequest);
        assertNotNull(ctx);

        var response = Marshaller.toTopicTriggerResponse(ctx.getResponse());

        // assertEquals("Hello World", response.getData().toStringUtf8());
        assertTrue(response.hasTopic());
        assertFalse(response.hasHttp());
        // assertTrue(response.getTopic().getSuccess());
    }

    @Test
    public void test_no_data() {
        var triggerContext = TopicTriggerContext
                .newBuilder()
                .setTopic("test");

        var triggerRequest = TriggerRequest
                .newBuilder()
                .setTopic(triggerContext.build())
                .build();

        // var trigger = FunctionTrigger.buildTrigger(triggerRequest);

        // assertNotNull(trigger.getData());
        // assertEquals(0, trigger.getData().length);
        // assertEquals("", trigger.getDataAsText());

        // var response = trigger.buildResponse((byte[]) null);
        // assertNotNull(response);
        // assertNull(response.getData());
    }

    @Test
    public void http_response_context() {
        // var headers = Map.of("x-nitric-test", "test");

        // var ctx = new HttpResponseContext()
        //         .setStatus(200)
        //         .addHeader("x-nitric-test", "test");
        // assertEquals(200, ctx.getStatus());
        // assertEquals(headers.toString(), ctx.getHeaders().toString());

        // ctx = new HttpResponseContext()
        //         .setStatus(200)
        //         .setHeaders(headers);
        // assertEquals(200, ctx.getStatus());
        // assertEquals(headers.toString(), ctx.getHeaders().toString());

        // assertEquals("HttpResponseContext[status=200, headers={x-nitric-test=test}]", ctx.toString());
    }

    @Test
    public void http_response_to_grpc() {
        // var ctx = new HttpResponseContext()
        //         .setStatus(200)
        //         .addHeader("x-nitric-test", "test");

        // var response = new Response("Hello World".getBytes(StandardCharsets.UTF_8), ctx);

        // var grpcResponse = response.toGrpcTriggerResponse();

        // assertEquals(grpcResponse.getData().toString(StandardCharsets.UTF_8), "Hello World");
        // assertTrue(grpcResponse.hasHttp());
        // assertEquals(200, grpcResponse.getHttp().getStatus());
        // assertEquals(Map.of("x-nitric-test", "test"), grpcResponse.getHttp().getHeadersMap());
    }

    @Test
    public void topic_response_context() {
        // var ctx = new TopicResponseContext().setSuccess(false);
        // assertFalse(ctx.isSuccess());

        // ctx.setSuccess(true);
        // assertTrue(ctx.isSuccess());

        // assertEquals("TopicResponseContext[success=true]", ctx.toString());
    }

    @Test
    public void topic_response_data() {
        // var ctx = new TopicResponseContext();

        // var response = new Response(null, ctx);
        // assertNull(response.getData());
        // assertNull(response.getDataAsText());

        // response = new Response("data".getBytes(StandardCharsets.UTF_8), ctx);
        // assertNotNull(response.getData());
        // assertEquals("data", response.getDataAsText());

        // response = new Response(null, ctx);
        // response.setData("data".getBytes(StandardCharsets.UTF_8));
        // assertNotNull(response.getData());
        // assertEquals("data", response.getDataAsText());

        // response = new Response(null, ctx);
        // response.setDataAsText("data");
        // assertNotNull(response.getData());
        // assertEquals("data", response.getDataAsText());
    }

    @Test
    public void topic_response_to_grpc() {
        // var ctx = new TopicResponseContext()
        //         .setSuccess(false);

        // var response = new Response("Hello World".getBytes(StandardCharsets.UTF_8), ctx);

        // var grpcResponse = response.toGrpcTriggerResponse();

        // assertEquals(grpcResponse.getData().toString(StandardCharsets.UTF_8), "Hello World");
        // assertTrue(grpcResponse.hasTopic());
        // assertFalse(grpcResponse.getTopic().getSuccess());
    }

    @Test
    public void topic_context_toString() {
        // var trc = new TopicResponseContext();
        // trc.setSuccess(true);
        // var resp = new Response("test".getBytes(StandardCharsets.UTF_8), trc);

        // assertEquals(resp.toString(), "Response[context=TopicResponseContext[success=true], data=test]");
    }

}
