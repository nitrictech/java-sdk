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

import io.nitric.api.document.model.Customer;
import io.nitric.api.document.model.Order;
import io.nitric.proto.document.v1.DocumentServiceGrpc;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Provides Documents test case.
 */
public class DocumentsTest {

    @Test
    public void useCase_serviceStub() {
        assertNotNull(Documents.getServiceStub());

        var mock = Mockito.mock(DocumentServiceGrpc.DocumentServiceBlockingStub.class);

        Documents.setServiceStub(mock);
        assertEquals(mock, Documents.getServiceStub());
    }

    // collection... [doc, query, subCollection]

    @Test
    public void useCase_collection() {
        var mapColl = Documents.collection("customers");

        assertEquals("customers", mapColl.collection.name);
        assertEquals("Collection[collection=DocColl[name=customers, parent=null]]", mapColl.toString());
    }

    @Test
    public void useCase_collection_doc() {
        var mapDoc = Documents.collection("customers")
                .doc("id");

        assertEquals("customers", mapDoc.key.collection.name);
        assertEquals(Map.class, mapDoc.type);
        assertEquals("id", mapDoc.id());
        assertEquals("CollectionDocRef[key=DocKey[collection=DocColl[name=customers, parent=null], id=id], type=interface java.util.Map]",
                mapDoc.toString());

        var custDoc = Documents.collection("customers")
                .doc("id", Customer.class);

        assertEquals("customers", custDoc.key.collection.name);
        assertEquals(Customer.class, custDoc.type);
        assertEquals("id", custDoc.id());
    }

    @Test
    public void useCase_collection_query() {
        var mapQuery = Documents.collection("customers")
                .query();

        assertEquals(Map.class, mapQuery.type);
        assertEquals("customers", mapQuery.collection.name);
        assertEquals("Query[collection=DocColl[name=customers, parent=null], expressions=[], limit=0, pagingToken=null, type=interface java.util.Map]",
                     mapQuery.toString());

        var custQuery = Documents.collection("customers")
                .query(Customer.class);

        assertEquals(Customer.class, custQuery.type);
        assertEquals("customers", custQuery.collection.name);
    }

    @Test
    public void useCase_collection_collection() {
        var subcoll = Documents.collection("customers")
                .collection("orders");

        assertEquals("customers", subcoll.collection.parent.collection.name);
        assertEquals("orders", subcoll.collection.name);
        assertEquals("SubCollection[collection=DocColl[name=orders, parent=DocKey[collection=DocColl[name=customers, parent=null], id=]]]",
                     subcoll.toString());
    }

    // collection/doc... [query, subCollection]

    @Test
    public void userCase_collection_doc_query() {
        var mapQuery = Documents.collection("customers")
                .doc("customers:id")
                .query("orders");

        assertEquals("customers", mapQuery.collection.parent.collection.name);
        assertEquals("customers:id", mapQuery.collection.parent.id);
        assertEquals("orders", mapQuery.collection.name);
        assertEquals(Map.class, mapQuery.type);
        assertEquals("Query[collection=DocColl[name=orders, parent=DocKey[collection=DocColl[name=customers, parent=null], id=customers:id]], expressions=[], limit=0, pagingToken=null, type=interface java.util.Map]",
                     mapQuery.toString());

        var orderQuery = Documents.collection("customers")
                .doc("customers:id")
                .query("orders", Order.class);

        assertEquals("customers", orderQuery.collection.parent.collection.name);
        assertEquals("customers:id", orderQuery.collection.parent.id);
        assertEquals("orders", orderQuery.collection.name);
        assertEquals(Order.class, orderQuery.type);
    }

