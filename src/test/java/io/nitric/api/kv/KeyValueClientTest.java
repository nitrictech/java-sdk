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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Struct;
import io.nitric.proto.kv.v1.*;
import io.nitric.util.ProtoUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class KeyValueClientTest {

    static final String KNOWN_KEY = "john.smith@gmail.com";
    static final Map<String, Object> KNOWN_MAP = Map.of("name", "John Smith");
    static final Struct KNOWN_STRUCT = ProtoUtils.toStruct(KNOWN_MAP);

    @Test
    public void test_newBuilder() {
        var client = KeyValueClient.newBuilder(Map.class)
                .collection("customers")
                .build();

        assertNotNull(client);
        assertEquals("customers", client.collection);
        assertNotNull(client.serviceStub);

        try {
            KeyValueClient.newBuilder(null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertEquals("type parameter is required", npe.getMessage());
        }

        try {
            KeyValueClient.newBuilder(Map.class).build();
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertEquals("collection parameter is required", npe.getMessage());
        }
    }

    @Test
    public void test_build() {
        var client = KeyValueClient.build(Map.class, "customers");

        assertNotNull(client);
        assertEquals("customers", client.collection);
        assertEquals(Map.class, client.type);
        assertNotNull(client.serviceStub);

        try {
            KeyValueClient.build(Map.class, null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertEquals("collection parameter is required", npe.getMessage());
        }

        try {
            KeyValueClient.build(null, "collection");
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertEquals("type parameter is required", npe.getMessage());
        }
    }

    @Test
    public void test_get() {
        var mock = new MockKeyValueBlockingStub() {
            @Override
            public KeyValueGetResponse get(KeyValueGetRequest request) {
                assertNotNull(request);
                assertNotNull(request.getCollection());
                assertNotNull(request.getKey());
                if (request.getKey().equals(KNOWN_KEY)) {
                    return KeyValueGetResponse.newBuilder().setValue(KNOWN_STRUCT).build();
                } else {
                    return KeyValueGetResponse.newBuilder().build();
                }
            }
        };

        // Map type client
        var mapClient = KeyValueClient.newBuilder(Map.class)
                .collection("customers")
                .serviceStub(mock)
                .build();

        var customer = mapClient.get(KNOWN_KEY);
        assertEquals(KNOWN_MAP, customer);

        customer = mapClient.get("unknown key");
        assertNull(customer);

        try {
            mapClient.get(null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertTrue(npe.getMessage().contains("key parameter is required"));
        }

        // Typed client
        var typedMock = new MockKeyValueBlockingStub() {
            @Override
            public KeyValueGetResponse get(KeyValueGetRequest request) {
                if (request.getKey().equals("12345")) {
                    Account account = createAccount();
                    Map map = new ObjectMapper().convertValue(account, Map.class);
                    var struct = ProtoUtils.toStruct(map);
                    return KeyValueGetResponse.newBuilder().setValue(struct).build();
                } else {
                    return KeyValueGetResponse.newBuilder().build();
                }
            }
        };

        var typedClient = KeyValueClient.newBuilder(Account.class)
                .serviceStub(typedMock)
                .collection("account")
                .build();

        Account account = typedClient.get(12345);
        assertNotNull(account);
        assertEquals(createAccount(), account);

        Account account2 = typedClient.get("00000");
        assertNull(account2);
    }

    @Test
    public void test_objectMapping() {
        // Test Jackson Marshalling
        ObjectMapper objectMapper = new ObjectMapper();

        Account account1 = createAccount();
        Account account2 = createAccount();
        assertTrue(account1.equals(account2));

        Map map1 = objectMapper.convertValue(account1, Map.class);
        Map map2 = objectMapper.convertValue(account2, Map.class);
        assertTrue(map1.equals(map2));

        Account account1_0 = objectMapper.convertValue(map1, Account.class);
        assertTrue(account1.equals(account1_0));

        Account account2_0 = objectMapper.convertValue(map2, Account.class);
        assertTrue(account1.equals(account2_0));

        // Test Proto Marshalling
        Struct struct1 = ProtoUtils.toStruct(map1);
        Map protoMap1 = ProtoUtils.toMap(struct1);

        Account protoAccount1 = objectMapper.convertValue(protoMap1, Account.class);
        assertEquals(account1, protoAccount1);
    }

    @Test
    public void test_put() {
        var mock = new MockKeyValueBlockingStub() {
            @Override
            public KeyValuePutResponse put(KeyValuePutRequest request) {
                assertNotNull(request);
                assertNotNull(request.getCollection());
                assertNotNull(request.getKey());
                assertNotNull(request.getValue());
                return KeyValuePutResponse.newBuilder().build();
            }
        };

        var mapClient = KeyValueClient.newBuilder(Map.class)
                .collection("customers")
                .serviceStub(mock)
                .build();

        mapClient.put(KNOWN_KEY, KNOWN_MAP);

        try {
            mapClient.put(null, KNOWN_MAP);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertTrue(npe.getMessage().contains("key parameter is required"));
        }

        try {
            mapClient.put(KNOWN_KEY, null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertTrue(npe.getMessage().contains("value parameter is required"));
        }

        var typeClient = KeyValueClient.newBuilder(Account.class)
                .collection("account")
                .serviceStub(mock)
                .build();

        var account = new Account();
        account.setId(12345);
        account.setType("wholesale");
        account.setActive(true);

        typeClient.put(account.getId(), account);
    }

    @Test
    public void test_delete() {
        var mock = new MockKeyValueBlockingStub() {
            @Override
            public KeyValueDeleteResponse delete(KeyValueDeleteRequest request) {
                assertNotNull(request);
                assertNotNull(request.getCollection());
                assertNotNull(request.getKey());
                var value = Struct.newBuilder().build();
                return KeyValueDeleteResponse.newBuilder().build();
            }
        };

        var client = KeyValueClient.newBuilder(Map.class)
                .collection("customers")
                .serviceStub(mock)
                .build();

        client.delete(KNOWN_KEY);

        try {
            client.delete(null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertTrue(npe.getMessage().contains("key parameter is required"));
        }
    }

    // Package Private Methods ------------------------------------------------

    static Account createAccount() {

        Account account = new Account();
        account.setActive(true);
        account.setAssetsValue(100_000D);
        account.setType("retail");
        account.setId(12345);

        Address address1 = new Address();
        address1.setFirstLine("first line");
        address1.setFirstLine("second line");
        address1.setCity("city");
        address1.setCountry("US");
        address1.setPostcode("12345");
        account.setCurrentAddress(address1);

        Address address2 = new Address();
        address2.setFirstLine("first line");
        address2.setFirstLine("second line");
        address2.setCity("town");
        address2.setCountry("UK");
        address2.setPostcode("56789");
        account.getPreviousAddresses().add(address2);

        Address address3 = new Address();
        address3.setFirstLine("first line");
        address3.setFirstLine("second line");
        address3.setCity("suburb");
        address3.setCountry("AU");
        address3.setPostcode("4321");
        account.getPreviousAddresses().add(address3);

        account.getProperties().put("custom prop 1", "custom value 1");
        account.getProperties().put("custom prop 2", "custom value 2");

        return account;
    }

}
