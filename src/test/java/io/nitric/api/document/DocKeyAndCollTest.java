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
public class DocKeyAndCollTest {

    @Test
    public void test_init() {
        var usersCol = new DocColl("users");
        assertEquals("users", usersCol.name);
        assertNull(usersCol.parent);

        var userKey = new DocKey(usersCol, "user@server.com");
        assertNotNull(userKey.collection);
        assertEquals("users", userKey.collection.name);
        assertNull(userKey.collection.parent);
        assertEquals("user@server.com", userKey.id);

        assertEquals("DocKey[collection=DocColl[name=users, parent=null], id=user@server.com]" , userKey.toString());

        var parentKey = new DocKey(new DocColl("customers"), "customers:123");
        assertEquals("DocKey[collection=DocColl[name=customers, parent=null], id=customers:123]" , parentKey.toString());

        var subCol = new DocColl("orders", parentKey);
        assertEquals("orders", subCol.name);
        assertNotNull(subCol.parent);
        assertEquals("customers", subCol.parent.collection.name);
        assertEquals("customers:123", subCol.parent.id);
        assertNull(subCol.parent.collection.parent);
        assertEquals("DocColl[name=orders, parent=DocKey[collection=DocColl[name=customers, parent=null], id=customers:123]]" , subCol.toString());
    }

    @Test
    public void test_toCollection_toKey() {
        var usersCol = new DocColl("users");
        var col = usersCol.toCollection();
        assertNotNull(col);
        assertEquals("users", col.getName());
        assertFalse(col.hasParent());

        var userKey = new DocKey(usersCol, "user@server.com");
        var key = userKey.toKey();

        assertNotNull(key);
        assertNotNull(key.getCollection());
        assertEquals(key.getCollection().getName(), "users");
        assertEquals(key.getId(), "user@server.com");
        assertFalse(key.getCollection().hasParent());

        var parentKey = new DocKey(new DocColl("customers"), "customers:123");
        var subCol = new DocColl("orders", parentKey);

        var subcollection = subCol.toCollection();
        assertNotNull(subcollection);
        assertEquals("orders", subcollection.getName());
        assertTrue(subcollection.hasParent());
        assertTrue(subcollection.getParent().hasCollection());
        assertEquals("customers", subcollection.getParent().getCollection().getName());
        assertEquals("customers:123", subcollection.getParent().getId());
        assertFalse(subcollection.getParent().getCollection().hasParent());
    }

}
