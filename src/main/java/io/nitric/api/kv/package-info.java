/**
 * <p>
 *  Provides the Key Value Service API.
 * </p>
 *
 * <p>
 *  The example below illustrates using the Key Value API.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.kv.KeyValueClient;
 *  import io.nitric.api.kv.Query.QueryResult;
 *  import java.util.Map;
 *  ...
 *
 *  // Create a 'customers' collection KV client
 *  KeyValueClient client = KeyValueClient.build(Map.class, "customers");
 *
 *  // Get a customer record
 *  String key = "john.smith@gmail.com";
 *  Map&lt;String, Object&gt; customer = client.newGet()
 *      .key(key)
 *      .get();
 *
 *  // Update a customer record
 *  customer.put("mobile", "0432 321 543");
 *  client.newPut()
 *      .key(key)
 *      .value(value)
 *      .put();
 *
 *  // Delete a customer record
 *  client.newDelete()
 *      .key(key)
 *      .delete();
 *
 *  // Fetch first 100 customer records with an active status
 *  QueryResult results = client.newQuery()
 *      .where("status", "==", "active")
 *      .limit(100)
 *      .fetch();
 *
 *  results.foreach(customer -&gt; {
 *      // Process customer...
 *  });
 * </code></pre>
 *
 * @since 1.0
 */
package io.nitric.api.kv;

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