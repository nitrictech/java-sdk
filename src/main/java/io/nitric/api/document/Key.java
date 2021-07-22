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

/**
 * Provides a document Key class.
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
     * @return the documents collections
     */
    public Collection getCollection() {
        return collection;
    }

    /**
     * @return the unique document collection  id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[collection=" + collection + ", id=" + id + "]";
    }

    // Package Private Methods ------------------------------------------------

    /**
     * @return the new GRPC Key [collection, id]
     */
    io.nitric.proto.document.v1.Key toGrpcKey() {
        return io.nitric.proto.document.v1.Key.newBuilder()
                .setCollection(collection.toGrpcCollection())
                .setId(id)
                .build();
    }
}
