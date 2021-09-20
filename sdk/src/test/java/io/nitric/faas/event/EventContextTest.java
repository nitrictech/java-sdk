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

import org.junit.jupiter.api.Test;

/**
 * Provides an EventContext test case.
 */
public class EventContextTest {

    final byte[] data = "data".getBytes(StandardCharsets.UTF_8);

    @Test
    public void test_request() {
        var request = new EventContext.Request(null, null, null);
        assertNull(request.getTopic());
        assertNull(request.getMimeType());
        assertNull(request.getData());
        assertNull(request.getDataAsText());

        request = new EventContext.Request("topic", "mimeType", data);
        assertEquals("topic", request.getTopic());
        assertEquals("mimeType", request.getMimeType());
        assertEquals(new String(data), new String(request.getData()));
        assertEquals("data", request.getDataAsText());

        assertEquals("Request[topic=topic, mimeType=mimeType, data=data]", request.toString());
    }

    @Test
    public void test_response() {
        var response = new EventContext.Response();
        assertFalse(response.isSuccess());
        assertNull(response.getData());
        assertNull(response.getDataAsText());

        response.success(true).data("data");

        assertTrue(response.isSuccess());
        assertEquals("data", response.getDataAsText());
        assertEquals(new String("data"), new String(response.getData()));

        var response2 = new EventContext.Response(response);
        assertTrue(response2.isSuccess());
        assertEquals("data", response2.getDataAsText());
        assertEquals(new String("data"), new String(response2.getData()));

        assertEquals("Response[success=true, data=data]", response2.toString());
    }

    @Test
    public void test_context() {
        var request = new EventContext.Request("topic", "mimeType", data);
        var response = new EventContext.Response().success(true).data("data");

        var ctx = new EventContext(request, response);
        assertNotNull(ctx.getRequest());
        assertNotNull(ctx.getResponse());
        assertEquals("EventContext[request=Request[topic=topic, mimeType=mimeType, data=data], response=Response[success=true, data=data]]",
                     ctx.toString());

        var ctx2 = new EventContext(ctx);
        assertNotNull(ctx2.getRequest());
        assertNotNull(ctx2.getResponse());
        assertEquals("EventContext[request=Request[topic=topic, mimeType=mimeType, data=data], response=Response[success=true, data=data]]",
                     ctx2.toString());
    }

    @Test
    public void test_builder() {
        var ctx = EventContext.newBuilder()
            .data("data")
            .mimeType("mimeType")
            .topic("topic")
            .build();

        assertNotNull(ctx);
        assertNotNull(ctx.getRequest());
        assertNotNull(ctx.getResponse());

        var request = ctx.getRequest();
        assertEquals("topic", request.getTopic());
        assertEquals("mimeType", request.getMimeType());
        assertEquals(new String("data"), new String(request.getData()));
        assertEquals("data", request.getDataAsText());

        var response = ctx.getResponse();
        assertFalse(response.isSuccess());
        assertNull(response.getData());
        assertNull(response.getDataAsText());
    }

}
