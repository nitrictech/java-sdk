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

import java.io.IOException;
import java.util.UUID;

import common.Example;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.nitric.api.document.Documents;
import io.nitric.faas.Faas;

public class Create {

    public static void main(String[] args) {
        new Faas()
                .http(context -> {
                    try {
                        var json = context.getRequest().getDataAsText();
                        var example = new ObjectMapper().readValue(json, Example.class);
                        var id = UUID.randomUUID().toString();

                        new Documents().collection("examples").doc(id, Example.class).set(example);

                        context.getResponse().text("Created example with ID: %s", id);

                    } catch (IOException ioe) {
                        context.getResponse()
                                .status(500)
                                .text("error: %s", ioe);
                    }

                    return context;
                })
                .start();
    }

}
// [END snippet]