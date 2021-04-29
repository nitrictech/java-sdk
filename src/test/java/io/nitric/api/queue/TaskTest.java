package io.nitric.api.queue;

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

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void test_build() {
        Map<String, Object> payload = Map.of("name", "value");

        var task = Task.newBuilder()
                .id("id")
                .leaseId("leaseId")
                .payloadType("payloadType")
                .payload(payload)
                .build();

        assertNotNull(task);
        assertEquals("id", task.getId());
        assertEquals("leaseId", task.getLeaseId());
        assertEquals("payloadType", task.getPayloadType());
        assertEquals(payload, task.getPayload());
        assertEquals("Task[id=id, leaseId=leaseId, payloadType=payloadType, payload={name=value}]", task.toString());

        try {
            Task.newBuilder().build();
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("payload parameter is required", npe.getMessage());
        }
    }
}
