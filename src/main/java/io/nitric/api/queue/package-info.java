/**
 * <p>
 *  Provides the Queue Service API.
 * </p>
 *
 * <p>
 *  The example below illustrates using the Queue API.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.queue.QueueClient;
 *  import io.nitric.api.queue.Task;
 *  ...
 *
 *  String orderId = ...
 *  String serialNumber = ...
 *
 *  var payload = Map.of("orderId", orderId, "serialNumber", serialNumber);
 *  var task = Task.build(payload);
 *
 *  // Send a task to the 'shipping' queue client
 *  var client = QueueClient.build("shipping");
 *  client.send(task);
 *
 *  // Receive a list of tasks from the 'shipping' queue
 *  List&lt;Task&gt; tasks = client.receive(100);
 * </code></pre>
 *
 * @since 1.0
 */
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