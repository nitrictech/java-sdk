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

package io.nitric.examples.queues;
// [START import]
import io.nitric.api.queue.Queues;
import io.nitric.api.queue.ReceivedTask;
import java.util.Map;
import java.util.List;
// [END import]

public class Receive {
    public static void Example() {
        // [START snippet]
        // Construct a new queue client for the 'example' queue
        var queue = Queues.queue("my-queue");

        // Receive a list of tasks from the 'example' queue
        List<ReceivedTask> tasks = queue.receive(1);

        for (ReceivedTask task : tasks) {
            try {
                // process task here
                // processTask(task);

                task.complete();
            } catch (Exception e) {
                // We don't need to requeue the task here
                // we can simply log the error and move on
                // the task will automatically requeue itself
            }
        }
        // [END snippet]
    }
}