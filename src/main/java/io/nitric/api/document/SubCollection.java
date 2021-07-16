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

import java.util.Map;
import io.nitric.util.Contracts;

/**
 * Provides a document Sub Collection class.
 */
public class SubCollection extends AbstractCollection {

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    SubCollection(DocColl collection) {
        super(collection);

        Contracts.requireNonNull(collection.parent, "collection.parent");
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Create a new collection Document Reference object for given document id and <code>Map</code> value type.
     *
     * @param id the document unique id (required)
     * @return new collection document reference
     */
    @Override
    public SubCollectionDocRef<Map> doc(String id) {
        Contracts.requireNonBlank(id, "id");

        if (collection.parent.id.isBlank()) {
            throw newMissingParentException(id);
        }

        var docKey = new DocKey(collection, id);
        return new SubCollectionDocRef<Map>(docKey, Map.class);
    }

    /**
     * Create a new collection Document Reference object for given document id and value type.
     *
     * @param id   the document unique id (required)
     * @param type the document value type (required)
     * @return new collection document reference
     */
    @Override
    public <K> SubCollectionDocRef<K> doc(String id, Class<K> type) {
        Contracts.requireNonBlank(id, "id");

        if (collection.parent.id.isBlank()) {
            throw newMissingParentException(id);
        }

        var docKey = new DocKey(collection, id);
        return new SubCollectionDocRef<K>(docKey, type);
    }

    /**
     * Create a new sub collection query object with the <code>Map</code> value type.
     *
     * @return a new collection query object
     */
    @Override
    public Query<Map> query() {
        return new Query<Map>(collection, Map.class);
    }

    /**
     * Create a new sub collection query object with the given value type.
     *
     * @param type the query value type (required)
     * @return a new collection query object
     */
    @Override
    public <T> Query<T> query(Class<T> type) {
        Contracts.requireNonNull(type, "type");

        return new Query<T>(collection, type);
    }

    // Package Private Methods ------------------------------------------------

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