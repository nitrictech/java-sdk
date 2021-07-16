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
import io.nitric.proto.storage.v1.StorageReadRequest;
import io.nitric.proto.storage.v1.StorageReadResponse;
import io.nitric.proto.storage.v1.StorageWriteRequest;
import io.nitric.util.Contracts;
import io.nitric.util.ProtoUtils;

/**
 * <p>
 *  Provides a Storage API bucket class.
 * </p>
 *
 * <p>
 *  The example below illustrates the Storage API.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.storage.Storage;
 *  ...
 *
 *  // Create a storage bucket with the name 'inspection-images'
 *  var bucket = Storage.bucket("inspection-images");
 *
 *  // Store an image file
 *  String imageKey = "582764-front-elevation.jpg"
 *  byte[] imageData = ...
 *  bucket.write(imageKey, imageData);
 *
 *  // Load an image file
 *  imageKey = "582764-side-elevation.jpg"
 *  imageData = bucket.read(imageKey, imageData);
 *
 *  // Delete an image file
 *  imageKey = "582764-rear-elevation.jpg"
 *  bucket.delete(imageKey);
 * </code></pre>
 */
public class Bucket {

    final String name;

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    Bucket(String name) {
        Contracts.requireNonBlank(name, "name");
        this.name = name;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return the bucket name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieve an item from a bucket with the given key if it exists.
     *
     * @param key the access key for the storage bucket item (required)
     * @return the storage item data is it exists, or null otherwise
     */
    public byte[] read(String key) {
        Contracts.requireNonBlank(key, "key");

        var request = StorageReadRequest.newBuilder()
                .setBucketName(name)
                .setKey(key)
                .build();

        StorageReadResponse response = null;
        try {
            response = Storage.getServiceStub().read(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw ProtoUtils.mapGrpcError(sre);
        }

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
        Contracts.requireNonBlank(key, "key");
        Contracts.requireNonNull(data, "data");

        var body = ByteString.copyFrom(data);

        var request = StorageWriteRequest.newBuilder()
                .setBucketName(name)
                .setKey(key)
                .setBody(body)
                .build();

        try {
            Storage.getServiceStub().write(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw ProtoUtils.mapGrpcError(sre);
        }
    }

    /**
     * Delete an item from a bucket with the given key if it exists.
     *
     * @param key the key for the bucket item (required)
     */
    public void delete(String key) {
        Contracts.requireNonBlank(key, "key");

        var request = StorageDeleteRequest.newBuilder()
                .setBucketName(name)
                .setKey(key)
                .build();

        try {
            Storage.getServiceStub().delete(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw ProtoUtils.mapGrpcError(sre);
        }
   }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + "]";
    }

}
