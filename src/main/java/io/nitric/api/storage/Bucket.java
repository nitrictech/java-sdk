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
 *  byte[] imageData = ...
 *
 *  // Store a file: 'inspection-images/582764-front-elevation.jpg'
 *  Storage.bucket("inspection-images")
 *     .file("582764-front-elevation.jpg")
 *     .write(imageData);
 *
 *  // Load an image file
 *  imageData = Storage.bucket("inspection-images")
 *      .file("582764-side-elevation.jpg")
 *      .read();
 *
 *  // Delete an image file
 *  Storage.bucket(bucket)
 *      .file("582764-rear-elevation.jpg")
 *      .delete();
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
     * Return the bucket name.
     *
     * @return the bucket name
     */
    public String getName() {
        return name;
    }

    /**
     * Create a storage file object with the given key.
     *
     * @param key the storage file key (required)
     * @return a new storage file object
     */
    public File file(String key) {
        Contracts.requireNonBlank(key, "key");
        return new File(this.name, key);
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + "]";
    }

}
