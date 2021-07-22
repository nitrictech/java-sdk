package io.nitric.api.document;

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

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Provides DocKey and DocColl test case.
 */
public class KeyAndCollectionTest {

    @Test
    public void test_init() {
        var usersCol = new Collection("users", null);
        assertEquals("users", usersCol.getName());
        assertNull(usersCol.getParent());

        var userKey = new Key(usersCol, "user@server.com");
        assertNotNull(userKey.getCollection());
        assertEquals("users", userKey.getCollection().getName());
        assertNull(userKey.getCollection().getParent());
        assertEquals("user@server.com", userKey.getId());
        assertEquals("Key[collection=Collection[name=users, parent=null], id=user@server.com]",
                userKey.toString());

        var parentKey = new Key(new Collection("customers", null), "customers:123");

        var subCol = new Collection("orders", parentKey);
        assertEquals("orders", subCol.getName());
        assertNotNull(subCol.getParent());
        assertEquals("customers", subCol.getParent().getCollection().getName());
        assertEquals("customers:123", subCol.getParent().getId());
        assertNull(subCol.getParent().getCollection().getParent());
    }

    @Test
    public void test_toCollection_toKey() {
        var usersCol = new Collection("users", null);
        var col = usersCol.toGrpcCollection();
        assertNotNull(col);
        assertEquals("users", col.getName());
        assertFalse(col.hasParent());

        var userKey = new Key(usersCol, "user@server.com");
        var key = userKey.toGrpcKey();

        assertNotNull(key);
        assertNotNull(key.getCollection());
        assertEquals(key.getCollection().getName(), "users");
        assertEquals(key.getId(), "user@server.com");
        assertFalse(key.getCollection().hasParent());

        var parentKey = new Key(new Collection("customers", null), "customers:123");
        var subCol = new Collection("orders", parentKey);

        var subcollection = subCol.toGrpcCollection();
        assertNotNull(subcollection);
        assertEquals("orders", subcollection.getName());
        assertTrue(subcollection.hasParent());
        assertTrue(subcollection.getParent().hasCollection());
        assertEquals("customers", subcollection.getParent().getCollection().getName());
        assertEquals("customers:123", subcollection.getParent().getId());
        assertFalse(subcollection.getParent().getCollection().hasParent());
    }

}