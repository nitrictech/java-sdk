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

package io.nitric.faas.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.google.api.Http;

import org.junit.jupiter.api.Test;

/**
 * Provides a HttpContext test case.
 */
public class HttpContextTest {

    final byte[] data = "data".getBytes(StandardCharsets.UTF_8);
    final Map<String, String> headers = Map.of("header", "value");
    final Map<String, String> params = Map.of("param", "value");

    @Test
    public void test_request() {
        var request = new HttpContext.Request(null, null, null, null, null, null);
        assertNull(request.getMethod());
        assertNull(request.getPath());
        assertNull(request.getMimeType());
        assertNull(request.getData());
        assertNull(request.getDataAsText());
        assertTrue(request.getHeaders().isEmpty());
        assertTrue(request.getQueryParams().isEmpty());

        var request2 = new HttpContext.Request("method", "path", headers, params, "mimeType", data);
        assertEquals("method", request2.getMethod());
        assertEquals("path", request2.getPath());
        assertEquals("mimeType", request2.getMimeType());
        assertEquals("data", new String(request2.getData()));
        assertEquals("data", request2.getDataAsText());
        assertEquals("{header=value}", request2.getHeaders().toString());
        assertEquals("{param=value}", request2.getQueryParams().toString());

        assertEquals("Request[method=method, path=path, headers={header=value}, queryParams{param=value}, mimeType=mimeType, data=data]",
                     request2.toString());
    }

    @Test
    public void test_response() {
        var response = new HttpContext.Response();
        assertEquals(200, response.getStatus());
        assertTrue(response.getHeaders().isEmpty());
        assertNull(response.getData());
        assertNull(response.getDataAsText());

        var response2 = new HttpContext.Response()
            .status(404)
            .headers(headers)
            .data(data);

        assertEquals(404, response2.getStatus());
        assertEquals(headers, response2.getHeaders());
        assertEquals("data", new String(response2.getData()));
        assertEquals("data", response2.getDataAsText());

        var response3 = new HttpContext.Response(response2);

        assertEquals(404, response3.getStatus());
        assertEquals(headers, response3.getHeaders());
        assertEquals("data", new String(response3.getData()));
        assertEquals("data", response3.getDataAsText());

        assertEquals("Response[status=404, headers={header=value}, data=data]",
                     response3.toString());
    }

    @Test
    public void test_context() {
        var request = new HttpContext.Request("method", "path", headers, params, "mimeType", data);
        var response = new HttpContext.Response().status(404).headers(headers).data(data);

        var ctx = new HttpContext(request, response);
        assertNotNull(ctx.getRequest());
        assertNotNull(ctx.getResponse());

        var request2 = ctx.getRequest();
        assertNotNull(request2);
        assertEquals("method", request2.getMethod());
        assertEquals("path", request2.getPath());
        assertEquals("mimeType", request2.getMimeType());
        assertEquals("data", new String(request2.getData()));
        assertEquals("data", request2.getDataAsText());
        assertEquals("{header=value}", request2.getHeaders().toString());
        assertEquals("{param=value}", request2.getQueryParams().toString());

        var response2 = ctx.getResponse();
        assertNotNull(response2);
        assertEquals(404, response2.getStatus());
        assertEquals(headers, response2.getHeaders());
        assertEquals("data", new String(response2.getData()));
        assertEquals("data", response2.getDataAsText());

        assertEquals("HttpContext[request=Request[method=method, path=path, headers={header=value}, queryParams{param=value}, mimeType=mimeType, data=data], response=Response[status=404, headers={header=value}, data=data]]",
                     ctx.toString());

        var ctx2 = new HttpContext(ctx);
        assertEquals("HttpContext[request=Request[method=method, path=path, headers={header=value}, queryParams{param=value}, mimeType=mimeType, data=data], response=Response[status=404, headers={header=value}, data=data]]",
                     ctx2.toString());
    }

    @Test
    public void test_builder() {
        var ctx = HttpContext.newBuilder()
            .method("method")
            .path("path")
            .headers(headers)
            .queryParams(params)
            .mimeType("mimeType")
            .data(data)
            .build();

        var request = ctx.getRequest();
        assertEquals("method", request.getMethod());
        assertEquals("path", request.getPath());
        assertEquals("mimeType", request.getMimeType());
        assertEquals("data", new String(request.getData()));
        assertEquals("data", request.getDataAsText());
        assertEquals("{header=value}", request.getHeaders().toString());
        assertEquals("{param=value}", request.getQueryParams().toString());

        var response = ctx.getResponse();
        assertEquals(200, response.getStatus());
        assertTrue(response.getHeaders().isEmpty());
        assertNull(response.getData());
        assertNull(response.getDataAsText());
    }

}
