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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Provides a HttpContext test case.
 */
public class HttpContextTest {

    final byte[] data = "data".getBytes(StandardCharsets.UTF_8);
    final byte[] longData = "A third-party OAuth application (JetBrains IDE Integration) with gist, read:org, repo"
            .getBytes(StandardCharsets.UTF_8);

    final Map<String, List<String>> headers = Map.of("header", Arrays.asList("value1", "value2"));
    final Map<String, List<String>> paramsList = Map.of("param", Arrays.asList("value"));
    final Map<String, String> paramsString = Map.of("param", "value");
    final Map<String, String> pathParams = Map.of("id", "123");
    final Map<String, Object> extras = Map.of("value", "{ \"type\": \"json\" }");

    @Test
    public void test_request() {
        var request = new HttpContext.Request(null, null, null, null, null, null, null, null);
        assertEquals("", request.getMethod());
        assertEquals("", request.getPath());
        assertEquals("", request.getMimeType());
        assertNull(request.getData());
        assertEquals("", request.getDataAsText());
        assertNull(request.getHeader("header"));
        assertTrue(request.getHeaders().isEmpty());
        assertNull(request.getQueryParam("param"));
        assertTrue(request.getQueryParams().isEmpty());
        assertTrue(request.getPathParams().isEmpty());
        assertTrue(request.getExtras().isEmpty());

        var request2 = new HttpContext.Request("method", "path", headers, paramsList, "mimeType", longData, pathParams, extras);
        assertEquals("method", request2.getMethod());
        assertEquals("path", request2.getPath());
        assertEquals("mimeType", request2.getMimeType());
        assertEquals("A third-party OAuth application (JetBrains IDE Integration) with gist, read:org, repo", new String(request2.getData()));
        assertEquals("A third-party OAuth application (JetBrains IDE Integration) with gist, read:org, repo", request2.getDataAsText());
        assertEquals("value1", request2.getHeader("header"));
        assertEquals("{header=[value1, value2]}", request2.getHeaders().toString());
        assertEquals("value", request2.getQueryParam("param"));
        assertEquals("{param=[value]}", request2.getQueryParams().toString());
        assertEquals("{id=123}", request2.getPathParams().toString());
        assertEquals("{value={ \"type\": \"json\" }}", request2.getExtras().toString());

        assertEquals("Request[method=method, path=path, headers={header=[value1, value2]}, queryParams={param=[value]}, mimeType=mimeType, data=A third-party OAuth application (JetBrai..., pathParams={id=123}, extras={value={ \"type\": \"json\" }}]",
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
            .data(longData);

        assertEquals(404, response2.getStatus());
        assertEquals(headers, response2.getHeaders());
        assertEquals(new String(longData), new String(response2.getData()));
        assertEquals(new String(longData), response2.getDataAsText());

        var response3 = new HttpContext.Response(response2);

        assertEquals(404, response3.getStatus());
        assertEquals(headers, response3.getHeaders());
        assertEquals(new String(longData), new String(response3.getData()));
        assertEquals(new String(longData), response3.getDataAsText());

        assertEquals("Response[status=404, headers={header=[value1, value2]}, data=A third-party OAuth application (JetBrains...]",
                     response3.toString());

        var response4 = new HttpContext.Response()
                .text("data")
                .contentType("application/json")
                .addHeader("header2", "value2")
                .addHeader("header2", "value2");

        assertEquals("data", new String(response4.getData()));
        assertEquals("data", response4.getDataAsText());
        assertEquals("[application/json]", response4.getHeaders().get("Content-Type").toString());
        assertEquals("[value2]", response4.getHeaders().get("header2").toString());

        var response5 = new HttpContext.Response()
                .contentType("text/plain")
                .text("Formatted: %s, %s, %s \n", 12, 13.1, false);

        assertEquals("Formatted: 12, 13.1, false \n", new String(response5.getData()));
        assertEquals("[text/plain]", response5.getHeaders().get("Content-Type").toString());
    }

    @Test
    public void test_context() {
        var request = new HttpContext.Request(
                "method",
                "path",
                headers,
                paramsList,
                "mimeType",
                data,
                pathParams,
                extras
        );
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
        assertEquals("{header=[value1, value2]}", request2.getHeaders().toString());
        assertEquals("{param=[value]}", request2.getQueryParams().toString());

        var response2 = ctx.getResponse();
        assertNotNull(response2);
        assertEquals(404, response2.getStatus());
        assertEquals(headers, response2.getHeaders());
        assertEquals("data", new String(response2.getData()));
        assertEquals("data", response2.getDataAsText());

        assertEquals("HttpContext[request=Request[method=method, path=path, headers={header=[value1, value2]}, queryParams={param=[value]}, mimeType=mimeType, data=data, pathParams={id=123}, extras={value={ \"type\": \"json\" }}], response=Response[status=404, headers={header=[value1, value2]}, data=data]]",
                     ctx.toString());

        var ctx2 = new HttpContext(ctx);
        assertEquals("HttpContext[request=Request[method=method, path=path, headers={header=[value1, value2]}, queryParams={param=[value]}, mimeType=mimeType, data=data, pathParams={id=123}, extras={value={ \"type\": \"json\" }}], response=Response[status=404, headers={header=[value1, value2]}, data=data]]",
                     ctx2.toString());
    }

    @Test
    public void test_builder() {
        var ctx = HttpContext.newBuilder()
                .method("method")
                .path("path")
                .addHeader("header", "value1")
                .addHeader("header", "value2")
                .addQueryParam("param", "value")
                .contentType("mimeType")
                .data(data)
                .addPathParam("id", "123")
                .addExtras("value", "{ \"type\": \"json\" }")
                .build();

        var request = ctx.getRequest();
        assertEquals("method", request.getMethod());
        assertEquals("path", request.getPath());
        assertEquals("mimeType", request.getMimeType());
        assertEquals("data", new String(request.getData()));
        assertEquals("data", request.getDataAsText());
        assertEquals("{header=[value1, value2], Content-Type=[mimeType]}", request.getHeaders().toString());
        assertEquals("{param=[value]}", request.getQueryParams().toString());
        assertEquals("{id=123}", request.getPathParams().toString());
        assertEquals("{value={ \"type\": \"json\" }}", request.getExtras().toString());

        var response = ctx.getResponse();
        assertEquals(200, response.getStatus());
        assertTrue(response.getHeaders().isEmpty());
        assertNull(response.getData());
        assertNull(response.getDataAsText());

        var ctx2 = HttpContext.newBuilder()
                .data(data)
                .addHeader("header", "value1")
                .addHeader("header", "value2")
                .addHeader("header", "value1")
                .addQueryParam("name", "value1")
                .addQueryParam("name", "value2")
                .build();
        assertEquals("data", ctx2.getRequest().getDataAsText());
        assertEquals("Request[method=null, path=null, headers={header=[value1, value2]}, queryParams={name=[value1, value2]}, mimeType=null, data=data, pathParams={}, extras={}]",
                ctx2.getRequest().toString());

        var ctx3 = HttpContext.newBuilder()
                .request(ctx2.getRequest())
                .contentType("application/json")
                .build();
        assertEquals("data", ctx3.getRequest().getDataAsText());
        assertEquals("Request[method=null, path=null, headers={header=[value1, value2], Content-Type=[application/json]}, queryParams={name=[value1, value2]}, mimeType=application/json, data=data, pathParams={}, extras={}]",
                ctx3.getRequest().toString());

        var ctx4 = HttpContext.newBuilder()
                .text("some text")
                .build();
        assertEquals("some text", ctx4.getRequest().getDataAsText());
    }

}
