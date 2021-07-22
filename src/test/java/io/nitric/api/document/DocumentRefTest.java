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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nitric.api.document.model.Customer;
import io.nitric.api.document.model.Order;
import io.nitric.proto.document.v1.Document;
import io.nitric.proto.document.v1.DocumentDeleteResponse;
import io.nitric.proto.document.v1.DocumentGetResponse;
import io.nitric.proto.document.v1.DocumentServiceGrpc;
import io.nitric.proto.document.v1.DocumentSetResponse;
import io.nitric.util.ProtoUtils;
import org.junit.Test;
import org.mockito.Mockito;

import javax.print.Doc;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Provides CollectionDocRef test case.
 */
public class DocumentRefTest {

    @Test
    public void test_toString() {
        var collection = new Collection("customers", null);
        var key = new Key(collection, "123");
        var document = new DocumentRef<Map>(key, Map.class);
        assertEquals("DocumentRef[key=Key[collection=Collection[name=customers, parent=null], id=123], type=interface java.util.Map]",
                document.toString());
    }

    @Test
    public void test_collection_doc_get() {
        var mock = Mockito.mock(DocumentServiceGrpc.DocumentServiceBlockingStub.class);
        Mockito.when(mock.get(Mockito.any())).thenReturn(
                DocumentGetResponse.newBuilder().build()
        );
        Documents.setServiceStub(mock);

        // Test empty response
        var value = Documents.collection("customers").doc("id").get();
        Mockito.verify(mock, Mockito.times(1)).get(Mockito.any());
        assertNull(value);

        // Test get map object
        Map<String, Object> custMap = Map.of("email", "test@server.com");
        var struct = ProtoUtils.toStruct(custMap);

        Mockito.when(mock.get(Mockito.any())).thenReturn(
                DocumentGetResponse.newBuilder()
                        .setDocument(Document.newBuilder().setContent(struct).build())
                        .build()
        );

        value = Documents.collection("customers").doc("id").get();
        Mockito.verify(mock, Mockito.times(2)).get(Mockito.any());
        assertEquals(custMap, value);

        // Test get customer object
        Customer customer = new Customer();
        customer.setEmail("test@server.com");
        custMap = new ObjectMapper().convertValue(customer, Map.class);
        struct = ProtoUtils.toStruct(custMap);

        Mockito.when(mock.get(Mockito.any())).thenReturn(
                DocumentGetResponse.newBuilder()
                        .setDocument(Document.newBuilder().setContent(struct).build())
                        .build()
        );

        var cust = Documents.collection("customers").doc("id", Customer.class).get();
        Mockito.verify(mock, Mockito.times(3)).get(Mockito.any());
        assertEquals(customer.getEmail(), cust.getEmail());
    }

    @Test
    public void test_collection_doc_set() {
        var mock = Mockito.mock(DocumentServiceGrpc.DocumentServiceBlockingStub.class);
        Mockito.when(mock.set(Mockito.any())).thenReturn(
                DocumentSetResponse.newBuilder().build()
        );
        Documents.setServiceStub(mock);

        // Test set map
        Documents.collection("customers").doc("id").set(Collections.emptyMap());
        Mockito.verify(mock, Mockito.times(1)).set(Mockito.any());

        // Test set customer object
        Customer customer = new Customer();
        customer.setEmail("test@server.com");
        Documents.collection("customers").doc("id", Customer.class).set(customer);
        Mockito.verify(mock, Mockito.times(2)).set(Mockito.any());
    }

    @Test
    public void test_collection_doc_delete() {
        var mock = Mockito.mock(DocumentServiceGrpc.DocumentServiceBlockingStub.class);
        Mockito.when(mock.delete(Mockito.any())).thenReturn(
                DocumentDeleteResponse.newBuilder().build()
        );
        Documents.setServiceStub(mock);

        Documents.collection("customers").doc("id").delete();
        Mockito.verify(mock, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    public void test_collection_doc_collection_doc_get() {
        var mock = Mockito.mock(DocumentServiceGrpc.DocumentServiceBlockingStub.class);
        Mockito.when(mock.get(Mockito.any())).thenReturn(
                DocumentGetResponse.newBuilder().build()
        );
        Documents.setServiceStub(mock);

        // Test empty response
        var value = Documents.collection("customers")
                .doc("customer-1")
                .collection("orders")
                .doc("order-1")
                .get();
        Mockito.verify(mock, Mockito.times(1)).get(Mockito.any());
        assertNull(value);

        // Test get map object
        Map<String, Object> orderMap = Map.of("sku", "BYD EA-1");
        var struct = ProtoUtils.toStruct(orderMap);

        Mockito.when(mock.get(Mockito.any())).thenReturn(
                DocumentGetResponse.newBuilder()
                        .setDocument(Document.newBuilder().setContent(struct).build())
                        .build()
        );

        value = Documents.collection("customers")
                .doc("customer-1")
                .collection("orders")
                .doc("order-1")
                .get();
        Mockito.verify(mock, Mockito.times(2)).get(Mockito.any());
        assertEquals(orderMap, value);

        Order order = new Order();
        order.setSku("BYD EA-1");
        orderMap = new ObjectMapper().convertValue(order, Map.class);
        struct = ProtoUtils.toStruct(orderMap);

        Mockito.when(mock.get(Mockito.any())).thenReturn(
                DocumentGetResponse.newBuilder()
                        .setDocument(Document.newBuilder().setContent(struct).build())
                        .build()
        );

        var ord = Documents.collection("customers")
                .doc("customer-1")
                .collection("orders")
                .doc("order-1", Order.class)
                .get();
        Mockito.verify(mock, Mockito.times(3)).get(Mockito.any());
        assertEquals(order.getSku(), ord.getSku());
    }

    @Test
    public void test_collection_doc_collection_doc_set() {
        var mock = Mockito.mock(DocumentServiceGrpc.DocumentServiceBlockingStub.class);
        Mockito.when(mock.set(Mockito.any())).thenReturn(
                DocumentSetResponse.newBuilder().build()
        );
        Documents.setServiceStub(mock);

        // Test set map
        Documents.collection("customers")
                .doc("customer-1")
                .collection("orders")
                .doc("order-1")
                .set(Collections.emptyMap());
        Mockito.verify(mock, Mockito.times(1)).set(Mockito.any());

        // Test set customer object
        Order order = new Order();
        order.setSku("BYD EA-1");
        Documents.collection("customers")
                .doc("customer-1")
                .collection("orders")
                .doc("order-1", Order.class)
                .set(order);
        Mockito.verify(mock, Mockito.times(2)).set(Mockito.any());
    }

    @Test
    public void test_collection_doc_collection_doc_delete() {
        var mock = Mockito.mock(DocumentServiceGrpc.DocumentServiceBlockingStub.class);
        Mockito.when(mock.delete(Mockito.any())).thenReturn(
                DocumentDeleteResponse.newBuilder().build()
        );
        Documents.setServiceStub(mock);

        Documents.collection("customers")
                .doc("customer-1")
                .collection("orders")
                .doc("order-1")
                .delete();
        Mockito.verify(mock, Mockito.times(1)).delete(Mockito.any());
    }

}
