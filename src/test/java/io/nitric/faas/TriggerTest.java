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
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;

import com.google.protobuf.ByteString;

import org.junit.Test;

import io.nitric.proto.faas.v1.HttpTriggerContext;
import io.nitric.proto.faas.v1.TopicTriggerContext;
import io.nitric.proto.faas.v1.TriggerRequest;

public class TriggerTest {

    @Test public void test_from_grpc_http() {
        var triggerContext = HttpTriggerContext
                .newBuilder()
                .setMethod("GET")
                .setPath("/test/");

        triggerContext.putHeaders("x-nitric-test", "test");
        triggerContext.putQueryParams("id", "test");

        var triggerRequest = TriggerRequest
                .newBuilder()
                .setData(ByteString.copyFrom("Hello World", StandardCharsets.UTF_8))
                .setMimeType("text/plain")
                .setHttp(triggerContext.build())
                .build();

        var trigger = Trigger.buildTrigger(triggerRequest);


        assertEquals(new String(trigger.getData()), "Hello World");
        assertEquals(trigger.getMimeType(), "text/plain");
        assertTrue(trigger.getContext().isHttp());
        assertEquals(trigger.getContext().asHttp().getMethod(), "GET");
        assertEquals(trigger.getContext().asHttp().getPath(), "/test/");
        assertEquals(trigger.getContext().asHttp().getHeaders().get("x-nitric-test"), "test");
        assertEquals(trigger.getContext().asHttp().getQueryParams().get("id"), "test");

        assertEquals("Trigger[context=HttpTriggerContext[method=GET, path=/test/, headers={x-nitric-test=test}, queryParams{id=test}], mimeType=text/plain, data=Hello World]", trigger.toString());
    }

    @Test public void test_from_grpc_topic() {
        var triggerContext = TopicTriggerContext
                .newBuilder()
                .setTopic("test");

        var triggerRequest = TriggerRequest
                .newBuilder()
                .setData(ByteString.copyFrom("Hello World", StandardCharsets.UTF_8))
                .setMimeType("text/plain")
                .setTopic(triggerContext.build())
                .build();

        var trigger = Trigger.buildTrigger(triggerRequest);

        assertEquals(new String(trigger.getData()), "Hello World");
        assertEquals(trigger.getMimeType(), "text/plain");
        assertTrue(trigger.getContext().isTopic());
        assertEquals(trigger.getContext().asTopic().getTopic(), "test");

        assertEquals("Trigger[context=TopicTriggerContext[topic=test], mimeType=text/plain, data=Hello World]", trigger.toString());
    }
}
