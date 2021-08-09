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

package io.nitric.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nitric.faas.Faas;
import io.nitric.faas.NitricFunction;
import io.nitric.faas.Response;
import io.nitric.faas.Trigger;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Provides a MockTrigger test case.
 */
public class MockTriggerTest {

    final static String LONG_DATA = "Another two syncons at the Robertstown sub-station should be switched on in the coming week, and while...";
    final static String RESPONSE_TEXT = "{\"status\": \"completed\"}";

    @Test
    public void test_build_http_trigger() {
        var headers = Map.of("content-length", "1024");
        var queryParams = Map.of("limit", "100");

        var trigger = MockTrigger.newHttpTriggerBuilder()
                .setDataAsText("data")
                .setMimeType("mimeType")
                .setPath("/path")
                .setMethod("GET")
                .setHeaders(headers)
                .setQueryParams(queryParams)
                .build();

        assertNotNull(trigger);
        assertEquals("data", new String(trigger.getData(), StandardCharsets.UTF_8));
        assertEquals("mimeType", trigger.getMimeType());
        assertNotNull(trigger.getContext());
        assertTrue(trigger.getContext().isHttp());
        assertFalse(trigger.getContext().isTopic());
        assertEquals("/path", trigger.getContext().asHttp().getPath());
        assertEquals("GET", trigger.getContext().asHttp().getMethod());
        assertEquals(headers.toString(), trigger.getContext().asHttp().getHeaders().toString());
        assertEquals(queryParams.toString(), trigger.getContext().asHttp().getQueryParams().toString());

        trigger = MockTrigger.newHttpTriggerBuilder()
                .setData("data".getBytes(StandardCharsets.UTF_8))
                .setMimeType("mimeType")
                .setPath("/path")
                .setMethod("GET")
                .addHeader("content-length", "1024")
                .addQueryParam("limit", "100")
                .build();

        assertNotNull(trigger);
        assertEquals("data", new String(trigger.getData(), StandardCharsets.UTF_8));
        assertNotNull(trigger.getContext());
        assertTrue(trigger.getContext().isHttp());
        assertFalse(trigger.getContext().isTopic());
        assertNotNull(trigger.getContext().asHttp());
        assertEquals("/path", trigger.getContext().asHttp().getPath());
        assertEquals("GET", trigger.getContext().asHttp().getMethod());
        assertEquals(headers.toString(), trigger.getContext().asHttp().getHeaders().toString());
        assertEquals(queryParams.toString(), trigger.getContext().asHttp().getQueryParams().toString());

        var context = trigger.getContext();
        try {
            context.asTopic();
            fail();
        } catch (UnsupportedOperationException uoe) {
        }

        assertEquals("MockTrigger[context=MockHttpTriggerContext[method=GET, path=/path, headers={content-length=1024}, queryParams{limit=100}], mimeType=mimeType, data=data]",
                trigger.toString());
    }

    @Test
    public void test_build_http_response() {
        var headers = Map.of("content-length", "1024");

        var trigger = MockTrigger.newHttpTriggerBuilder()
                .setDataAsText("data")
                .setMimeType("mimeType")
                .setPath("/path")
                .setMethod("GET")
                .setHeaders(headers)
                .build();

        var response = trigger.buildResponse();
        assertNotNull(response);
        assertNull(response.getData());
        assertNotNull(response.getContext());
        assertNotNull(response.getContext().asHttp());
        assertNull(response.getContext().asTopic());
        assertTrue(response.getContext().isHttp());
        assertFalse(response.getContext().isTopic());

        response = trigger.buildResponse(RESPONSE_TEXT);

        response.getContext().asHttp()
                .setHeaders(headers)
                .setStatus(200);

        assertNotNull(response);
        assertEquals(RESPONSE_TEXT, new String(response.getData(), StandardCharsets.UTF_8));
        assertNotNull(response.getContext());
        assertNotNull(response.getContext().asHttp());
        assertEquals(200, response.getContext().asHttp().getStatus());
        assertEquals(headers.toString(), response.getContext().asHttp().getHeaders().toString());
        assertNull(response.getContext().asTopic());
        assertTrue(response.getContext().isHttp());
        assertFalse(response.getContext().isTopic());

        assertEquals("Response[context=HttpResponseContext[status=200, headers={content-length=1024}], data={\"status\": \"completed\"}]",
                response.toString());
    }

