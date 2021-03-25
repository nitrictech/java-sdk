package io.nitric.api.kv;

import com.google.protobuf.Struct;
import io.nitric.proto.kv.v1.KeyValueDeleteRequest;
import io.nitric.proto.kv.v1.KeyValueGetRequest;
import io.nitric.proto.kv.v1.KeyValueGrpc;
import io.nitric.proto.kv.v1.KeyValueGrpc.KeyValueBlockingStub;
import io.nitric.proto.kv.v1.KeyValuePutRequest;
import io.nitric.util.GrpcChannelProvider;
import io.nitric.util.NitricException;
import io.nitric.util.ProtoUtils;

import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  Provides a Key Value API client.
 * </p>
 *
 * <p>
 *  The example below illustrates the Key Value API.
 * </p>
 *
 * <pre>
 *  // Create a 'customers' collection KV client
 *  var client = KeyValueClient.newBuilder("customers").build();
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
 * </pre>
 *
 * @since 1.0
 */
public class KeyValueClient {

    final String collection;
    final KeyValueBlockingStub serviceStub;

    /*
     * Enforce builder pattern.
     */
    KeyValueClient(Builder builder) {
        this.collection = builder.collection;
        this.serviceStub = builder.serviceStub;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the collection value for the given key.
     *
     * @param key the values key in the collection (required)
     * @return the collection value for the given key, or null if not found
     */
    public Map<String, Object> get(String key) {
        Objects.requireNonNull(key, "key parameter is required");

        var request = KeyValueGetRequest.newBuilder()
                .setCollection(collection)
                .setKey(key)
                .build();

        var response = serviceStub.get(request);

        if (response.hasValue()) {
            var struct = response.getValue();
            return ProtoUtils.toMap(response.getValue());

        } else {
            return null;
        }
    }

    /**
     * Store the value in the collection with the given key.
     *
     * @param key the values key in the collection (required)
     * @param value the value to store for the given collection and key
     * @return the KV collection value for the given key
     */
    public void put(String key, Map<String, Object> value) {
        Objects.requireNonNull(key, "key parameter is required");
        Objects.requireNonNull(value, "value parameter is required");

        var struct = ProtoUtils.toStruct(value);

        var request = KeyValuePutRequest.newBuilder()
                .setCollection(collection)
                .setKey(key)
                .setValue(struct)
                .build();

        serviceStub.put(request);
    }

    /**
     * Delete the collection value with the given key.
     *
     * @param key the values key in the collection (required)
     */
    public void delete(String key) {
        Objects.requireNonNull(key, "key parameter is required");

        var request = KeyValueDeleteRequest.newBuilder()
                .setCollection(collection)
                .setKey(key)
                .build();

        serviceStub.delete(request);
    }

    /**
     * Create an new KeyValueClient builder for the given collection.
     *
     * @param collection the KV collection (required)
     * @return new KeyValueClient builder
     */
    public static Builder newBuilder(String collection) {
        Objects.requireNonNull(collection, "collection parameter is required");
        return new Builder(collection);
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[collection=" + collection
                + ", serviceStub=" + serviceStub
                + "]";
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a KeyValueClient Builder.
     */
    public static class Builder {

        private String collection;
        private KeyValueBlockingStub serviceStub;

        /*
         * Enforce builder pattern.
         */
        Builder(String collection) {
            this.collection = collection;
        }

        /**
         * Set the GRPC service stub for mock testing.
         *
         * @param serviceStub the GRPC service stub to inject
         * @return the KeyValueClient builder
         */
        Builder serviceStub(KeyValueBlockingStub serviceStub) {
            this.serviceStub = serviceStub;
            return this;
        }

        /**
         * @return build a new KeyValueClient
         */
        public KeyValueClient build() {
            if (serviceStub == null) {
                var channel = GrpcChannelProvider.getChannel();
                this.serviceStub = KeyValueGrpc.newBlockingStub(channel);
            }

            return new KeyValueClient(this);
        }
    }

}