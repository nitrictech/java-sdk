/*-
 * #%L
 * Nitric Java SDK
 * %%
 * Copyright (C) 2021 Nitric Pty Ltd
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

package io.nitric.api.document;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Provide a query test case.
 */
public class ResultDocTest {

    @Test
    public void test_init() {
        var key = new Key(new Collection("collection", null), "id");
        var resultDoc = new ResultDoc<String>(key, "value");
        assertEquals(key, resultDoc.getKey());
        assertEquals("value", resultDoc.getContent());
        assertEquals("ResultDoc[key=Key[collection=Collection[name=collection, parent=null], id=id], content=value]", resultDoc.toString());

        var resultDoc2 = new ResultDoc<String>("value");
        assertNull(resultDoc2.getKey());
        assertEquals("value", resultDoc2.getContent());
        assertEquals("ResultDoc[key=null, content=value]", resultDoc2.toString());
    }
}