    @Test
    public void test_build_topic_trigger() {
        var trigger = MockTrigger.newTopicTriggerBuilder()
                .setDataAsText(LONG_DATA)
                .setMimeType("mimeType")
                .setTopic("topic")
                .build();

        assertNotNull(trigger);
        assertEquals(LONG_DATA, new String(trigger.getData(), StandardCharsets.UTF_8));
        assertEquals("mimeType", trigger.getMimeType());
        assertNotNull(trigger.getContext());
        assertTrue(trigger.getContext().isTopic());
        assertFalse(trigger.getContext().isHttp());
        assertNotNull(trigger.getContext().asTopic());
        assertEquals("topic", trigger.getContext().asTopic().getTopic());

        assertNotNull(trigger.toString());

        trigger = MockTrigger.newTopicTriggerBuilder()
                .setData("data".getBytes(StandardCharsets.UTF_8))
                .setMimeType("mimeType")
                .setTopic("topic")
                .build();

        assertNotNull(trigger);
        assertEquals("data", new String(trigger.getData(), StandardCharsets.UTF_8));
        assertNotNull(trigger.getContext());
        assertTrue(trigger.getContext().isTopic());
        assertFalse(trigger.getContext().isHttp());
        assertNotNull(trigger.getContext().asTopic());
        assertEquals("topic", trigger.getContext().asTopic().getTopic());

        var context = trigger.getContext();
        try {
            context.asHttp();
            fail();
        } catch (UnsupportedOperationException uoe) {
        }

        assertEquals("MockTrigger[context=MockTopicTriggerContext[topic=topic], mimeType=mimeType, data=data]",
                trigger.toString());
    }

    @Test
    public void test_build_topic_response() {
        var trigger = MockTrigger.newTopicTriggerBuilder()
                .setDataAsText("data")
                .setMimeType("mimeType")
                .setTopic("topic")
                .build();

        var response = trigger.buildResponse();
        assertNotNull(response);
        assertNull(response.getData());
        assertNotNull(response.getContext());
        assertNotNull(response.getContext().asTopic());
        assertTrue(response.getContext().asTopic().isSuccess());
        assertNull(response.getContext().asHttp());
        assertTrue(response.getContext().isTopic());
        assertFalse(response.getContext().isHttp());

        response = trigger.buildResponse(RESPONSE_TEXT);
        assertNotNull(response);
        assertEquals(RESPONSE_TEXT, new String(response.getData(), StandardCharsets.UTF_8));
        assertNotNull(response.getContext());
        assertNotNull(response.getContext().asTopic());
        assertTrue(response.getContext().asTopic().isSuccess());
        assertNull(response.getContext().asHttp());
        assertTrue(response.getContext().isTopic());
        assertFalse(response.getContext().isHttp());

        assertEquals("Response[context=TopicResponseContext[success=true], data={\"status\": \"completed\"}]",
                response.toString());
    }

    @Test
    public void test_user_not_found() {

        final var expectHeaders = Map.of("Content-Type", "application/json");

        var trigger = MockTrigger.newHttpTriggerBuilder()
                .setMethod("GET")
                .setPath("/user/")
                .setQueryParams(Map.of("id", "123456"))
                .build();

        // Test User Not Found
        var response = new GetUserHandler().handle(trigger);
        assertNotNull(response);
        assertEquals("{ \"message\": \"User '123456' not found\" }", response.getDataAsText());

        var httpCtx = response.getContext().asHttp();
        assertEquals(404, httpCtx.getStatus());
        assertEquals(expectHeaders, httpCtx.getHeaders());
    }

    public static class GetUserHandler implements NitricFunction {

        @Override
        public Response handle(Trigger trigger)  {
            String id = trigger.getContext()
                    .asHttp()
                    .getQueryParams()
                    .get("id");

            // TODO: lookup user...
            Map<String, String> user = null;

            if (user == null) {
                // Error Handling
                var msg = String.format("{ \"message\": \"User '%s' not found\" }", id);

                var response = trigger.buildResponse(msg);

                response.getContext()
                        .asHttp()
                        .setStatus(404)
                        .addHeader("Content-Type", "application/json");

                return response;
            }

            try {
                var json = new ObjectMapper().writeValueAsString(user);

                var response = trigger.buildResponse(json.getBytes(StandardCharsets.UTF_8));

                response.getContext()
                        .asHttp()
                        .setStatus(200)
                        .addHeader("Content-Type", "application/json");

                return response;

            } catch (JsonProcessingException jpe) {
                var response = trigger.buildResponse();

                response.getContext()
                        .asHttp()
                        .setStatus(500)
                        .addHeader("Content-Type", "application/json");

                return response;
            }
        }

        public static void main(String... args) {
            Faas.start(new GetUserHandler());
        }
    }

}
