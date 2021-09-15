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

package io.nitric.api.queue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class ReceivedTaskTest {

    @Test
    public void test_build() {
        Map<String, Object> payload = Map.of("name", "value");

        var task = ReceivedTask.newReceivedTaskBuilder()
                .id("id")
                .payloadType("payloadType")
                .payload(payload)
                .leaseId("leaseId")
                .queue("queue")
                .build();

        assertNotNull(task);
        assertEquals("id", task.getId());
        assertEquals("payloadType", task.getPayloadType());
        assertEquals(payload, task.getPayload());
        assertEquals("leaseId", task.getLeaseId());
        assertEquals("queue", task.getQueue());

        try {
            ReceivedTask.newReceivedTaskBuilder().build();
            fail();

        } catch (IllegalArgumentException iae) {
        }
    }
}
