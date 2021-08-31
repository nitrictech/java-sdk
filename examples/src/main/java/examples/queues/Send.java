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

package examples.queues;
// [START import]
import io.nitric.api.queue.Queues;
import io.nitric.api.queue.Task;
import java.util.Map;
// [END import]

public class Send {
    public static void Example() {
        // [START snippet]
        var queue = Queues.queue("my-queue");
        queue.send(
            Task.newBuilder()
                .id("1234")
                .payload(Map.of("example", "payload"))
                .build()
        );
        // [END snippet]
    }
}