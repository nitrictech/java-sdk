package io.nitric.faas;

/*-
 * #%L
 * Nitric Java SDK
 * %%
 * Copyright (C) 2021 Nitric Pty Ltd
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

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NitricResponseTest {

    @Test public void test_response() {
        var body = "hello world".getBytes(StandardCharsets.UTF_8);

        var response = NitricResponse
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
        var response = NitricResponse.newBuilder().build();

        assertEquals(0, response.getStatus());
        assertNotNull(response.getHeaders());
        assertEquals(0, response.getHeaders().size());
    }

    @Test public void test_headers() {
        var headers = new HashMap<String, String>();
        headers.put("Content-length", "1024");
        headers.put("Accept-Charset", "ISO-8859-1");

        var response = NitricResponse
                .newBuilder()
                .headers(headers)
                .build();

        assertNotNull(response.getHeaders());

        assertNotNull(response.getHeaders().get("Content-length"));
        assertEquals("1024", response.getHeaders().get("Content-length"));

        assertNotNull(response.getHeaders().get("Accept-Charset"));
        assertEquals("ISO-8859-1", response.getHeaders().get("Accept-Charset"));

        try {
            response.getHeaders().put("Accept-Charset", "utf-16");
            assertTrue(false, "Cant modify headers");

        } catch (Exception e) {
            assertTrue(true);
        }

        response = NitricResponse
                .newBuilder()
                .header("name", "value")
                .build();

        assertNotNull(response.getHeaders());
        assertEquals("value", response.getHeaders().get("name"));
    }

    @Test public void test_header() {
        var response = NitricResponse.newBuilder().header("Content-Type", "text/html").build();
        assertEquals("text/html", response.getHeaders().get("Content-Type"));
    }

    @Test public void test_bodyText() {
        var body = "hello world";

        var response = NitricResponse
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
        assertEquals(body, response.getBodyText());
    }

    @Test public void test_toString() {
        var response = NitricResponse
                .newBuilder()
                .build();

        assertEquals("NitricResponse[status=0, headers={}, body.length=0]",
                response.toString());
    }

    @Test public void test_build() {
        var response = NitricResponse.build("Hello Nitric");
        assertEquals(0, response.getStatus());
        assertEquals("Hello Nitric", response.getBodyText());

        response = NitricResponse.build(404);
        assertEquals(404, response.getStatus());
        assertEquals(0, response.getHeaders().size());

        response = NitricResponse.build(200, "Hello Nitric");
        assertEquals(200, response.getStatus());
        assertEquals("Hello Nitric", response.getBodyText());
    }

}
