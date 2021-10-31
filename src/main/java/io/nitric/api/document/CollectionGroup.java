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

package io.nitric.api.document;

/**
 * <p>
 *  Provides a query sub CollectionGroup class.
 * </p>
 *
 * <p>
 *  The example below illustrates the Document Collection API.
 * </p>
 *
 * <pre><code class="code">
 * import io.nitric.api.document.Documents;
 * import java.util.Map;
 * ...
 *
 * // Create an orders query sub collection group
 * var orders = new Documents().collection("customers").collection("orders");
 *
 * var stream = orders.query()
 *      .where("status", "==", "In-Progress")
 *      .limit(100)
 *      .stream();
 *
 *  stream.foreach(doc -&gt; {
 *      // Process results...
 *  });
 * </code></pre>
 */
public class CollectionGroup extends AbstractCollection {

    /*
     * Enforce package builder patterns.
     */
    CollectionGroup(String name, Key parent) {
        super(name, parent);
    }

}
