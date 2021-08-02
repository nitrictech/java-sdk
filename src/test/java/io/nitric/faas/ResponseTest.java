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

package io.nitric.faas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class ResponseTest {

    @Test
    public void http_response_to_grpc() {
        var ctx = new HttpResponseContext()
                .setStatus(200)
                .addHeader("x-nitric-test", "test");

        var response = new Response("Hello World".getBytes(StandardCharsets.UTF_8), ctx);

        var grpcResponse = response.toGrpcTriggerResponse();

        assertEquals(grpcResponse.getData().toString(StandardCharsets.UTF_8), "Hello World");
        assertTrue(grpcResponse.hasHttp());
        assertEquals(grpcResponse.getHttp().getStatus(), 200);
        assertEquals(grpcResponse.getHttp().getHeadersMap().get("x-nitric-test"), "test");
    }

    @Test public void topic_response_to_grpc() {
        var ctx = new TopicResponseContext()
                .setSuccess(false);

        var response = new Response("Hello World".getBytes(StandardCharsets.UTF_8), ctx);

        var grpcResponse = response.toGrpcTriggerResponse();

        assertEquals(grpcResponse.getData().toString(StandardCharsets.UTF_8), "Hello World");
        assertTrue(grpcResponse.hasTopic());
        assertFalse(grpcResponse.getTopic().getSuccess());
    }
}
