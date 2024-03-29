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
 * var customers = new Documents().collection("customers");
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
    Collection(String name, Key parent) {
        super(name, parent);
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

        var key = new Key(this, id);
        return new DocumentRef<>(key, Map.class);
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

        var key = new Key(this, id);
        return new DocumentRef<>(key, type);
    }

    /**
     * Create a new sub collection query group for this collection.
     *
     * @param name the name of the sub collection (required)
     * @return a new sub collection query group for the parent collection
     */
    public CollectionGroup collection(String name) {
        Contracts.requireNonBlank(name, "name");

        var parentKey = new Key(this, "");
        return new CollectionGroup(name, parentKey);
    }

}
