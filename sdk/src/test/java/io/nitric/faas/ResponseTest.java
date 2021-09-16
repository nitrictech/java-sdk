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
import java.util.Map;

import org.junit.jupiter.api.Test;

public class ResponseTest {

    @Test
    public void http_response_context() {
        var headers = Map.of("x-nitric-test", "test");

        var ctx = new HttpResponseContext()
                .setStatus(200)
                .addHeader("x-nitric-test", "test");
        assertEquals(200, ctx.getStatus());
        assertEquals(headers.toString(), ctx.getHeaders().toString());

        ctx = new HttpResponseContext()
                .setStatus(200)
                .setHeaders(headers);
        assertEquals(200, ctx.getStatus());
        assertEquals(headers.toString(), ctx.getHeaders().toString());

        assertEquals("HttpResponseContext[status=200, headers={x-nitric-test=test}]", ctx.toString());
    }

    @Test
    public void http_response_to_grpc() {
        var ctx = new HttpResponseContext()
                .setStatus(200)
                .addHeader("x-nitric-test", "test");

        var response = new Response("Hello World".getBytes(StandardCharsets.UTF_8), ctx);

        var grpcResponse = response.toGrpcTriggerResponse();

        assertEquals(grpcResponse.getData().toString(StandardCharsets.UTF_8), "Hello World");
        assertTrue(grpcResponse.hasHttp());
        assertEquals(200, grpcResponse.getHttp().getStatus());
        assertEquals(Map.of("x-nitric-test", "test"), grpcResponse.getHttp().getHeadersMap());
    }

    @Test
    public void topic_response_context() {
        var ctx = new TopicResponseContext().setSuccess(false);
        assertFalse(ctx.isSuccess());

        ctx.setSuccess(true);
        assertTrue(ctx.isSuccess());

        assertEquals("TopicResponseContext[success=true]", ctx.toString());
    }

    @Test
    public void topic_response_data() {
        var ctx = new TopicResponseContext();

        var response = new Response(null, ctx);
        assertNull(response.getData());
        assertNull(response.getDataAsText());

        response = new Response("data".getBytes(StandardCharsets.UTF_8), ctx);
        assertNotNull(response.getData());
        assertEquals("data", response.getDataAsText());

        response = new Response(null, ctx);
        response.setData("data".getBytes(StandardCharsets.UTF_8));
        assertNotNull(response.getData());
        assertEquals("data", response.getDataAsText());

        response = new Response(null, ctx);
        response.setDataAsText("data");
        assertNotNull(response.getData());
        assertEquals("data", response.getDataAsText());
    }

    @Test
    public void topic_response_to_grpc() {
        var ctx = new TopicResponseContext()
                .setSuccess(false);

        var response = new Response("Hello World".getBytes(StandardCharsets.UTF_8), ctx);

        var grpcResponse = response.toGrpcTriggerResponse();

        assertEquals(grpcResponse.getData().toString(StandardCharsets.UTF_8), "Hello World");
        assertTrue(grpcResponse.hasTopic());
        assertFalse(grpcResponse.getTopic().getSuccess());
    }

    @Test public void topic_context_toString() {
        var trc = new TopicResponseContext();
        trc.setSuccess(true);
        var resp = new Response("test".getBytes(StandardCharsets.UTF_8), trc);

        assertEquals(resp.toString(), "Response[context=TopicResponseContext[success=true], data=test]");
    }
}
