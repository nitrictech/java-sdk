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

package examples.faas;

// [START import]
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nitric.faas.Faas;

import java.io.IOException;
import java.util.Map;
// [END import]

public class Events {

    public static void main(String[] args) {
        // [START snippet]
        new Faas()
            .event(context -> {
                try {
                    var json = context.getRequest().getDataAsText();
                    var event = new ObjectMapper().readValue(json, Map.class);

                    System.out.printf("Received nitric event: %s \n", event);

                    context.getResponse().success(true);

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    context.getResponse().success(false);
                }

                return context;
            })
            .start();
        // [END snippet]
    }
}
