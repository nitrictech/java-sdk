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
 * Provides an abstract Document Collection class.
 */
public abstract class AbstractCollection {

    final DocColl collection;

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    AbstractCollection(DocColl collection) {
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
    public abstract AbstractDocRef<Map> doc(String id);

    /**
     * Create a new collection Document Reference object for given document id and value type.
     *
     * @param id the document unique id (required)
     * @param type the document value type (required)
     * @return new collection document reference
     */
    public abstract <T> AbstractDocRef<T> doc(String id, Class<T> type);

    /**
     * Create a new collection query object with the <code>Map</code> value type.
     *
     * @return a new collection query object
     */
    public abstract Query<Map> query();

    /**
     * Create a new collection query object with the given value type.
     *
     * @param type the query value type (required)
     * @return a new collection query object
     */
    public abstract <T> Query<T> query(Class<T> type);

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[collection=" + collection + "]";
    }

}
