package io.nitric.api.kv;

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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QueryTest {

    @Test
    public void test_where() {
        var builder = KeyValueClient.newBuilder(Account.class);
        var query = new Query<Account>(builder);

        query.where("key", "=", "value");
        assertEquals(1, query.expressions.size());
        var expression = query.expressions.get(0);
        assertEquals("key", expression.operand);
        assertEquals("=", expression.operator);
        assertEquals("value", expression.value);

        query.where("range", ">", "123");
        assertEquals(2, query.expressions.size());
        expression = query.expressions.get(1);
        assertEquals("range", expression.operand);
        assertEquals(">", expression.operator);
        assertEquals("123", expression.value);

        query.expressions.clear();
        try {
            query.where("", "=", "value");
            assertTrue(false);
        } catch (IllegalArgumentException iae) {
            assertEquals("non blank operand parameter is required", iae.getMessage());
        }

        query.expressions.clear();
        try {
            query.where("key", "", "value");
            assertTrue(false);
        } catch (IllegalArgumentException iae) {
            assertEquals("non blank operator parameter is required", iae.getMessage());
        }

        query.expressions.clear();
        try {
            query.where("key", "<", "");
            assertTrue(false);
        } catch (IllegalArgumentException iae) {
            assertEquals("non blank value parameter is required", iae.getMessage());
        }

        query.expressions.clear();
        try {
            query.where("key", "<>", "value");
            assertTrue(false);
        } catch (IllegalArgumentException iae) {
            assertEquals("operator '<>' is not supported", iae.getMessage());
        }
    }

    @Test
    public void test_limit() {
        var builder = KeyValueClient.newBuilder(Account.class);
        var query = new Query<Account>(builder);

        query.limit(100);

        assertEquals(100, query.limit);
    }

}
