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

/**
 * Provides a Document Key class.
 *
 * @see Collection
 */
public class Key {

    Collection collection;
    String id;

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder pattern
     */
    Key(Collection collection, String id) {
        Contracts.requireNonNull(collection, "collection");

        this.collection = collection;
        this.id = (id != null) ? id : "";
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the document collection.
     *
     * @return the documents collection
     */
    public Collection getCollection() {
        return collection;
    }

    /**
     * Return the unique collection document id.
     *
     * @return the unique document collection  id
     */
    public String getId() {
        return id;
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[collection=" + collection + ", id=" + id + "]";
    }

    // Package Private Methods ------------------------------------------------

    static Key buildFromGrpcKey(io.nitric.proto.document.v1.Key key) {
        var grpcParentKey = key.getCollection().getParent();

        if (grpcParentKey != null) {
            var parentCol = new Collection(grpcParentKey.getCollection().getName(), null);
            var parentKey = new Key(parentCol, grpcParentKey.getId());
            return new Key(new Collection(key.getCollection().getName(), parentKey), key.getId());

        } else {
            return new Key(new Collection(key.getCollection().getName(), null), key.getId());
        }
    }

    io.nitric.proto.document.v1.Key toGrpcKey() {
        return io.nitric.proto.document.v1.Key.newBuilder()
                .setCollection(collection.toGrpcCollection())
                .setId(id)
                .build();
    }

}
