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
 *  byte[] frontData = ...
 *
 *  var storage = new Storage();
 *
 *  // Store an file file
 *  storage.bucket("inspection-images")
 *     .file("582764-front-elevation.jpg")
 *     .write(frontData);
 *
 *  // Retrieve an image file
 *  byte[] sideData = storage.bucket("inspection-images")
 *      .file("582764-side-elevation.jpg")
 *      .read();
 *
 *  // Delete an image file
 *  storage.bucket(bucket)
 *      .file("582764-rear-elevation.jpg")
 *      .delete();
 * </code></pre>
 */
package io.nitric.api.storage;
