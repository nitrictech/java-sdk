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
 *   Provides the Document Service API.
 * </p>
 *
* <p>
 *  The example below illustrates the Documents API.
 * </p>
 *
 * <pre><code class="code">
 * import io.nitric.api.document.Documents;
 * ...
 *
 * // Create a "customers" collection
 * var customers = new Documents().collection("customers");
 *
 * // Get a customer document reference by id
 * var customerRef = customers.doc("anne.smith@example.com");
 *
 * // Get the document references content
 * var contentMap = customerRef.get();
 *
 * // Update customer document content
 * contentMap.put("status", "active");
 * customerRef.set(contentMap);
 *
 * // Delete a customer document
 * customerRef.delete();
 * </code></pre>
 */
package io.nitric.api.document;