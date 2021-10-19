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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Provides an EventContext test case.
 */
public class EventContextTest {

    final byte[] data = "data".getBytes(StandardCharsets.UTF_8);
    final byte[] longData = "A third-party OAuth application (JetBrains IDE Integration) with gist, read:org, repo"
            .getBytes(StandardCharsets.UTF_8);
    final Map<String, Object> extras = Map.of("value", "{ \"type\": \"json\" }");

    @Test
    public void test_request() {
        var request = new EventContext.Request(null, null, null, null);
        assertEquals("", request.getTopic());
        assertEquals("", request.getMimeType());
        assertNull(request.getData());
        assertEquals("", request.getDataAsText());
        assertTrue(request.getExtras().isEmpty());

        request = new EventContext.Request("topic", "mimeType", longData, extras);
        assertEquals("topic", request.getTopic());
        assertEquals("mimeType", request.getMimeType());
        assertEquals(new String(longData), new String(request.getData()));
        assertEquals("A third-party OAuth application (JetBrains IDE Integration) with gist, read:org, repo", request.getDataAsText());

        assertEquals("Request[topic=topic, mimeType=mimeType, data=A third-party OAuth application (JetBrai..., extras={value={ \"type\": \"json\" }}]",
                     request.toString());
    }

    @Test
    public void test_response() {
        var response = new EventContext.Response();
        assertTrue(response.isSuccess());
        assertNull(response.getData());
        assertNull(response.getDataAsText());

        response.success(true).data(longData);

        assertTrue(response.isSuccess());
        assertEquals(new String(longData), response.getDataAsText());
        assertEquals(new String(longData), new String(response.getData()));

        var response2 = new EventContext.Response(response);
        assertTrue(response2.isSuccess());
        assertEquals(new String(longData), response2.getDataAsText());
        assertEquals(new String(longData), new String(response2.getData()));

        assertEquals("Response[success=true, data=A third-party OAuth application (JetBrai...]", response2.toString());

        var response3 = new EventContext.Response(response).success(false);
        assertFalse(response3.isSuccess());

        var response4 = new EventContext.Response(response).text("Value: %s", 13);
        assertEquals("Value: 13", response4.getDataAsText());
    }

    @Test
    public void test_context() {
        var request = new EventContext.Request("topic", "mimeType", data, extras);
        var response = new EventContext.Response().success(true).text("data");

        var ctx = new EventContext(request, response);
        assertNotNull(ctx.getRequest());
        assertNotNull(ctx.getResponse());
        assertEquals("EventContext[request=Request[topic=topic, mimeType=mimeType, data=data, extras={value={ \"type\": \"json\" }}], response=Response[success=true, data=data]]",
                     ctx.toString());

        var ctx2 = new EventContext(ctx);
        assertNotNull(ctx2.getRequest());
        assertNotNull(ctx2.getResponse());
        assertEquals("EventContext[request=Request[topic=topic, mimeType=mimeType, data=data, extras={value={ \"type\": \"json\" }}], response=Response[success=true, data=data]]",
                     ctx2.toString());
    }

    @Test
    public void test_builder() {
        var ctx = EventContext.newBuilder()
                .text("data")
                .mimeType("mimeType")
                .topic("topic")
                .addExtras("value", "{ \"type\": \"json\" }")
                .build();

        assertNotNull(ctx);
        assertNotNull(ctx.getRequest());
        assertNotNull(ctx.getResponse());

        var request = ctx.getRequest();
        assertEquals("topic", request.getTopic());
        assertEquals("mimeType", request.getMimeType());
        assertEquals(new String("data"), new String(request.getData()));
        assertEquals("data", request.getDataAsText());
        assertEquals(1, request.getExtras().size());
        assertEquals("{value={ \"type\": \"json\" }}", request.getExtras().toString());

        var response = ctx.getResponse();
        assertTrue(response.isSuccess());
        assertNull(response.getData());
        assertNull(response.getDataAsText());

        var ctx2 = EventContext.newBuilder()
                .data(longData)
                .build();
        assertEquals(new String(longData), ctx2.getRequest().getDataAsText());
    }

}
