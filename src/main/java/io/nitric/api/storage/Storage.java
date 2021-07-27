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

import io.nitric.proto.storage.v1.StorageServiceGrpc;
import io.nitric.proto.storage.v1.StorageServiceGrpc.StorageServiceBlockingStub;
import io.nitric.util.Contracts;
import io.nitric.util.GrpcChannelProvider;

/**
 * <p>
 * Provides a Storage API client.
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
 *
 * @see Bucket
 */
public class Storage {

    static StorageServiceBlockingStub serviceStub;

    /*
     * Enforce package builder patterns.
     */
    private Storage() {
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Create a new storage bucket with the given name.
     *
     * @param name the name of the bucket (required)
     * @return a new storage bucket
     */
    public static Bucket bucket(String name) {
        Contracts.requireNonBlank(name, "name");
        return new Bucket(name);
    }

    // Package Private Methods ------------------------------------------------

    static StorageServiceBlockingStub getServiceStub() {
        if (serviceStub == null) {
            var channel = GrpcChannelProvider.getChannel();
            serviceStub = StorageServiceGrpc.newBlockingStub(channel);
        }
        return serviceStub;
    }

    static void setServiceStub(StorageServiceBlockingStub stub) {
        serviceStub = stub;
    }

}
