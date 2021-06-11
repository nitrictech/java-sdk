package io.nitric.api.kv;

/*-
 * #%L
 * Nitric Java SDK
 * %%
 * Copyright (C) 2021 Nitric Pty Ltd
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

import io.nitric.proto.kv.v1.KeyValueDeleteRequest;
import io.nitric.util.ProtoUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * Provides a Delete operation class. The Delete operation will remove a value from a collection based the specified
 * key.
 * </p>
 *
 * <p>
 * The example below illustrates a Key Value Delete operation with a <code>"customers"</code> collection and an object
 * mapping to the Java <code>Map</code> class.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.kv.KeyValueClient;
 *  import java.util.Map;
 *  ...
 *
 *  // Create a 'customers' collection KV client
 *  var client = KeyValueClient.build(Map.class, "customers");
 *
 *  // Delete a customer record
 *  client.newDelete()
 *      .key("john.smith@gmail.com")
 *      .delete();
 *  </code></pre>
 *
 * @see KeyValueClient
 */
public class Delete<T> {

    final KeyValueClient.Builder<T> builder;
    final Map<String, Object> key = new HashMap<>();

    /*
     * Enforce builder pattern.
     */
    Delete(KeyValueClient.Builder<T> builder) {
        this.builder = builder;
    }

    /**
     * Set the default key.
     *
     * @param key the key in the collection (required)
     * @return the Delete operation
     */
    public Delete<T> key(String key) {
        Objects.requireNonNull(key, "key parameter is required");

        this.key.put(KeyValueClient.DEFAULT_KEY_NAME, key);
        return this;
    }

    /**
     * Set the default key.
     *
     * @param key the values key in the collection (required)
     * @return the Delete operation
     */
    public Delete<T> key(Number key) {
        Objects.requireNonNull(key, "key parameter is required");

        this.key.put(KeyValueClient.DEFAULT_KEY_NAME, key);
        return this;
    }

    /**
     * Set the custom key name and key value pair.
     *
     * @param key   the custom key name (required)
     * @param value the custom key value (required)
     * @return the Delete operation
     */
    public Delete<T> key(String key, Object value) {
        Objects.requireNonNull(key, "key parameter is required");
        Objects.requireNonNull(value, "value parameter is required");

        this.key.put(key, value);
        return this;
    }

    /**
     * Set the custom key name and key value pairs.
     *
     * @param key1   the custom key 1 name (required)
     * @param value1 the custom key 1 value (required)
     * @param key2   the custom key 2 name (required)
     * @param value2 the custom key 2 value (required)
     * @return the Delete operation
     */
    public Delete<T> key(String key1, Object value1, String key2, Object value2) {
        Objects.requireNonNull(key1, "key1 parameter is required");
        Objects.requireNonNull(value1, "value1 parameter is required");
        Objects.requireNonNull(key2, "key2 parameter is required");
        Objects.requireNonNull(value2, "value2 parameter is required");

        this.key.put(key1, value1);
        this.key.put(key2, value2);
        return this;
    }

    /**
     * Delete the collection value with the specified key.
     */
    public void delete() {
        if (key.isEmpty()) {
            throw new NullPointerException("key parameter is required");
        }

        var keyStruct = ProtoUtils.toStruct(key);

        var request = KeyValueDeleteRequest.newBuilder()
                .setCollection(builder.collection)
                .setKey(keyStruct)
                .build();

        builder.serviceStub.delete(request);
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[builder=" + builder
                + ", key=" + key
                + "]";
    }
}
