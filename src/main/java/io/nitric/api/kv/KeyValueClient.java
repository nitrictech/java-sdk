package io.nitric.api.kv;

import com.google.protobuf.Struct;
import io.nitric.proto.kv.v1.KeyValueDeleteRequest;
import io.nitric.proto.kv.v1.KeyValueGetRequest;
import io.nitric.proto.kv.v1.KeyValueGrpc;
import io.nitric.proto.kv.v1.KeyValueGrpc.KeyValueBlockingStub;
import io.nitric.proto.kv.v1.KeyValuePutRequest;
import io.nitric.util.GrpcChannelProvider;
import io.nitric.util.NitricException;

import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Provides a Nitric Key Value API Client class.
 *
 * @since 1.0
 */
public class KeyValueClient<T> {

    final String collection;
    final Class<T> type;
    final KeyValueBlockingStub kvStub;

    /*
     * Provides a package private constructor to enforce builder pattern.
     */
    KeyValueClient(Builder builder) {
        this.collection = builder.collection;
        this.type = builder.type;
        this.kvStub = builder.kvStub;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the collection value for the given key.
     *
     * @param key the values key in the collection (required)
     * @return the KV collection value for the given key
     */
    public T get(String key) {
        Objects.requireNonNull(key, "key parameter is null");

        var request = KeyValueGetRequest.newBuilder()
                .setCollection(collection)
                .setKey(key)
                .build();

        var response = kvStub.get(request);

        // https://www.programcreek.com/java-api-examples/?api=com.google.protobuf.Struct
        // https://www.programcreek.com/java-api-examples/?code=google%2Frejoiner%2Frejoiner-master%2Frejoiner-grpc%2Fsrc%2Fmain%2Fjava%2Fcom%2Fgoogle%2Fapi%2Fgraphql%2Fgrpc%2FStructUtil.java#

        // TODO: coerce value
        return (T)  response.getValue();
    }

    /**
     * Store the value in the collection with the given key.
     *
     * @param key the values key in the collection (required)
     * @param value the value to store for the given collection and key
     * @return the KV collection value for the given key
     */
    public void put(String key, Object value) {
        Objects.requireNonNull(key, "key parameter is null");
        Objects.requireNonNull(key, "value parameter is null");

        try {
            var data = value.toString().getBytes(StandardCharsets.UTF_8);
            var struc = Struct.newBuilder().mergeFrom(data).build();

            var request = KeyValuePutRequest.newBuilder()
                    .setCollection(collection)
                    .setKey(key)
                    .setValue(struc)
                    .build();

            var response = kvStub.put(request);

        } catch (com.google.protobuf.InvalidProtocolBufferException ipbe) {
            throw NitricException.newBuilder()
                    .cause(ipbe)
                    .build();
        }
    }

    /**
     * Delete the collection value with the given key.
     *
     * @param key the values key in the collection (required)
     * @return the KV collection value for the given key
     */
    public void delete(String key) {
        Objects.requireNonNull(collection, "null collection parameter");
        Objects.requireNonNull(key, "null key parameter");

        var request = KeyValueDeleteRequest.newBuilder()
                .setCollection(collection)
                .setKey(key)
                .build();

        var response = kvStub.delete(request);
    }

    /**
     * Create an new KeyValueClient builder with the given value type.
     *
     * @param type the collection value type (required)
     * @return new KeyValueClient builder
     */
    public static <T> Builder<T> newBuilder(Class<T> type) {
        Objects.requireNonNull(type, "type parameter is null");
        return new Builder<T>(type);
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[collection=" + collection
                + ", type=" + type.getName()
                + ", kvStub=" + kvStub
                + "]";
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a KeyValueClient Builder.
     */
    public static class Builder<K> {

        private String collection;
        private Class<K> type;
        private KeyValueBlockingStub kvStub;

        /**
         * Create a KeyValueClient builder with the given collection value type.
         * @param type the collection value type
         */
        Builder(Class<K> type) {
            this.type = type;
        }

        /**
         * Set the KV collection name.
         *
         * @param collection the KV collection name
         * @return the KeyValueClient builder
         */
        public Builder<K> collection(String collection) {
            this.collection = collection;
            return this;
        }

        /**
         * @return build a new KeyValueClient
         */
        public KeyValueClient<K> build() {
            var channel = GrpcChannelProvider.getChannel();
            this.kvStub = KeyValueGrpc.newBlockingStub(channel);

            return new KeyValueClient<>(this);
        }
    }

}