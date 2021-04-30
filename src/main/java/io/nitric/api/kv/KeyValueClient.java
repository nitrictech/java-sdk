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
import io.nitric.proto.kv.v1.KeyValueDeleteRequest;
import io.nitric.proto.kv.v1.KeyValueGetRequest;
import io.nitric.proto.kv.v1.KeyValueGrpc;
import io.nitric.proto.kv.v1.KeyValueGrpc.KeyValueBlockingStub;
import io.nitric.proto.kv.v1.KeyValuePutRequest;
import io.nitric.util.GrpcChannelProvider;
import io.nitric.util.ProtoUtils;

import java.security.Key;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  Provides a Key Value API client.
 * </p>
 *
 * <h3>Collections</h3>
 *
 * <p>
 *  The Key Value API supports collections, which is typically used to store similar types of data.
 *  This is a common pattern used in Document stores, and in the SQL relational world this equates to tables.
 * </p>
 *
 * <h3>Object Mapping</h3>
 *
 * <p>
 *  The Key Value Client supports mapping data plain old Java objects (POJO) into and out of the KV service.
 *  POJO classes should follow the Java bean type pattern with public getters and setters for properties and a
 *  default or no-args constructor.
 * </p>
 *
 * <h3>Examples</h3>
 *
 * <p>
 *  The example below illustrates the Key Value API with a 'customers' collection and an object mapping to the Java
 *  <code>Map</code> type.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.kv.KeyValueClient;
 *  ...
 *
 *  // Create a 'customers' collection KV client
 *  var client = KeyValueClient.build(Map.class, "customers");
 *
 *  // Get a customer record
 *  String key = "john.smith@gmail.com";
 *  Map&lt;String, Object&gt; customer = client.get(key);
 *
 *  // Update a customer record
 *  customer.put("mobile", "0432 321 543");
 *  client.put(key, value);
 *
 *  // Delete a customer record
 *  client.delete(key);
 * </code></pre>
 *
 * <p>
 *  The example below illustrates type mapping with a custom POJO class.
 * </p>
 *
 * <pre><code class="code">
 *  package com.example.entity;
 *
 *  public class Account {
 *
 *      private String mobile;
 *      private Boolean active;
 *
 *      public String getMobile() {
 *          return mobile;
 *      }
 *      public void setMobile(String mobile) {
 *          this.mobile = mobile;
 *      }
 *
 *      public Boolean getActive() {
 *          return active;
 *      }
 *      public void setActive(Boolean active) {
 *          this.active = active;
 *      }
 *  }
 *
 *  package com.example.function;
 *
 *  import com.example.entity.Account;
 *
 *  import io.nitric.api.kv.KeyValueClient;
 *  import io.nitric.http.HttpHandler;
 *  import io.nitric.http.HttpRequest;
 *  import io.nitric.http.HttpResponse;
 *
 *  public class AccountFunction implements HttpHandler {
 *
 *      public HttpResponse handle(HttpRequest request) {
 *          // Create a Account KV client for the 'accounts' collection
 *          var client = KeyValueClient.build(Account.class, "accounts");
 *
 *          // Get an account record
 *          String id = request.getParameter("id");
 *          Account account = client.get(id);
 *
 *          // Update an account record
 *          account.setMobile("0432 321 543");
 *          account.setActive(false);
 *
 *          client.put(id, account);
 *
 *          return HttpResponse.build("OK");
 *      }
 *  }
 * </code></pre>
 *
 * <h3>Native Builds</h3>
 *
 * <p>
 *  When building a native function using GraalVM compiler you will need to a reflection configuration file
 *  so that your custom POJO class is supported. To do this simply add the JSON file to your build path:
 * </p>
 *
 * <pre class="code">
 * src/main/resources/META-INF/native-image/reflect-config.json
 * </pre>
 *
 * <p>
 *  Include the following information for the compiler to enable object mapping by the Jackson data binding library.
 * </p>
 *
 * <pre class="code">
 * [
 *    {
 *       "name" : "com.example.entity.Account",
 *       "allDeclaredConstructors" : true,
 *       "allPublicConstructors" : true,
 *       "allDeclaredMethods" : true,
 *       "allPublicMethods" : true,
 *       "allDeclaredFields" : true,
 *       "allPublicFields" : true
 *    }
 * ]
 * </pre>
 *
 * <p>
 *  If you forget to do this, you may get a runtime error like this:
 * </p>
 *
 * <pre class="code">
 * java.lang.IllegalArgumentException: No serializer found for class com.example.entity.Account and no properties ...
 *         at com.fasterxml.jackson.databind.ObjectMapper._convert(ObjectMapper.java:4314)
 * </pre>
 *
 * @since 1.0
 */
