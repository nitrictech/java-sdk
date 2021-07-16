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
public class Collection extends AbstractCollection {

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    Collection(DocColl collection) {
        super(collection);
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Create a new collection Document Reference object for given document id and <code>Map</code> value type.
     *
     * @param id the document unique id (required)
     * @return new collection document reference
     */
    @Override
    public CollectionDocRef<Map> doc(String id) {
        Contracts.requireNonBlank(id, "id");

        var docKey = new DocKey(collection, id);
        return new CollectionDocRef<Map>(docKey, Map.class);
    }

    /**
     * Create a new collection Document Reference object for given document id and value type.
     *
     * @param id the document unique id (required)
     * @param type the document value type (required)
     * @return new collection document reference
     */
    @Override
    public <T> CollectionDocRef<T> doc(String id, Class<T> type) {
        Contracts.requireNonBlank(id, "id");

        var docKey = new DocKey(collection, id);
        return new CollectionDocRef<T>(docKey, type);
    }

    /**
     * Create a new collection query object with the <code>Map</code> value type.
     *
     * @return a new collection query object
     */
    @Override
    public Query<Map> query() {
        return new Query<Map>(collection, Map.class);
    }

    /**
     * Create a new collection query object with the given value type.
     *
     * @param type the query value type (required)
     * @return a new collection query object
     */
    @Override
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
    public SubCollection collection(String name) {
        Contracts.requireNonBlank(name, "name");

        var parentKey = new DocKey(collection, "");
        var subColl = new DocColl(name, parentKey);
        return new SubCollection(subColl);
    }

}
