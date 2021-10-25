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

// [START snippet]
package function;

import io.nitric.api.NotFoundException;
import io.nitric.api.document.Documents;
import io.nitric.faas.Faas;

public class Delete {

    public static void main(String[] args) {
        new Faas()
                .http(context -> {
                    var paths = context.getRequest().getPath().split("/");
                    var id = paths[paths.length - 1];

                    try {
                        new Documents().collection("examples")
                                .doc(id)
                                .delete();

                        context.getResponse()
                                .text("Removed example: %s", id);

                    } catch (NotFoundException nfe) {
                        context.getResponse()
                                .status(404)
                                .text("Document not found: %s", id);
                    }

                    return context;
                })
                .start();
    }
}
// [END snippet]