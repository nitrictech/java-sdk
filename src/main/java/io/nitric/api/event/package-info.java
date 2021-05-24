/**
 * <p>
 *  Provides the Event Service API.
 * </p>
 *
 * <p>
 *  The example below illustrates using the Event API.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.Event;
 *  import io.nitric.api.EventClient;
 *
 *  // Create an order completed event
 *  var payload = Map.of("id", id, "status", "completed");
 *  var event = Event.build(payload);
 *
 *  // Publish the event to the orders topic
 *  var client = EventClient.newBuilder().build();
 *  client.publish("orders", event);
 *  </code></pre>
 *
 * @since 1.0
 */
package io.nitric.api.event;

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