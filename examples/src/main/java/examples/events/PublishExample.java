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

package examples.events;
// [START import]
import io.nitric.faas.Faas;
import io.nitric.faas.http.HttpContext;
import io.nitric.faas.http.HttpHandler;
import io.nitric.api.event.Events;
import io.nitric.api.event.Event;
import java.util.Map;
// [END import]

// [START snippet]
public class PublishExample implements HttpHandler {

    @Override
    public HttpContext handle(HttpContext ctx) {
        var topic = new Events().topic("my-topic");
        topic.publish(Event.build(Map.of("Content", "of event")));

        ctx.getResponse().data("Successfully published message");

        return ctx;
    }

    public static void main(String[] args) {
        new Faas()
            .http(new PublishExample())
            .start();
    }
}
// [END snippet]