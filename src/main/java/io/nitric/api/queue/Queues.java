package io.nitric.api.queue;

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

import io.nitric.proto.queue.v1.QueueServiceGrpc;
import io.nitric.proto.queue.v1.QueueServiceGrpc.QueueServiceBlockingStub;
import io.nitric.util.Contracts;
import io.nitric.util.GrpcChannelProvider;

/**
 * <p>
 * Provides the Queue API client.
 * </p>
 *
 * <p>
 *  The example below illustrates the Queue API.
 * </p>
 *
 * <pre><code class="code">
 * import io.nitric.api.queue.Queues;
 * import io.nitric.api.queue.Task;
 * import io.nitric.api.queue.ReceivedTask;
 * ...
 *
 * String orderId = ...
 * String serialNumber = ...
 *
 * var payload = Map.of("orderId", orderId, "serialNumber", serialNumber);
 * var task = Task.build(payload);
 *
 * // Send a task to the 'shipping' queue
 * var queue = Queues.queue("shipping");
 * queue.send(task);
 *
 * // Receive a list of tasks from the 'shipping' queue
 * List&lt;ReceivedTask&gt; tasks = queue.receive(100);
 *
 * // Complete the first shipping task
 * var shippingTask = tasks.get(0);
 * shippingTask.complete();
 * </code></pre>
 *
 * @see Queue
 * @see Task
 */
public class Queues {

    static QueueServiceBlockingStub serviceStub;

    // Public Methods ---------------------------------------------------------

    /**
     * Create a new queue with the given name.
     *
     * @param name the name of the queue (required)
     * @return a new queue
     */
    public static Queue queue(String name) {
        Contracts.requireNonBlank(name, "name");

        return new Queue(name);
    }

    // Package Private Methods ------------------------------------------------

    static QueueServiceBlockingStub getServiceStub() {
        if (serviceStub == null) {
            var channel = GrpcChannelProvider.getChannel();
            serviceStub = QueueServiceGrpc.newBlockingStub(channel);
        }
        return serviceStub;
    }

    static void setServiceStub(QueueServiceBlockingStub stub) {
        serviceStub = stub;
    }

}
