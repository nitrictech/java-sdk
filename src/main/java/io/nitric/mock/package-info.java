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
 *  Provides the Mock builders for Nitric Function unit testing.
 * </p>
 *
 * <p>
 *  The example below illustrates the Mock API.
 * </p>
 *
 * <pre><code class="code">
 * import io.nitric.mock.MockTrigger;
 * import org.junit.Test;
 * import java.util.Map;
 *
 * import static org.junit.Assert.*;
 * ...
 *
 * &commat;Test
 * public void test_user_not_found() {
 *
 *     final var expectHeaders = Map.of("Content-Type", "application/json");
 *
 *     var trigger = MockTrigger.newHttpTriggerBuilder()
 *         .setMethod("GET")
 *         .setPath("/user/")
 *         .setQueryParams(Map.of("id", "123456"))
 *         .build();
 *
 *     // Test User Not Found
 *     var response = new GetUserHandler().handle(trigger);
 *     assertNotNull(response);
 *     assertEquals("{ \"message\": \"User '123456' not found\" }", response.getDataAsText());
 *
 *     var httpCtx = response.getContext().asHttp();
 *     assertEquals(404, httpCtx.getStatus());
 *     assertEquals(expectHeaders, httpCtx.getHeaders());
 * }
 * </code></pre>
 */
package io.nitric.mock;

