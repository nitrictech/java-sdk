package io.nitric.api.document;

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

import io.nitric.util.Contracts;

import java.util.Map;

/**
 * <p>
 *  Provides a document Collection class.
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

    final DocColl collection;

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    Collection(DocColl collection) {
        Contracts.requireNonNull(collection, "collection");

        this.collection = collection;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Create a new collection Document Reference object for given document id and <code>Map</code> value type.
     *
     * @param id the document unique id (required)
     * @return new collection document reference
     */
    public DocumentRef<Map> doc(String id) {
        Contracts.requireNonBlank(id, "id");

        if (collection.parent != null && collection.parent.id.isBlank()) {
            throw newMissingParentException(id);
        }

        var docKey = new DocKey(collection, id);
        return new DocumentRef<Map>(docKey, Map.class);
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

        if (collection.parent != null && collection.parent.id.isBlank()) {
            throw newMissingParentException(id);
        }

        var docKey = new DocKey(collection, id);
        return new DocumentRef<T>(docKey, type);
    }

    /**
     * Create a new collection query object with the <code>Map</code> value type.
     *
     * @return a new collection query object
     */
    public Query<Map> query() {
        return new Query<Map>(collection, Map.class);
    }

    /**
     * Create a new collection query object with the given value type.
     *
     * @param type the query value type (required)
     * @return a new collection query object
     */
    public <T> Query<T> query(Class<T> type) {
        Contracts.requireNonNull(type, "type");

        return new Query<T>(collection, type);
    }

    /**
     * Create a new sub collection for this top level collection.
     *
     * @param name the name of the sub collection (required)
     * @return a new sub collection for the parent collection
     */
    public Collection collection(String name) {
        Contracts.requireNonBlank(name, "name");

        if (collection.parent != null) {
            var msg = "cannot make a collection under a sub collection";
            throw newUnsupportedSubCollOperation(msg);
        }

        var parentKey = new DocKey(collection, "");
        var subColl = new DocColl(name, parentKey);
        return new Collection(subColl);
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[collection=" + collection + "]";
    }

    // Package Private Methods ------------------------------------------------

    UnsupportedOperationException newUnsupportedSubCollOperation(String prefix) {

        var builder = new StringBuilder().append(prefix)
            .append(": \n\n Documents.collection(\"")
            .append(collection.parent.collection.name)
            .append("\n)");

        if (collection.parent.id != null && !collection.parent.id.isBlank()) {
            builder.append(".doc(\"").append(collection.parent.id).append("\")");
        }
        builder.append(".collection(\"").append(collection.name).append("\")");

        return new UnsupportedOperationException(builder.toString());
    }

    UnsupportedOperationException newMissingParentException(String id) {

        var msg = String.format(
                "parent document id not defined: %s:<unknown>/%s:%s \n\n"
                        + "Did you mean: Documents.collection(\"%s\").doc(id).subCollection(\"%s\").doc(\"%s\")\n",
                collection.parent.collection.name,
                collection.name,
                id,
                collection.name,
                collection.parent.collection.name,
                id);

        throw new UnsupportedOperationException(msg);
    }
}
