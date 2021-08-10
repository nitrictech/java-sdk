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

package io.nitric.examples;
// [START import]
import io.nitric.api.document.Documents;
// [END import]

class DocumentsPagedResults {
    public static void PagedResults() {
        // [START snippet]
        var query = Documents.collection("Customers")
        .query()
        .where("active", "==", true)
        .limit(100);

        // Fetch first page
        var results = query.fetch();

        // Fetch next page
        results = query.pagingFrom(results.getPagingToken()).fetch();
        // [END snippet]
    }
}