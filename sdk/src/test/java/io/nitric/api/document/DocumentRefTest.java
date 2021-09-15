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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.nitric.api.NitricException;
import io.nitric.api.NotFoundException;
import io.nitric.api.document.model.Customer;
import io.nitric.api.document.model.Order;
import io.nitric.proto.document.v1.Document;
import io.nitric.proto.document.v1.DocumentDeleteRequest;
import io.nitric.proto.document.v1.DocumentDeleteResponse;
import io.nitric.proto.document.v1.DocumentGetRequest;
import io.nitric.proto.document.v1.DocumentGetResponse;
import io.nitric.proto.document.v1.DocumentServiceGrpc;
import io.nitric.proto.document.v1.DocumentSetRequest;
import io.nitric.proto.document.v1.DocumentSetResponse;
import io.nitric.util.ProtoUtils;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Provides CollectionDocRef test case.
 */
@ExtendWith(MockitoExtension.class)
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
        var value = new Documents().collection("customers").doc("id").get();
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

        value = new Documents().collection("customers").doc("id").get();
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

        var cust = new Documents().collection("customers").doc("id", Customer.class).get();
        Mockito.verify(mock, Mockito.times(3)).get(Mockito.any());
        assertEquals(customer.getEmail(), cust.getEmail());

        // Verify GRPC Failure Mode
        Mockito.when(mock.get(Mockito.any(DocumentGetRequest.class))).thenThrow(
                new StatusRuntimeException(Status.NOT_FOUND)
        );

        var docRef =  new Documents().collection("customers").doc("id", Customer.class);
        try {
            docRef.get();
            fail();
        } catch (NotFoundException nfe) {
        }
    }

    @Test
    public void test_collection_doc_set() {
        var mock = Mockito.mock(DocumentServiceGrpc.DocumentServiceBlockingStub.class);
        Mockito.when(mock.set(Mockito.any())).thenReturn(
                DocumentSetResponse.newBuilder().build()
        );
        Documents.setServiceStub(mock);

        // Test set map
        new Documents().collection("customers").doc("id").set(Collections.emptyMap());
        Mockito.verify(mock, Mockito.times(1)).set(Mockito.any());

        // Test set customer object
        Customer customer = new Customer();
        customer.setEmail("test@server.com");
        new Documents().collection("customers").doc("id", Customer.class).set(customer);
        Mockito.verify(mock, Mockito.times(2)).set(Mockito.any());

        // Verify GRPC Failure Mode
        Mockito.when(mock.set(Mockito.any(DocumentSetRequest.class))).thenThrow(
                new StatusRuntimeException(Status.INTERNAL)
        );

        var docRef = new Documents().collection("customers").doc("id", Customer.class);
        try {
            docRef.set(customer);
            fail();
        } catch (NitricException ne) {
        }
    }

    @Test
    public void test_collection_doc_delete() {
        var mock = Mockito.mock(DocumentServiceGrpc.DocumentServiceBlockingStub.class);
        Mockito.when(mock.delete(Mockito.any())).thenReturn(
                DocumentDeleteResponse.newBuilder().build()
        );
        Documents.setServiceStub(mock);

        new Documents().collection("customers").doc("id").delete();
        Mockito.verify(mock, Mockito.times(1)).delete(Mockito.any());

        // Verify GRPC Failure Mode
        Mockito.when(mock.delete(Mockito.any(DocumentDeleteRequest.class))).thenThrow(
                new StatusRuntimeException(Status.INTERNAL)
        );

        var docRef = new Documents().collection("customers").doc("id");
        try {
            docRef.delete();
            fail();
        } catch (NitricException ne) {
        }
    }

    @Test
    public void test_collection_doc_collection_doc_get() {
        var mock = Mockito.mock(DocumentServiceGrpc.DocumentServiceBlockingStub.class);
        Mockito.when(mock.get(Mockito.any())).thenReturn(
                DocumentGetResponse.newBuilder().build()
        );
        Documents.setServiceStub(mock);

        // Test empty response
        var value = new Documents().collection("customers")
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

        value = new Documents().collection("customers")
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

        var ord = new Documents().collection("customers")
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
        new Documents().collection("customers")
                .doc("customer-1")
                .collection("orders")
                .doc("order-1")
                .set(Collections.emptyMap());
        Mockito.verify(mock, Mockito.times(1)).set(Mockito.any());

        // Test set customer object
        Order order = new Order();
        order.setSku("BYD EA-1");
        new Documents().collection("customers")
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

        new Documents().collection("customers")
                .doc("customer-1")
                .collection("orders")
                .doc("order-1")
                .delete();
        Mockito.verify(mock, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    public void test_collection_doc_collection_doc_collection() {
        var docRef = new Documents().collection("customers")
                .doc("customer-1")
                .collection("orders")
                .doc("order-1");
        try {
            docRef.collection("payments");
            fail();
        } catch (IllegalArgumentException uoe) {
        }
    }

}
