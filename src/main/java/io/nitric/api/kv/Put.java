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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nitric.proto.kv.v1.KeyValuePutRequest;
import io.nitric.util.ProtoUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * Provides a Put operation class. The Put operation will insert or update a value in a collection based
 * the specified key.
 * </p>
 *
 * <p>
 * The example below illustrates a Key Value Put operation with a <code>"customers"</code> collection and an object
 * mapping to the Java <code>Map</code> class.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.kv.KeyValueClient;
 *  import java.util.Map;
 *  ...
 *
 *  var client = KeyValueClient.build(Map.class, "customers");
 *
 *  var customer = Map.of("mobile", "0432 321 543");
 *
 *  client.newPut()
 *      .key("john.smith@gmail.com")
 *      .value(customer)
 *      .put();
 *  </code></pre>
 *
 * <p>
 * The example below illustrates single table design Put operation with composite partition key (pk) and sort key (sk).
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.kv.KeyValueClient;
 *  import java.util.Map;
 *  ...
 *
 *  Map customer = ...
 *  Map order = ...
 *
 *  var client = KeyValueClient.build(Map.class, "application");
 *
 *  client.newPut()
 *     .key("pk", "Customer#" + customer.getId())
 *     .key("sk", "Order#" + order.getId())
 *     .value(order)
 *     .put();
 *  </code></pre>
 *
 * @see KeyValueClient
 */
public class Put<T> {

    final KeyValueClient.Builder<T> builder;
    final Map<String, Object> key = new HashMap<>();
    T value;

    /*
     * Enforce builder pattern.
     */
    Put(KeyValueClient.Builder<T> builder) {
        this.builder = builder;
    }

    /**
     * Set the default key value.
     *
     * @param keyValue the key in the collection (required)
     * @return the Put operation
     */
    public Put<T> key(String keyValue) {
        Objects.requireNonNull(keyValue, "keyValue parameter is required");

        this.key.put(KeyValueClient.DEFAULT_KEY_NAME, keyValue);
        return this;
    }

    /**
     * Set the default keyValue.
     *
     * @param keyValue the values key in the collection (required)
     * @return the Put operation
     */
    public Put<T> key(Number keyValue) {
        Objects.requireNonNull(keyValue, "keyValue parameter is required");

        this.key.put(KeyValueClient.DEFAULT_KEY_NAME, keyValue);
        return this;
    }

    /**
     * Set the custom key name and key value pair.
     *
     * @param key   the custom key name (required)
     * @param value the custom key value (required)
     * @return the Put operation
     */
    public Put<T> key(String key, Object value) {
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
     * @return the Put operation
     */
    public Put<T> key(String key1, Object value1, String key2, Object value2) {
        Objects.requireNonNull(key1, "key1 parameter is required");
        Objects.requireNonNull(value1, "value1 parameter is required");
        Objects.requireNonNull(key2, "key2 parameter is required");
        Objects.requireNonNull(value2, "value2 parameter is required");

        this.key.put(key1, value1);
        this.key.put(key2, value2);
        return this;
    }

    /**
     * Set the put operation value.
     *
     * @param value the value (required)
     * @return the Put operation
     */
    public Put<T> value(T value) {
        Objects.requireNonNull(value, "value parameter is required");

        this.value = value;
        return this;
    }

    /**
     * Store the value in the collection with the specified key and value.
     */
    public void put() {
        if (key.isEmpty()) {
            throw new NullPointerException("key parameter is required");
        }
        Objects.requireNonNull(value, "value parameter is required");

        Map valueMap = null;

        if (value instanceof Map) {
            valueMap = (Map) value;

        } else {
            var objectMapper = new ObjectMapper();
            valueMap = objectMapper.convertValue(value, Map.class);
        }

        var keyStruct = ProtoUtils.toStruct(key);
        var valueStruct = ProtoUtils.toStruct(valueMap);

        var requestBuilder = KeyValuePutRequest.newBuilder()
                .setCollection(builder.collection)
                .setKey(keyStruct)
                .setValue(valueStruct);

        var request = requestBuilder.build();

        builder.serviceStub.put(request);
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[builder=" + builder
                + ", key=" + key
                + ", value=" + value
                + "]";
    }
}
