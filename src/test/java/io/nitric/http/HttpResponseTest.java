package io.nitric.http;

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

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class HttpResponseTest {

    @Test public void test_response() {
        var body = "hello world".getBytes(StandardCharsets.UTF_8);

        var response = HttpResponse
                .newBuilder()
                .status(301)
                .body(body)
                .build();

        assertEquals(301, response.getStatus());
        assertNotNull(response.getHeaders());
        assertEquals(0, response.getHeaders().size());
        assertNotNull(response.getBody());
        assertEquals(body, response.getBody());
        assertEquals(11, response.getBodyLength());
    }

    @Test public void test_defaults() {
        var response = HttpResponse.newBuilder().build();

        assertEquals(0, response.getStatus());
        assertNotNull(response.getHeaders());
        assertEquals(0, response.getHeaders().size());

        response = HttpResponse.newBuilder().bodyText("{ 'a': 'b' }").build();
        assertEquals("text/json; charset=UTF-8", response.getHeader("Content-Type"));

        response = HttpResponse.newBuilder().bodyText("[{'a':'b'}]").build();
        assertEquals("text/json; charset=UTF-8", response.getHeader("Content-Type"));

        response = HttpResponse.newBuilder().bodyText("[{'a':'b'}]").build();
        assertEquals("text/json; charset=UTF-8", response.getHeader("Content-Type"));

        response = HttpResponse.newBuilder().bodyText("<?xml><body></body>").build();
        assertEquals("text/xml; charset=UTF-8", response.getHeader("Content-Type"));

        response = HttpResponse.newBuilder().bodyText("<!DOCTYPE html><html></html>").build();
        assertEquals("text/html; charset=UTF-8", response.getHeader("Content-Type"));
        assertEquals("text/html; charset=UTF-8", response.getHeader("content-type"));

        response = HttpResponse.newBuilder().bodyText(" <!doctype html><html></html> ").build();
        assertEquals("text/html; charset=UTF-8", response.getHeader("Content-Type"));
        assertEquals("text/html; charset=UTF-8", response.getHeader("content-type"));

        response = HttpResponse.newBuilder().bodyText("Hello World").build();
        assertNull(response.getHeader("Content-Type"));
    }

    @Test public void test_headers() {
        var headers = new HashMap<String, List<String>>();
        headers.put("Content-length", Arrays.asList("1024"));
        headers.put("Accept-Charset", Arrays.asList("ISO-8859-1", "utf-8"));

        var response = HttpResponse
                .newBuilder()
                .headers(headers)
                .build();

        assertNotNull(response.getHeaders());

        assertNotNull(response.getHeaders().get("Content-length"));
        assertEquals(1, response.getHeaders().get("Content-length").size());
        assertEquals("1024", response.getHeader("Content-length"));

        assertNotNull(response.getHeaders().get("Accept-Charset"));
        assertEquals(2, response.getHeaders().get("Accept-Charset").size());
        assertEquals("ISO-8859-1", response.getHeader("Accept-Charset"));

        try {
            response.getHeaders().get("Accept-Charset").add("utf-16");
            assertTrue(false);

        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test public void test_header() {
        var response = HttpResponse.newBuilder().header("Content-Type", "text/html").build();
        assertEquals("text/html", response.getHeader("Content-Type"));
    }

    @Test public void test_bodyText() {
        var body = "hello world";

        var response = HttpResponse
                .newBuilder()
                .status(301)
                .bodyText(body)
                .build();

        assertEquals(301, response.getStatus());
        assertNotNull(response.getHeaders());
        assertEquals(0, response.getHeaders().size());
        assertNotNull(response.getBody());
        assertEquals(body, new String(response.getBody(), StandardCharsets.UTF_8));
        assertEquals(11, response.getBodyLength());
    }

    @Test public void test_toString() {
        var response = HttpResponse
                .newBuilder()
                .build();

        assertEquals("HttpResponse[status=0, headers={}, body.length=0]",
                response.toString());
    }

    @Test public void test_build() {
        var response = HttpResponse.build(404);
        assertEquals(404, response.getStatus());
        assertEquals(0, response.getHeaders().size());

        response = HttpResponse.build(200, "Hello Nitric");
        assertEquals(200, response.getStatus());
        assertEquals("Hello Nitric", new String(response.getBody(), StandardCharsets.UTF_8));
        assertNull(response.getHeader("Content-Type"));
    }

}
