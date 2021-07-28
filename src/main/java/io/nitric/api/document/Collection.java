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

import java.util.Map;

import io.nitric.util.Contracts;

/**
 * <p>
 *  Provides a Collection class.
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
 * // Create a customers collection
 * var customers = Documents.collection("customers");
 *
 * // Store a new customer document
 * var customerMap = Map.of("email", "anne.smith@example.com", "status", "active");
 *
 * customers.doc("anne.smith@example.com").set(customerMap);
 *
 * // Get customer document ref and content
 * var customerRef = customers.doc("anne.smith@example.com");
 *
 * customerMap = customerRef.get();
 *
 * // Delete a customer document
 * customers.doc("anne.smith@example.com").delete();
 * </code></pre>
 */
public class Collection {

    final String name;
    final Key parent;

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    Collection(String name, Key parent) {
        Contracts.requireNonBlank(name, "name");

        this.name = name;
        this.parent = parent;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the collection name.
     *
     * @return the collection name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the collection parent key.
     *
     * @return the collection parent key
     */
    public Key getParent() {
        return parent;
    }

    /**
     * Create a new collection Document Reference object for given document id and <code>Map</code> value type.
     *
     * @param id the document unique id (required)
     * @return new collection document reference
     */
    public DocumentRef<Map> doc(String id) {
        Contracts.requireNonBlank(id, "id");

        if (parent != null && parent.id.isBlank()) {
            throw newMissingParentException(id);
        }

        var key = new Key(this, id);
        return new DocumentRef<Map>(key, Map.class);
    }

    /**
     * Create a new collection Document Reference object for given document id and value type.
     *
     * @param id the document unique id (required)
     * @param type the document value type (required)
     * @return new collection document reference
     */
    public <T> DocumentRef<T> doc(String id, Class<T> type) {
        Contracts.requireNonBlank(id, "id");

        if (parent != null && parent.id.isBlank()) {
            throw newMissingParentException(id);
        }

        var key = new Key(this, id);
        return new DocumentRef<T>(key, type);
    }

    /**
     * Create a new collection query object with the <code>Map</code> value type.
     *
     * @return a new collection query object
     */
    public Query<Map> query() {
        return new Query<Map>(this, Map.class);
    }

    /**
     * Create a new collection query object with the given value type.
     *
     * @param type the query value type (required)
     * @return a new collection query object
     */
    public <T> Query<T> query(Class<T> type) {
        Contracts.requireNonNull(type, "type");

        return new Query<T>(this, type);
    }

    /**
     * Create a new sub collection for this top level collection.
     *
     * @param name the name of the sub collection (required)
     * @return a new sub collection for the parent collection
     */
    public Collection collection(String name) {
        Contracts.requireNonBlank(name, "name");

        if (parent != null) {
            throw newUnsupportedSubCollOperation("Max collection depth 1 exceeded");
        }

        var parentKey = new Key(this, "");
        return new Collection(name, parentKey);
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

    UnsupportedOperationException newUnsupportedSubCollOperation(String prefix) {

        var builder = new StringBuilder().append(prefix)
            .append(": [")
            .append(parent.collection.name);

        if (!parent.id.isBlank()) {
            builder.append(":").append(parent.id);
        }
        builder.append("]/[").append(name).append("]");

        return new UnsupportedOperationException(builder.toString());
    }

    UnsupportedOperationException newMissingParentException(String id) {

        var msg = String.format(
                "parent document id not defined: %s:<unknown>/%s:%s \n\n"
                        + "Did you mean: Documents.collection(\"%s\").doc(id).subCollection(\"%s\").doc(\"%s\")\n",
                parent.collection.name,
                name,
                id,
                name,
                parent.collection.name,
                id);

        throw new UnsupportedOperationException(msg);
    }
}
