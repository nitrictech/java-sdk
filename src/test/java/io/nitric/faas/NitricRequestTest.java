package io.nitric.faas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NitricRequestTest {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String FORM_URL_ENCODED = "application/x-www-form-urlencoded";

    @Test public void test_request() throws Exception {
        var body = "hello world".getBytes(StandardCharsets.UTF_8);

        var request = NitricRequest
                .newBuilder()
                .method("GET")
                .path("/mycorp/customer")
                .query("id=123")
                .body(body)
                .build();

        assertEquals("/mycorp/customer", request.getPath());
        assertNotNull(request.getHeaders());
        assertEquals(0, request.getHeaders().size());
        assertNotNull(request.getBody());
        assertEquals(body, request.getBody());
    }

    @Test public void test_body() throws Exception {
        var request = NitricRequest.newBuilder().build();
        assertNull(request.getBody());
        assertNull(request.getBodyText());

        request = NitricRequest.newBuilder()
                .body("hello world".getBytes(StandardCharsets.UTF_8))
                .build();
        assertEquals("hello world".length(), request.getBody().length);
        assertEquals("hello world", request.getBodyText());
    }

    @Test public void test_headers() throws Exception {
        var headers = new HashMap<String, List<String>>();
        headers.put("Content-length", Arrays.asList("1024"));
        headers.put("Accept-Charset", Arrays.asList("ISO-8859-1", "utf-8"));

        var request = NitricRequest
                .newBuilder()
                .headers(headers)
                .build();

        assertNotNull(request.getHeaders());

        assertNotNull(request.getHeaders().get("Content-length"));
        assertEquals(1, request.getHeaders().get("Content-length").size());
        assertEquals("1024", request.getHeader("Content-length"));

        assertNotNull(request.getHeaders().get("Accept-Charset"));
        assertEquals(2, request.getHeaders().get("Accept-Charset").size());
        assertEquals("ISO-8859-1", request.getHeader("Accept-Charset"));

        assertNull(request.getHeader("User-Agent"));

        try {
            request.getHeaders().get("Accept-Charset").add("utf-16");
            assertTrue(false, "Cant modify headers");

        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test public void test_contentType() throws Exception {
        // Test GET
        var request = NitricRequest.newBuilder().build();
        assertNull(request.getContentType());

        // Test POST
        request = NitricRequest
                .newBuilder()
                .method("POST")
                .headers(Collections.singletonMap(CONTENT_TYPE, Arrays.asList(FORM_URL_ENCODED)))
                .body("a=1&b=2&c=3&c=4&c=5".getBytes(StandardCharsets.UTF_8))
                .build();

        assertEquals(FORM_URL_ENCODED, request.getContentType());

        // Test case sensitivity
        var headers = Collections.singletonMap("Content-type", Arrays.asList(FORM_URL_ENCODED));
        request = NitricRequest.newBuilder().headers(headers).build();
        assertEquals(FORM_URL_ENCODED, request.getContentType());

        headers = Collections.singletonMap("content-type", Arrays.asList(FORM_URL_ENCODED));
        request = NitricRequest.newBuilder().headers(headers).build();
        assertEquals(FORM_URL_ENCODED, request.getContentType());
    }

    @Test public void test_parameters() throws Exception {
        // Test GET
        var request = NitricRequest
                .newBuilder()
                .method("GET")
                .query("a=1&b=2&c=3&c=4&c=5")
                .build();

        assertEquals(3, request.getParameters().size());
        assertEquals("1", request.getParameter("a"));
        assertEquals("2", request.getParameter("b"));
        assertEquals("3", request.getParameter("c"));
        assertNull(request.getParameter("d"));

        assertEquals(Arrays.asList("3", "4", "5"), request.getParameters().get("c"));

        // Test DELETE
        request = NitricRequest
                .newBuilder()
                .method("DELETE")
                .query("customerId=1234567890")
                .build();

        assertEquals(1, request.getParameters().size());
        assertEquals("1234567890", request.getParameter("customerId"));

        assertEquals(Arrays.asList("1234567890"), request.getParameters().get("customerId"));

        // Test PUT
        request = NitricRequest
                .newBuilder()
                .method("PUT")
                .query("customerId=1234567890")
                .build();

        assertEquals(1, request.getParameters().size());
        assertEquals("1234567890", request.getParameter("customerId"));

        assertEquals(Arrays.asList("1234567890"), request.getParameters().get("customerId"));

        request = NitricRequest
                .newBuilder()
                .method("PUT")
                .headers(Collections.singletonMap(CONTENT_TYPE, Arrays.asList(FORM_URL_ENCODED)))
                .body("a=1&b=2&c=3&c=4&c=5".getBytes(StandardCharsets.UTF_8))
                .build();

        assertEquals(0, request.getParameters().size());
        assertNull(request.getParameter("a"));

        // Test POST
        request = NitricRequest
                .newBuilder()
                .method("POST")
                .headers(Collections.singletonMap(CONTENT_TYPE, Arrays.asList(FORM_URL_ENCODED)))
                .body("a=1&b=2&c=3&c=4&c=5".getBytes(StandardCharsets.UTF_8))
                .build();

        assertEquals(3, request.getParameters().size());
        assertEquals("1", request.getParameter("a"));
        assertEquals("2", request.getParameter("b"));
        assertEquals("3", request.getParameter("c"));
        assertNull(request.getParameter("d"));

        assertEquals(Arrays.asList("3", "4", "5"), request.getParameters().get("c"));
    }

    @Test public void test_toString() throws Exception {
        // Test GET
        var body = "hello world".getBytes(StandardCharsets.UTF_8);

        var request = NitricRequest
                .newBuilder()
                .method("GET")
                .path("/mycorp/customer")
                .query("id=123")
                .body(body)
                .build();

        assertEquals("NitricRequest[path=/mycorp/customer, headers={}, parameters={id=[123]}, body.length=11]",
                request.toString());

        // Test POST
        body = "id=123".getBytes(StandardCharsets.UTF_8);

        request = NitricRequest
                .newBuilder()
                .method("POST")
                .headers(Collections.singletonMap(CONTENT_TYPE, Arrays.asList(FORM_URL_ENCODED)))
                .path("/mycorp/customer")
                .body(body)
                .build();

        assertEquals("NitricRequest[path=/mycorp/customer, headers={Content-Type=[application/x-www-form-urlencoded]}, parameters={id=[123]}, body.length=6]",
                request.toString());
    }

}