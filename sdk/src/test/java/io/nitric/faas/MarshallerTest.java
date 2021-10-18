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

import io.nitric.proto.faas.v1.QueryValue;
import org.junit.jupiter.api.Test;

import io.nitric.proto.faas.v1.HeaderValue;
import io.nitric.proto.faas.v1.HttpTriggerContext;
import io.nitric.proto.faas.v1.TopicTriggerContext;
import io.nitric.proto.faas.v1.TriggerRequest;

/**
 * Provides Marshaller Unit Test.
 */
public class MarshallerTest {

    final static String LONG_DATA = "Another two syncons at the Robertstown sub-station should be switched on in the coming week, and while...";

    @Test
    public void test_toHttpContext() {
        var triggerContext = HttpTriggerContext.newBuilder()
                .setMethod("GET")
                .setPath("/test/");

        var queryValue = QueryValue.newBuilder().addValue("test1").addValue("test2").build();
        triggerContext.putQueryParams("id", queryValue);
        triggerContext.putHeaders("x-nitric-test", HeaderValue.newBuilder().addValue("test").build());

        var triggerRequest = TriggerRequest.newBuilder()
                .setData(ByteString.copyFrom("Hello World", StandardCharsets.UTF_8))
                .setMimeType("text/plain")
                .setHttp(triggerContext.build())
                .build();

        var ctx1 = Marshaller.toHttpContext(triggerRequest);
        assertNotNull(ctx1);

        var req1 = ctx1.getRequest();
        assertEquals("GET", req1.getMethod());
        assertEquals("/test/", req1.getPath());
        assertEquals("text/plain", req1.getMimeType());
        assertEquals("Hello World", req1.getDataAsText());
        assertEquals("test", req1.getHeader("x-nitric-test"));
        assertEquals("[test]", req1.getHeaders().get("x-nitric-test").toString());
        assertEquals("test1", req1.getQueryParam("id"));
        assertEquals("[test1, test2]", req1.getQueryParams().get("id").toString());

        assertEquals("Request[method=GET, path=/test/, headers={x-nitric-test=[test]}, queryParams={id=[test1, test2]}, mimeType=text/plain, data=Hello World, pathParams={}, extras={}]",
                     req1.toString());

        // Test No Data
        var triggerContext2 = HttpTriggerContext.newBuilder();

        var triggerRequest2 = TriggerRequest.newBuilder()
                .setHttp(triggerContext2.build())
                .build();

        var ctx2 = Marshaller.toHttpContext(triggerRequest2);
        assertNotNull(ctx2);

        var req2 = ctx2.getRequest();
        assertNotNull(req2);
        assertEquals("", req2.getMethod());
        assertEquals("", req2.getPath());
        assertEquals("", req2.getMimeType());
        assertEquals("", req2.getDataAsText());
        assertTrue(req2.getHeaders().isEmpty());
        assertTrue(req2.getQueryParams().isEmpty());

        // Invalid Trigger Type
        var triggerContext3 = TopicTriggerContext.newBuilder()
                .setTopic("topic3");

        var triggerRequest3 = TriggerRequest.newBuilder()
                .setTopic(triggerContext3.build())
                .build();

        try {
            Marshaller.toHttpContext(triggerRequest3);
            fail();
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void test_toHttpTriggerResponse() {
        var triggerContext1 = HttpTriggerContext
                .newBuilder()
                .setMethod("GET")
                .setPath("/test/");

        var queryValue = QueryValue.newBuilder().addValue("test").build();
        triggerContext1.putQueryParams("id", queryValue);
        triggerContext1.putHeaders("x-nitric-test", HeaderValue.newBuilder().addValue("test").build());

        var triggerRequest1 = TriggerRequest.newBuilder()
                .setData(ByteString.copyFrom(LONG_DATA, StandardCharsets.UTF_8))
                .setMimeType("text/plain")
                .setHttp(triggerContext1.build())
                .build();

        var ctx1 = Marshaller.toHttpContext(triggerRequest1);
        assertNotNull(ctx1);

        ctx1.getResponse()
            .status(404)
            .addHeader("header", "value1")
            .addHeader("header", "value2")
            .text(LONG_DATA);

        var resp1 = Marshaller.toHttpTriggerResponse(ctx1.getResponse());
        assertNotNull(resp1);

        assertEquals(LONG_DATA, resp1.getData().toStringUtf8());

        assertTrue(resp1.hasHttp());
        assertFalse(resp1.hasTopic());

        var htr1 = resp1.getHttp();
        assertEquals(404, htr1.getStatus());
        assertEquals(1, htr1.getHeadersCount());
        var hv = htr1.getHeadersMap().get("header");
        assertNotNull(hv);
        assertEquals(2, hv.getValueCount());
        assertEquals("value1", hv.getValue(0));
        assertEquals("value2", hv.getValue(1));

        // Response without data
        var triggerContext2 = HttpTriggerContext
                .newBuilder();

        var triggerRequest2 = TriggerRequest.newBuilder()
                .setHttp(triggerContext2.build())
                .build();

        var ctx2 = Marshaller.toHttpContext(triggerRequest2);
        assertNotNull(ctx2);

        var resp2 = Marshaller.toHttpTriggerResponse(ctx2.getResponse());
        assertNotNull(resp2);

        assertTrue(resp2.getData().isEmpty());

        assertTrue(resp1.hasHttp());
        assertFalse(resp1.hasTopic());

        var htr2 = resp2.getHttp();
        assertEquals(200, htr2.getStatus());
        assertEquals(0, htr2.getHeadersCount());
    }

    @Test void test_toTopicContext() {
        var triggerContext = TopicTriggerContext.newBuilder()
                .setTopic("topic");

        var triggerRequest = TriggerRequest.newBuilder()
                .setData(ByteString.copyFrom("Hello World", StandardCharsets.UTF_8))
                .setMimeType("text/plain")
                .setTopic(triggerContext.build())
                .build();

        var ctx = Marshaller.toEventContext(triggerRequest);
        assertNotNull(ctx);

        var request = ctx.getRequest();
        assertNotNull(request);
        assertEquals("topic", request.getTopic());
        assertEquals("text/plain", request.getMimeType());
        assertEquals("Hello World", request.getDataAsText());
        assertEquals("Hello World", new String(request.getData(), StandardCharsets.UTF_8));

        // Test No Data
        var triggerContext2 = TopicTriggerContext.newBuilder();

        var triggerRequest2 = TriggerRequest.newBuilder()
                .setTopic(triggerContext2.build())
                .build();

        var ctx2 = Marshaller.toEventContext(triggerRequest2);
        assertNotNull(ctx2);

        var request2 = ctx2.getRequest();
        assertNotNull(request2);
        assertEquals("", request2.getTopic());
        assertEquals("", request2.getMimeType());
        assertEquals("", request2.getDataAsText());
        assertNotNull(request2.getData());
        assertEquals(0, request2.getData().length);

        // Invalid Trigger Type
        var triggerContext3 = HttpTriggerContext.newBuilder()
                .setMethod("PUT");

        var triggerRequest3 = TriggerRequest.newBuilder()
                .setHttp(triggerContext3.build())
                .build();

        try {
            Marshaller.toEventContext(triggerRequest3);
            fail();
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void test_toTopicTriggerResponse() {
        var triggerContext1 = TopicTriggerContext.newBuilder()
                .setTopic("topic");

        var triggerRequest1 = TriggerRequest.newBuilder()
                .setData(ByteString.copyFrom("Hello World", StandardCharsets.UTF_8))
                .setMimeType("text/plain")
                .setTopic(triggerContext1.build())
                .build();

        var ctx1 = Marshaller.toEventContext(triggerRequest1);

        ctx1.getResponse()
                .success(false)
                .data(LONG_DATA);

        var resp1 = Marshaller.toTopicTriggerResponse(ctx1.getResponse());
        assertNotNull(resp1);

        assertEquals(LONG_DATA, resp1.getData().toStringUtf8());

        assertFalse(resp1.hasHttp());
        assertTrue(resp1.hasTopic());

        assertNotNull(resp1.getTopic());
        assertEquals(false, resp1.getTopic().getSuccess());

        // Defaults (No Data)
        var ctx2 = Marshaller.toEventContext(triggerRequest1);

        var resp2 = Marshaller.toTopicTriggerResponse(ctx2.getResponse());
        assertNotNull(resp2);

        assertTrue(resp2.getData().isEmpty());

        assertFalse(resp2.hasHttp());
        assertTrue(resp2.hasTopic());

        assertNotNull(resp2.getTopic());
        assertEquals(true, resp2.getTopic().getSuccess());
    }

}
