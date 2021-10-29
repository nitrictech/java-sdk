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

public class Failed {
    public static void Example() {
        // [START snippet]
        var queue = new Queues().queue("my-queue");

        // Send task, then process the failed task response
        var failedTask = queue.send(Task.newBuilder()
          .id("1234")
          .payload(Map.of("example", "payload"))
          .build()
        );
        if (failedTask != null){
          System.out.println(failedTask.getMessage());
          // Attempt to resend task
          queue.send(failedTask.getTask());
        }
        // [END snippet]
    }
}