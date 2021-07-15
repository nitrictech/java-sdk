package io.nitric.api.storage;

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

import com.google.protobuf.ByteString;
import io.nitric.proto.storage.v1.StorageDeleteRequest;
import io.nitric.proto.storage.v1.StorageServiceGrpc;
import io.nitric.proto.storage.v1.StorageReadRequest;
import io.nitric.proto.storage.v1.StorageWriteRequest;
import io.nitric.util.GrpcChannelProvider;

import java.util.Objects;

/**
 * <p>
 *  Provides a Storage API client.
 * </p>
 *
 * <p>
 *  The example below illustrates the Storage API.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.storage.StorageClient;
 *  ...
 *
 *  // Create a storage client with the bucket name 'inspection-images'
 *  var client = StorageClient.build("inspection-images");
 *
 *  // Store an image file
 *  String imageKey = "582764-front-elevation.jpg"
 *  byte[] imageData = ...
 *  client.write(imageKey, imageData);
 *  ...
 *
 *  // Load an image file
 *  imageKey = "582764-side-elevation.jpg"
 *  imageData = client.read(imageKey, imageData);
 *  ...
 *
 *  // Delete an image file
 *  imageKey = "582764-rear-elevation.jpg"
 *  client.delete(imageKey);
 * </code></pre>
 *
 * @since 1.0
 */
public class StorageClient {

    final String bucket;
    final StorageServiceGrpc.StorageServiceBlockingStub serviceStub;

    /*
     * Enforce builder pattern.
     */
    StorageClient(StorageClient.Builder builder) {
        this.bucket = builder.bucket;
        this.serviceStub = builder.serviceStub;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Retrieve an item from a bucket with the given key if it exists.
     *
     * @param key the access key for the storage bucket item (required)
     * @return the storage item data is it exists, or null otherwise
     */
    public byte[] read(String key) {
        Objects.requireNonNull(key, "key parameter is required");

        var request = StorageReadRequest.newBuilder()
                .setBucketName(bucket)
                .setKey(key)
                .build();

        var response = serviceStub.read(request);

        var body = response.getBody();

        return (!body.isEmpty()) ? body.toByteArray() : null;
    }

    /**
     * Store an item to a bucket with the given key. This operation will perform a create or update depending upon
     * whether an item already exists in the bucket for the given key.
     *
     * @param key the key for the bucket item (required)
     * @param data the item data
     */
    public void write(String key, byte[] data) {
        Objects.requireNonNull(key, "key parameter is required");
        Objects.requireNonNull(data, "data parameter is required");

        var body = ByteString.copyFrom(data);

        var request = StorageWriteRequest.newBuilder()
                .setBucketName(bucket)
                .setKey(key)
                .setBody(body)
                .build();

        serviceStub.write(request);
    }

    /**
     * Delete an item from a bucket with the given key if it exists.
     *
     * @param key the key for the bucket item (required)
     */
    public void delete(String key) {
        Objects.requireNonNull(key, "key parameter is required");

        var request = StorageDeleteRequest.newBuilder()
                .setBucketName(bucket)
                .setKey(key)
                .build();

        serviceStub.delete(request);
    }

    /**
     * Create an new StorageClient builder.
     *
     * @return new StorageClient builder
     */
    public static StorageClient.Builder newBuilder() {
        return new StorageClient.Builder();
    }

    /**
     * Return a new StorageClient with the specified bucket name.
     *
     * @param bucket the bucket name (required)
     * @return a new StorageClient with the specified bucket name
     */
    public static StorageClient build(String bucket) {
        return newBuilder().bucket(bucket).build();
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[bucket=" + bucket + ", serviceStub=" + serviceStub + "]";
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a StorageClient Builder.
     */
    public static class Builder {

        String bucket;
        StorageServiceGrpc.StorageServiceBlockingStub serviceStub;

        /*
         * Enforce builder pattern.
         */
        Builder() {
        }

        /**
         * Set the bucket name.
         *
         * @param bucket the bucket name (required)
         * @return the builder object
         */
        public StorageClient.Builder bucket(String bucket) {
            this.bucket = bucket;
            return this;
        }

        /**
         * Set  the GRPC service stub.
         *
         * @param serviceStub the GRPC service stub to inject
         * @return the builder object
         */
        public StorageClient.Builder serviceStub(StorageServiceGrpc.StorageServiceBlockingStub serviceStub) {
            this.serviceStub = serviceStub;
            return this;
        }

        /**
         * @return build a new StorageClient
         */
        public StorageClient build() {
            Objects.requireNonNull(bucket, "bucket parameter is required");
            if (serviceStub == null) {
                var channel = GrpcChannelProvider.getChannel();
                this.serviceStub = StorageServiceGrpc.newBlockingStub(channel);
            }

            return new StorageClient(this);
        }
    }
}