public class KeyValueClient<T> {

    final String collection;
    final Class<T> type;
    final KeyValueBlockingStub serviceStub;

    /*
     * Enforce builder pattern.
     */
    KeyValueClient(Builder builder) {
        this.collection = builder.collection;
        this.type = builder.type;
        this.serviceStub = builder.serviceStub;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the collection value for the given key.
     *
     * @param key the values key in the collection (required)
     * @return the collection value for the given key, or null if not found
     */
    public T get(Object key) {
        Objects.requireNonNull(key, "key parameter is required");

        var request = KeyValueGetRequest.newBuilder()
                .setCollection(collection)
                .setKey(key.toString())
                .build();

        var response = serviceStub.get(request);

        if (response.hasValue()) {
            var struct = response.getValue();
            Map map = ProtoUtils.toMap(response.getValue());

            if (map.getClass().isAssignableFrom(type)) {
                return (T) map;

            } else {
                var objectMapper = new ObjectMapper();
                return (T) objectMapper.convertValue(map, type);
            }

        } else {
            return null;
        }
    }

    /**
     * Store the value in the collection with the given key.
     *
     * @param key the values key in the collection (required)
     * @param value the value to store for the given collection and key
     */
    public void put(Object key, T value) {
        Objects.requireNonNull(key, "key parameter is required");
        Objects.requireNonNull(value, "value parameter is required");

        Map valueMap = null;

        if (value instanceof Map) {
            valueMap = (Map) value;

        } else {
            var objectMapper = new ObjectMapper();
            valueMap = objectMapper.convertValue(value, Map.class);
        }

        var struct = ProtoUtils.toStruct(valueMap);

        var request = KeyValuePutRequest.newBuilder()
                .setCollection(collection)
                .setKey(key.toString())
                .setValue(struct)
                .build();

        serviceStub.put(request);
    }

    /**
     * Delete the collection value with the given key.
     *
     * @param key the values key in the collection (required)
     */
    public void delete(Object key) {
        Objects.requireNonNull(key, "key parameter is required");

        var request = KeyValueDeleteRequest.newBuilder()
                .setCollection(collection)
                .setKey(key.toString())
                .build();

        serviceStub.delete(request);
    }

    /**
     * Return a new KeyValueClient Builder for the given type.
     *
     * @param <T> the key value collection object type
     * @param type the type object mapping type (required)
     * @return new KeyValueClient builder
     */
    public static <T> Builder<T> newBuilder(Class<T> type) {
        Objects.requireNonNull(type, "type parameter is required");
        return new Builder<T>(type);
    }

    /**
     * Return a new KeyValueClient for the given type and collection.
     *
     * @param <T> the key value collection object type
     * @param type the object mapping type (required)
     * @param collection the name of the collection (required)
     * @return new KeyValueClient builder
     */
    public static <T> KeyValueClient<T> build(Class<T> type, String collection) {
        return newBuilder(type)
                .collection(collection)
                .build();
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[collection=" + collection
                + ", type=" + type
                + ", serviceStub=" + serviceStub
                + "]";
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a KeyValueClient Builder.
     */
    public static class Builder<K> {

        String collection;
        Class<K> type;
        KeyValueBlockingStub serviceStub;

        /*
         * Enforce builder pattern.
         */
        Builder(Class<K> type) {
            this.type = type;
        }

        /**
         * Set the collection name.
         *
         * @param collection the collection name (required)
         * @return the builder object
         */
        public Builder<K> collection(String collection) {
            this.collection = collection;
            return this;
        }

        /**
         * Set the GRPC service stub.
         *
         * @param serviceStub the GRPC service stub to inject
         * @return the builder object
         */
        public Builder<K> serviceStub(KeyValueBlockingStub serviceStub) {
            this.serviceStub = serviceStub;
            return this;
        }

        /**
         * @return build a new KeyValueClient
         */
        public KeyValueClient<K> build() {
            Objects.requireNonNull(collection, "collection parameter is required");
            if (serviceStub == null) {
                var channel = GrpcChannelProvider.getChannel();
                this.serviceStub = KeyValueGrpc.newBlockingStub(channel);
            }

            return new KeyValueClient(this);
        }
    }

}
