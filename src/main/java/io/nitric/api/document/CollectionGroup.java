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

import io.nitric.util.Contracts;

import java.util.Map;

/**
 * <p>
 *  Provides a query sub collection group class.
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
 * var orders = Documents.collection("customers").collection("orders");
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
public class CollectionGroup {

    final String name;
    final Key parent;

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    CollectionGroup(String name, Key parent) {
        Contracts.requireNonBlank(name, "name");

        this.name = name;
        this.parent = parent;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the sub collection name.
     *
     * @return the sub collection name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the sub collection parent key.
     *
     * @return the sub collection parent key
     */
    public Key getParent() {
        return parent;
    }

    /**
     * Create a new sub collection group query object with the <code>Map</code> value type.
     *
     * @return a new sub collection group query object
     */
    public Query<Map> query() {
        return new Query<Map>(toGrpcCollection(), Map.class);
    }

    /**
     * Create a new sub collection query object with the given value type.
     *
     * @param type the query value type (required)
     * @return a new sub collection group query object
     */
    public <T> Query<T> query(Class<T> type) {
        Contracts.requireNonNull(type, "type");

        return new Query<T>(toGrpcCollection(), type);
    }

    /**
     * Return this string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", parent=" + parent + "]";
    }

    // Package Private Methods ------------------------------------------------

    io.nitric.proto.document.v1.Collection toGrpcCollection() {
        var builder = io.nitric.proto.document.v1.Collection
                .newBuilder()
                .setName(name);

        if (parent != null) {
            var parentCol = io.nitric.proto.document.v1.Collection
                    .newBuilder()
                    .setName(parent.collection.name)
                    .build();

            var parentKey = io.nitric.proto.document.v1.Key
                    .newBuilder()
                    .setId(parent.id)
                    .setCollection(parentCol)
                    .build();

            builder.setParent(parentKey);
        }

        return builder.build();
    }

}
