/**
 * <p>
 *  Provides the Storage Service API.
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
