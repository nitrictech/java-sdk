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


import io.nitric.proto.document.v1.Collection;
import io.nitric.proto.document.v1.Key;
import io.nitric.util.Contracts;

/**
 * Provides an internal API document collection class.
 */
class DocColl {

    final String name;
    final DocKey parent;

    // Constructors ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    DocColl(String name, DocKey parent) {
        Contracts.requireNonBlank(name, "name");

        this.name = name;
        this.parent = parent;
    }

    DocColl(String name) {
        this(name, null);
    }

    // Package Private Methods ------------------------------------------------

    /**
     * @return the new GRPC Collection [name, parent]
     */
    Collection toCollection() {
        var builder = Collection.newBuilder().setName(name);

        if (parent != null) {
            var parentCol = Collection.newBuilder().setName(parent.collection.name).build();
            var parentKey = Key.newBuilder().setId(parent.id).setCollection(parentCol).build();
            builder.setParent(parentKey);
        }

        return builder.build();
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", parent=" + parent + "]";
    }
}