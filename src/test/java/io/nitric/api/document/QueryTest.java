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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.mockito.Mockito;

import io.nitric.api.document.model.Customer;
import io.nitric.api.document.model.Order;
import io.nitric.proto.document.v1.Document;
import io.nitric.proto.document.v1.DocumentQueryResponse;
import io.nitric.proto.document.v1.DocumentServiceGrpc;
import io.nitric.util.ProtoUtils;

/**
 * Provide a query test case.
 */
public class QueryTest {

    @Test
    public void test_where() {
        var collection = new Collection("customers", null);
        var query = new Query<Customer>(collection, Customer.class);

        query.where("key", "==", "value");
        assertEquals(1, query.expressions.size());
        var expression = query.expressions.get(0);
        assertEquals("key", expression.operand);
        assertEquals("==", expression.operator);
        assertEquals("value", expression.value);

        query.where("range", ">", "123");
        assertEquals(2, query.expressions.size());
        expression = query.expressions.get(1);
        assertEquals("range", expression.operand);
        assertEquals(">", expression.operator);
        assertEquals("123", expression.value);

        query.expressions.clear();
        try {
            query.where("", "==", "value");
            assert false;
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-blank operand", iae.getMessage());
        }

        query.expressions.clear();
        try {
            query.where("key", "", "value");
            assert false;
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-blank operator", iae.getMessage());
        }

        query.expressions.clear();
        try {
            query.where("key", "<", "");
            assert false;
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-blank value", iae.getMessage());
        }

        query.expressions.clear();
        query.where("string", "==", "string")
            .where("double", "==", 1.1)
            .where("integer", "==", 2)
            .where("boolean", "==", true);
        assertEquals(4, query.expressions.size());
        assertEquals("string", query.expressions.get(0).value);
        assertEquals(1.1, query.expressions.get(1).value);
        assertEquals(2, query.expressions.get(2).value);
        assertEquals(true, query.expressions.get(3).value);

        assertEquals("string", query.expressions.get(0).toExpressionValue().getStringValue());
        assertEquals(1.1, query.expressions.get(1).toExpressionValue().getDoubleValue(), 0.0000001);
        assertEquals(2, query.expressions.get(2).toExpressionValue().getIntValue());
        assertEquals(true, query.expressions.get(3).toExpressionValue().getBoolValue());

        var exp = new Query.Expression("field", ">", 1);
        assertEquals("Expression[operand=field, operator=>, value=1]", exp.toString());

        exp = new Query.Expression("field", "<>", Collections.emptyMap());
        try {
            exp.toExpressionValue();
            fail();
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void test_limit() {
        var collection = new Collection("customers", null);
        var query = new Query<Customer>(collection, Customer.class);
        query.limit(100);

        assertEquals(100, query.limit);
    }

    @Test
    public void test_pagingFrom() {
        var collection = new Collection("customers", null);
        var query = new Query<Customer>(collection, Customer.class);
        var pagingToken = Map.of("page", "2");
        query.pagingFrom(pagingToken);

        assertEquals(pagingToken, query.pagingToken);
    }

    @Test
    public void test_fetch() {
        // Test Empty Result
        var mock = Mockito.mock(DocumentServiceGrpc.DocumentServiceBlockingStub.class);
        Mockito.when(mock.query(Mockito.any())).thenReturn(
                DocumentQueryResponse.newBuilder().build()
        );
        Documents.setServiceStub(mock);

        var query = newOrderQuery();
        var result = query.fetch();

        Mockito.verify(mock, Mockito.times(1)).query(Mockito.any());
        assertNotNull(result);
        assertNull(result.getPagingToken());
        assertFalse(result.iterator().hasNext());

        // Test 12 POJO Values Result
        Mockito.when(mock.query(Mockito.any())).thenReturn(
                createOrdersQueryResponse()
        );

        query = newOrderQuery();
        result = query.fetch();

        Mockito.verify(mock, Mockito.times(2)).query(Mockito.any());
        assertNotNull(result);
        assertNull(result.getPagingToken());
        assertTrue(result.iterator().hasNext());

        final var count = new AtomicInteger();
        result.forEach(doc -> {
            assertTrue(doc.getContent() instanceof  Order);
            assertNotNull(doc.getKey());
            count.incrementAndGet();
        });
        assertEquals(12, count.get());

        // Test 12 Map Values Result
        Mockito.when(mock.query(Mockito.any())).thenReturn(
                createCustomersQueryResponse()
        );

        var customerResults = Documents.collection("customer").query().fetch();

        Mockito.verify(mock, Mockito.times(3)).query(Mockito.any());
        assertNotNull(customerResults);
        assertNotNull(customerResults.getPagingToken());
        assertTrue(customerResults.iterator().hasNext());

        final var count2 = new AtomicInteger();
        customerResults.forEach(doc -> {
            assertTrue(Map.class.isAssignableFrom(doc.getContent().getClass()));
            assertNotNull(doc.getKey());
            count2.incrementAndGet();
        });
        assertEquals(12, count2.get());

        // Test Paged Query
        var pagingToken = Map.of("page", "2");
        customerResults = Documents.collection("customer")
                .query()
                .pagingFrom(pagingToken)
                .fetch();

        Mockito.verify(mock, Mockito.times(4)).query(Mockito.any());
        assertNotNull(customerResults);
        assertNotNull(customerResults.getPagingToken());
        assertTrue(customerResults.iterator().hasNext());

        final var count3 = new AtomicInteger();
        customerResults.forEach(doc -> {
            count3.incrementAndGet();
        });
        assertEquals(12, count3.get());
    }

    @Test
    public void test_stream() {
        // Test Empty Result
        var mock = Mockito.mock(DocumentServiceGrpc.DocumentServiceBlockingStub.class);
        Mockito.when(mock.query(Mockito.any())).thenReturn(
                DocumentQueryResponse.newBuilder().build()
        );
        Documents.setServiceStub(mock);

        var query = newOrderQuery();
        Stream<ResultDoc<Order>> stream = query.stream();

        Mockito.verify(mock, Mockito.times(1)).query(Mockito.any());
        assertNotNull(stream);
        assertEquals(0, stream.count());

        // Test 12 Values Result
        Mockito.when(mock.query(Mockito.any())).thenReturn(
                createOrdersQueryResponse()
        );

        query = newOrderQuery();
        stream = query.stream();

        Mockito.verify(mock, Mockito.times(2)).query(Mockito.any());
        assertNotNull(stream);

        final var count = new AtomicInteger();
        stream.forEach(doc -> {
            assertNotNull(doc.getContent());
            assertNotNull(doc.getKey());
            count.incrementAndGet();
        });
        assertEquals(12, count.get());

        // Test 12 Values Result
        Mockito.when(mock.query(Mockito.any())).thenReturn(
                createOrdersQueryResponse()
        );

        query = newOrderQuery();
        stream = query.limit(10).stream();

        Mockito.verify(mock, Mockito.times(3)).query(Mockito.any());
        assertNotNull(stream);
        assertEquals(12, count.get());
    }

    @Test
    public void test_toString() {
        var parentKey = new Key(new Collection("customers", null), "customers:123");
        var collection = new Collection("orders", parentKey);
        var query = new Query<Order>(collection, Order.class);
        query.where("sku", "==", "BYD EA-1");
        query.limit(100);
        query.pagingFrom(Map.of("page", "2"));

        assertEquals("Query[collection=Collection[name=orders, parent=Key[collection=Collection[name=customers, parent=null], id=customers:123]], expressions=[Expression[operand=sku, operator===, value=BYD EA-1]], limit=100, pagingToken={page=2}, type=class io.nitric.api.document.model.Order]",
                query.toString());
    }

    // Private Methods --------------------------------------------------------

    private List<Document> newCustomers() {
        List<Map> customers = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            var content = Map.of("id", String.valueOf(i));
            customers.add(content);
        }

        var collection = io.nitric.proto.document.v1.Collection
                .newBuilder()
                .setName("customers")
                .build();

        List<Document> results = new ArrayList<>();

        var objectMapper = new ObjectMapper();
        customers.forEach(customer -> {

            var key = io.nitric.proto.document.v1.Key
                    .newBuilder()
                    .setId(customer.get("id").toString())
                    .setCollection(collection)
                    .build();

            var contentStruct = ProtoUtils.toStruct(customer);
            var document = Document.newBuilder()
                    .setKey(key)
                    .setContent(contentStruct)
                    .build();
            results.add(document);
        });

        return results;
    }

    private List<Document> newOrders() {

        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            var order = new Order();
            order.setId(String.valueOf(i));
            order.setSku("ABC-" + i);
            orders.add(order);
        }

        var parentCollection = io.nitric.proto.document.v1.Collection
                .newBuilder()
                .setName("customers")
                .build();
        var parentKey = io.nitric.proto.document.v1.Key
                .newBuilder()
                .setId("12345")
                .setCollection(parentCollection)
                .build();
        var collection = io.nitric.proto.document.v1.Collection
                .newBuilder()
                .setName("orders")
                .setParent(parentKey)
                .build();

        List<Document> results = new ArrayList<>();

        var objectMapper = new ObjectMapper();
        orders.forEach(order -> {

            var key = io.nitric.proto.document.v1.Key
                    .newBuilder()
                    .setId(order.getId())
                    .setCollection(collection)
                    .build();

            var contentMap = objectMapper.convertValue(order, Map.class);
            var contentStruct = ProtoUtils.toStruct(contentMap);
            var document = Document.newBuilder()
                    .setKey(key)
                    .setContent(contentStruct)
                    .build();
            results.add(document);
        });

        return results;
    }

    private Query<Order> newOrderQuery() {
        var parentKey = new Key(new Collection("customers", null), "customers:123");
        var collection = new Collection("orders", parentKey);
        var query = new Query<Order>(collection, Order.class);
        query.where("sku", "==", "BYD EA-1");

        return query;
    }

    private DocumentQueryResponse createOrdersQueryResponse() {
        return DocumentQueryResponse.newBuilder().addAllDocuments(newOrders()).build();
    }

    private DocumentQueryResponse createCustomersQueryResponse() {
        return DocumentQueryResponse.newBuilder()
                .addAllDocuments(newCustomers())
                .putAllPagingToken(Map.of("page", "0"))
                .build();
    }

}
