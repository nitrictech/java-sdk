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

package io.nitric.api.storage;

import com.google.protobuf.ByteString;
import io.nitric.api.NitricException;
import io.nitric.api.NotFoundException;
import io.nitric.proto.storage.v1.StorageDeleteRequest;
import io.nitric.proto.storage.v1.StorageReadRequest;
import io.nitric.proto.storage.v1.StorageReadResponse;
import io.nitric.proto.storage.v1.StorageWriteRequest;
import io.nitric.util.Contracts;

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
 *  byte[] frontData = ...
 *
 *  // Store an file file
 *  Storage.bucket("inspection-images")
 *     .file("582764-front-elevation.jpg")
 *     .write(frontData);
 *
 *  // Retrieve an image file
 *  byte[] sideData = Storage.bucket("inspection-images")
 *      .file("582764-side-elevation.jpg")
 *      .read();
 *
 *  // Delete an image file
 *  Storage.bucket(bucket)
 *      .file("582764-rear-elevation.jpg")
 *      .delete();
 * </code></pre>
 */
public class File {

    final String bucket;
    final String key;

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    File(String bucket, String key) {
        Contracts.requireNonBlank(bucket, "bucket");
        Contracts.requireNonBlank(key, "key");
        this.bucket = bucket;
        this.key = key;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the storage bucket name.
     *
     * @return the bucket name
     */
    public String getBucket() {
        return bucket;
    }

    /**
     * Return the storage file key.
     *
     * @return the file key
     */
    public String getKey() {
        return key;
    }

    /**
     * Retrieve an item from a bucket with the given key if it exists.
     *
     * @return the storage item data is it exists, or null otherwise
     * @throws NotFoundException if the storage bucket or item was not found
     * @throws NitricException if a Storage Service API error occurs
     */
    public byte[] read() throws NotFoundException, NitricException {

        var request = StorageReadRequest.newBuilder()
                .setBucketName(bucket)
                .setKey(key)
                .build();

        StorageReadResponse response = null;
        try {
            response = Storage.getServiceStub().read(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw NitricException.build(sre);
        }

        var body = response.getBody();

        return (!body.isEmpty()) ? body.toByteArray() : null;
    }

    /**
     * Store an item to a bucket with the given key. This operation will perform a create or update depending upon
     * whether an item already exists in the bucket for the given key.
     *
     * @param data the item data
     * @throws NotFoundException if the storage bucket was not found
     * @throws NitricException if a Storage Service API error occurs
     */
    public void write(byte[] data) throws NotFoundException, NitricException {
        Contracts.requireNonBlank(key, "key");
        Contracts.requireNonNull(data, "data");

        var body = ByteString.copyFrom(data);

        var request = StorageWriteRequest.newBuilder()
                .setBucketName(bucket)
                .setKey(key)
                .setBody(body)
                .build();

        try {
            Storage.getServiceStub().write(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw NitricException.build(sre);
        }
    }

    /**
     * Delete an item from a bucket with the given key if it exists.
     *
     * @throws NotFoundException if the storage bucket or item was not found
     * @throws NitricException if a Storage Service API error occurs
     */
    public void delete() throws NotFoundException, NitricException {
        var request = StorageDeleteRequest.newBuilder()
                .setBucketName(bucket)
                .setKey(key)
                .build();

        try {
            Storage.getServiceStub().delete(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw NitricException.build(sre);
        }
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[bucket=" + bucket + ", key=" + key + "]";
    }

}
