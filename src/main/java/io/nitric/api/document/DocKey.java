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

import io.nitric.proto.document.v1.Key;
import io.nitric.util.Contracts;

/**
 * Provides an internal API document key class.
 */
class DocKey {

    final DocColl collection;
    final String id;

    // Constructors ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    DocKey(DocColl collection, String id) {
        Contracts.requireNonNull(collection, "collection");

        this.collection = collection;
        this.id = (id != null) ? id : "";
    }

    // Package Private Methods ------------------------------------------------

    /**
     * @return the new GRPC Key [collection, id]
     */
    Key toKey() {
        return Key.newBuilder()
                .setCollection(collection.toCollection())
                .setId(id)
                .build();
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[collection=" + collection + ", id=" + id + "]";
    }
}
