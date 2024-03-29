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

package io.nitric.api.document;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import io.nitric.api.document.model.Customer;
import io.nitric.api.document.model.Order;
import io.nitric.proto.document.v1.DocumentServiceGrpc;

/**
 * Provides Documents test case.
 */
@ExtendWith(MockitoExtension.class)
public class DocumentsTest {

    @Test
    public void test_serviceStub() {
        Documents.setServiceStub(null);
        assertNotNull(Documents.getServiceStub());

        var mock = Mockito.mock(DocumentServiceGrpc.DocumentServiceBlockingStub.class);

        Documents.setServiceStub(mock);
        assertEquals(mock, Documents.getServiceStub());
    }

    @Test
    public void test_collection() {
        var mapColl = new Documents().collection("customers");

        assertEquals("customers", mapColl.getName());
        assertNull(mapColl.getParent());

        assertEquals("Collection[name=customers, parent=null]", mapColl.toString());
    }

    @Test
    public void test_collection_doc() {
        var mapDoc = new Documents().collection("customers")
                .doc("id");

        assertEquals("customers", mapDoc.key.collection.name);
        assertEquals(Map.class, mapDoc.type);
        assertEquals("id", mapDoc.getKey().getId());

        var custDoc = new Documents().collection("customers")
                .doc("id", Customer.class);

        assertEquals("customers", custDoc.key.collection.name);
        assertEquals(Customer.class, custDoc.type);
        assertEquals("id", custDoc.getKey().getId());
    }

    @Test
    public void test_collection_query() {
        var mapQuery = new Documents().collection("customers")
                .query();

        assertEquals(Map.class, mapQuery.type);
        assertEquals("customers", mapQuery.collection.getName());

        var custQuery = new Documents().collection("customers")
                .query(Customer.class);

        assertEquals(Customer.class, custQuery.type);
        assertEquals("customers", custQuery.collection.getName());
    }

    @Test
    public void test_collection_collection() {
        var subcoll = new Documents().collection("customers")
                .collection("orders");

        assertEquals("customers", subcoll.parent.collection.name);
        assertEquals("orders", subcoll.name);
    }

    @Test
    public void test_collection_doc_query() {
        var mapQuery = new Documents().collection("customers")
                .doc("customers:id")
                .query("orders");

        assertEquals("customers", mapQuery.collection.getParent().getCollection().getName());
        assertEquals("customers:id", mapQuery.collection.getParent().getId());
        assertEquals("orders", mapQuery.collection.getName());
        assertEquals(Map.class, mapQuery.type);

        var orderQuery = new Documents().collection("customers")
                .doc("customers:id")
                .query("orders", Order.class);

        assertEquals("customers", orderQuery.collection.getParent().getCollection().getName());
        assertEquals("customers:id", orderQuery.collection.getParent().getId());
        assertEquals("orders", orderQuery.collection.getName());
        assertEquals(Order.class, orderQuery.type);
    }

    @Test
    public void test_collection_doc_collection() {
        var subcoll = new Documents().collection("customers")
                .doc("customers:id")
                .collection("orders");

        assertEquals("customers", subcoll.parent.collection.name);
        assertEquals("customers:id", subcoll.parent.id);
        assertEquals("orders", subcoll.name);

        subcoll = new Documents().collection("customers")
                .doc("customers:id", Map.class)
                .collection("orders");

        assertEquals("customers", subcoll.parent.collection.name);
        assertEquals("customers:id", subcoll.parent.id);
        assertEquals("orders", subcoll.name);
    }

    @Test
    public void test_collection_collection_query() {
        var mapQuery = new Documents().collection("customers")
                .collection("orders")
                .query();

        assertEquals("customers", mapQuery.collection.getParent().getCollection().getName());
        assertEquals("orders", mapQuery.collection.getName());
        assertEquals(Map.class, mapQuery.type);

        var orderQuery = new Documents().collection("customers")
                .collection("orders")
                .query(Order.class);

        assertEquals("customers", orderQuery.collection.getParent().getCollection().getName());
        assertEquals("orders", orderQuery.collection.getName());
        assertEquals(Order.class, orderQuery.type);
    }

    @Test
    public void test_collection_doc_collection_doc() {
        var mapDoc = new Documents().collection("customers")
                .doc("customers:id")
                .collection("orders")
                .doc("orders:id");

        assertEquals("customers", mapDoc.key.collection.parent.collection.name);
        assertEquals("customers:id", mapDoc.key.collection.parent.id);
        assertEquals("orders", mapDoc.key.collection.name);
        assertEquals("orders:id", mapDoc.key.id);
        assertEquals(Map.class, mapDoc.type);

        var orderDoc = new Documents().collection("customers")
                .doc("customers:id")
                .collection("orders")
                .doc("orders:id", Order.class);

        assertEquals("customers", orderDoc.key.collection.parent.collection.name);
        assertEquals("customers:id", orderDoc.key.collection.parent.id);
        assertEquals("orders", orderDoc.key.collection.name);
        assertEquals("orders:id", orderDoc.key.id);
        assertEquals(Order.class, orderDoc.type);
    }

    @Test
    public void test_collection_doc_subcollection_query() {
        var mapQuery = new Documents().collection("customers")
                .doc("customers:id")
                .collection("orders")
                .query();

        assertEquals("customers", mapQuery.collection.getParent().getCollection().getName());
        assertEquals("customers:id", mapQuery.collection.getParent().getId());
        assertEquals("orders", mapQuery.collection.getName());
        assertEquals(Map.class, mapQuery.type);

        var orderQuery = new Documents().collection("customers")
                .doc("customers:id")
                .collection("orders")
                .query(Order.class);

        assertEquals("customers", orderQuery.collection.getParent().getCollection().getName());
        assertEquals("customers:id", orderQuery.collection.getParent().getId());
        assertEquals("orders", orderQuery.collection.getName());
        assertEquals(Order.class, orderQuery.type);
    }

    @Test
    public void test_collection_doc_collection_doc_query() {

        var orderDoc = new Documents().collection("customers")
                .doc("customers:id")
                .collection("orders")
                .doc("orders:id", Order.class);

        try {
            orderDoc.query("payments");
            fail();
        } catch (IllegalArgumentException iae) {
            assertTrue(iae.getMessage().startsWith("Max collection depth 1 exceeded"));
        }

        try {
            orderDoc.query("payments", Map.class);
            fail();
        } catch (IllegalArgumentException iae) {
            assertTrue(iae.getMessage().startsWith("Max collection depth 1 exceeded"));
        }
    }

}
