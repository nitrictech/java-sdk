package io.nitric.faas.http;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpResponseTest {

    @Test public void test_response() {
        var body = "hello world".getBytes(StandardCharsets.UTF_8);

        var response = HttpResponse
                .newBuilder()
                .statusCode(301)
                .body(body)
                .build();

        assertEquals(301, response.getStatusCode());
        assertNotNull(response.getHeaders());
        assertEquals(1, response.getHeaders().size());
        assertNotNull(response.getBody());
        assertEquals(body, response.getBody());
        assertEquals(11, response.getBodyLength());
    }

    @Test public void test_defaults() {
        var response = HttpResponse.newBuilder().build();

        assertEquals(200, response.getStatusCode());
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

        response = HttpResponse.newBuilder().bodyText("Hello World").build();
        assertEquals("text/html; charset=UTF-8", response.getHeader("Content-Type"));
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
            assertTrue(false, "Cant modify headers");

        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test public void test_contentType() {
        var response = HttpResponse.newBuilder().contentType("text/html").build();
        assertEquals("text/html", response.getHeader("Content-Type"));
    }

    @Test public void test_bodyText() {
        var body = "hello world";

        var response = HttpResponse
                .newBuilder()
                .statusCode(301)
                .bodyText(body)
                .build();

        assertEquals(301, response.getStatusCode());
        assertNotNull(response.getHeaders());
        assertEquals(1, response.getHeaders().size());
        assertNotNull(response.getBody());
        assertEquals(body, new String(response.getBody(), StandardCharsets.UTF_8));
        assertEquals(11, response.getBodyLength());
    }

    @Test public void test_toString() {
        var response = HttpResponse
                .newBuilder()
                .build();

        assertEquals("HttpResponse[statusCode=200, headers={}, body.length=0]",
                response.toString());
    }

    @Test public void test_build() {
        var response = HttpResponse.build(404);
        assertEquals(404, response.getStatusCode());
        assertEquals(0, response.getHeaders().size());

        response = HttpResponse.build(200, "Hello Nitric");
        assertEquals(200, response.getStatusCode());
        assertEquals("Hello Nitric", new String(response.getBody(), StandardCharsets.UTF_8));
        assertEquals("text/html; charset=UTF-8", response.getHeader("Content-Type"));
    }

}