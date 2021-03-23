package io.nitric.faas;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NitricEventTest {

    @Test
    public void test_event() throws Exception {
        var headers = new HashMap<String, List<String>>();
        headers.put("x-nitric-request-id", List.of("x-nitric-request-id"));
        headers.put("x-nitric-source-type", List.of("x-nitric-source-type"));
        headers.put("x-nitric-source", List.of("x-nitric-source"));
        headers.put("x-nitric-payload-type", List.of("x-nitric-payload-type"));

        var payload = "hello world".getBytes(StandardCharsets.UTF_8);

        var event = NitricEvent.newBuilder().headers(headers).payload(payload).build();
        assertNotNull(event);
        assertTrue(event.toString().contains("NitricContext[requestId=x-nitric-request-id"));

        assertNotNull(event.getContext());
        assertEquals("x-nitric-request-id", event.getContext().requestID);
        assertEquals("x-nitric-source-type", event.getContext().sourceType);
        assertEquals("x-nitric-source", event.getContext().source);
        assertEquals("x-nitric-payload-type", event.getContext().payloadType);
        assertTrue(event.getContext().toString().contains("NitricContext[requestId=x-nitric-request-id"));

        assertNotNull(event.getPayload());
        assertNotNull(event.getPayloadText());
        assertEquals("hello world", event.getPayloadText());
    }

}
