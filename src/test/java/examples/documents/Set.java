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

package examples.documents;
// [START import]
import io.nitric.api.document.Documents;
import java.util.Map;
// [END import]

public class Set {
    public static void Example() {
        // [START snippet]
        var document = Documents.collection("products").doc("nitric");

        document.set(Map.of(
            "id", "nitric",
            "name", "Nitric Framework",
            "description", "A development framework"
        ));
        // [END snippet]
    }
}