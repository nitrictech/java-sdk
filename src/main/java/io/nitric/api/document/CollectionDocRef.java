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
 * Provides a collection Document Reference class.
 */
public class CollectionDocRef<T> extends AbstractDocRef<T> {

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    CollectionDocRef(DocKey key, Class<T> type) {
        super(key, type);
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Create a new sub collection query for this Document Ref.
     * The query object will have a <code>Map</code> value type.
     *
     * @param name the name of the sub collection (required)
     * @return a new collection query object
     */
    public Query<Map> query(String name) {
        Contracts.requireNonBlank(name, "name");

        var subColl = new DocColl(name, key);
        return new Query<Map>(subColl, Map.class);
    }

    /**
     * Create a new sub collection query for this Document Ref, and with the given value type.
     *
     * @param name the name of the sub collection (required)
     * @param type the query value type (required)
     * @return a new collection query object
     */
    public <K> Query<K> query(String name, Class<K> type) {
        Contracts.requireNonBlank(name, "name");
        Contracts.requireNonNull(type, "type");

        var subColl = new DocColl(name, key);
        return new Query<K>(subColl, type);
    }

    /**
     * Create a new sub collection for this top level document reference.
     *
     * @param name the name of the sub collection (required)
     * @return a new sub collection for the parent collection
     */
    public SubCollection collection(String name) {
        Contracts.requireNonBlank(name, "name");

        var subColl = new DocColl(name, key);
        return new SubCollection(subColl);
    }

}