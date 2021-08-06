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

import java.util.Map;

/**
 * <p>
 *  Provides an abstract Collection class.
 * </p>
 */
public class AbstractCollection {

    final String name;
    final Key parent;

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    AbstractCollection(String name, Key parent) {
        Contracts.requireNonBlank(name, "name");

        this.name = name;
        this.parent = parent;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the collection name.
     *
     * @return the collection name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the collection parent key.
     *
     * @return the collection parent key
     */
    public Key getParent() {
        return parent;
    }

    /**
     * Create a new query object with the <code>Map</code> value type.
     *
     * @return a new query object
     */
    public Query<Map> query() {
        return new Query<>(toGrpcCollection(), Map.class);
    }

    /**
     * Create a new query object with the given value type.
     *
     * @param type the query value type (required)
     * @return a new query object
     */
    public <T> Query<T> query(Class<T> type) {
        Contracts.requireNonNull(type, "type");

        return new Query<>(toGrpcCollection(), type);
    }

    /**
     * Return this string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", parent=" + parent + "]";
    }

    // Package Private Methods ------------------------------------------------

    io.nitric.proto.document.v1.Collection toGrpcCollection() {
        var builder = io.nitric.proto.document.v1.Collection
                .newBuilder()
                .setName(name);

        if (parent != null) {
            var parentCol = io.nitric.proto.document.v1.Collection
                    .newBuilder()
                    .setName(parent.collection.name)
                    .build();

            var parentKey = io.nitric.proto.document.v1.Key
                    .newBuilder()
                    .setId(parent.id)
                    .setCollection(parentCol)
                    .build();

            builder.setParent(parentKey);
        }

        return builder.build();
    }

}