    @Test
    public void useCase_collection_doc_collection() {
        var subcoll = Documents.collection("customers")
                .doc("customers:id")
                .collection("orders");

        assertEquals("customers", subcoll.collection.parent.collection.name);
        assertEquals("customers:id", subcoll.collection.parent.id);
        assertEquals("orders", subcoll.collection.name);
        assertEquals("SubCollection[collection=DocColl[name=orders, parent=DocKey[collection=DocColl[name=customers, parent=null], id=customers:id]]]",
                     subcoll.toString());

        subcoll = Documents.collection("customers")
                .doc("customers:id", Map.class)
                .collection("orders");

        assertEquals("customers", subcoll.collection.parent.collection.name);
        assertEquals("customers:id", subcoll.collection.parent.id);
        assertEquals("orders", subcoll.collection.name);
        assertEquals("SubCollection[collection=DocColl[name=orders, parent=DocKey[collection=DocColl[name=customers, parent=null], id=customers:id]]]",
                subcoll.toString());
    }

    // collection/subcollection/... [doc, query]

    @Test
    public void useCase_collection_collection_doc() {
        try {
            var mapDoc = Documents.collection("customers")
                    .collection("orders")
                    .doc("38234");
            assert false;

        } catch (UnsupportedOperationException uae) {
            assert true;
        }

        try {
            var mapDoc = Documents.collection("customers")
                    .collection("orders")
                    .doc("38234", Order.class);
            assert false;

        } catch (UnsupportedOperationException uae) {
            assert true;
        }
    }

    @Test
    public void useCase_collection_collection_query() {
        var mapQuery = Documents.collection("customers")
                .collection("orders")
                .query();

        assertEquals("customers", mapQuery.collection.parent.collection.name);
        assertEquals("orders", mapQuery.collection.name);
        assertEquals(Map.class, mapQuery.type);
        assertEquals("Query[collection=DocColl[name=orders, parent=DocKey[collection=DocColl[name=customers, parent=null], id=]], expressions=[], limit=0, pagingToken=null, type=interface java.util.Map]",
                mapQuery.toString());

        var orderQuery = Documents.collection("customers")
                .collection("orders")
                .query(Order.class);

        assertEquals("customers", orderQuery.collection.parent.collection.name);
        assertEquals("orders", orderQuery.collection.name);
        assertEquals(Order.class, orderQuery.type);
    }

    // collection/doc/subcollection/... [doc, query, subCollection]

    @Test
    public void useCase_collection_doc_collection_doc() {
        var mapDoc = Documents.collection("customers")
                .doc("customers:id")
                .collection("orders")
                .doc("orders:id");

        assertEquals("customers", mapDoc.key.collection.parent.collection.name);
        assertEquals("customers:id", mapDoc.key.collection.parent.id);
        assertEquals("orders", mapDoc.key.collection.name);
        assertEquals("orders:id", mapDoc.key.id);
        assertEquals(Map.class, mapDoc.type);
        assertEquals("SubCollectionDocRef[key=DocKey[collection=DocColl[name=orders, parent=DocKey[collection=DocColl[name=customers, parent=null], id=customers:id]], id=orders:id], type=interface java.util.Map]",
                mapDoc.toString());

        var orderDoc = Documents.collection("customers")
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
    public void userCase_collection_doc_subcollection_query() {
        var mapQuery = Documents.collection("customers")
                .doc("customers:id")
                .collection("orders")
                .query();

        assertEquals("customers", mapQuery.collection.parent.collection.name);
        assertEquals("customers:id", mapQuery.collection.parent.id);
        assertEquals("orders", mapQuery.collection.name);
        assertEquals(Map.class, mapQuery.type);
        assertEquals("Query[collection=DocColl[name=orders, parent=DocKey[collection=DocColl[name=customers, parent=null], id=customers:id]], expressions=[], limit=0, pagingToken=null, type=interface java.util.Map]",
                mapQuery.toString());

        var orderQuery = Documents.collection("customers")
                .doc("customers:id")
                .collection("orders")
                .query(Order.class);

        assertEquals("customers", orderQuery.collection.parent.collection.name);
        assertEquals("customers:id", orderQuery.collection.parent.id);
        assertEquals("orders", orderQuery.collection.name);
        assertEquals(Order.class, orderQuery.type);
    }

}