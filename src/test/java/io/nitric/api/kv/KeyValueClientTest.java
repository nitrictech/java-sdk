package io.nitric.api.kv;

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
    public void test_build() {
        var client = KeyValueClient.build("customers");

        assertNotNull(client);
        assertEquals("customers", client.collection);
        assertNotNull(client.serviceStub);

        try {
            KeyValueClient.newBuilder().build();
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertEquals("collection parameter is required", npe.getMessage());
        }
    }

    @Test
    public void test_get() {
        var mock = new MockKeyValueBlockingStub() {
            @Override
            public KeyValueGetResponse get(KeyValueGetRequest request) {
                if (request.getKey().equals(KNOWN_KEY)) {
                    return KeyValueGetResponse.newBuilder().setValue(KNOWN_STRUCT).build();
                } else {
                    return KeyValueGetResponse.newBuilder().build();
                }
            }
        };

        var client = KeyValueClient.newBuilder()
                .collection("customers")
                .serviceStub(mock)
                .build();

        var customer = client.get(KNOWN_KEY);
        assertEquals(KNOWN_MAP, customer);

        customer = client.get("unknown key");
        assertNull(customer);

        try {
            client.get(null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertTrue(npe.getMessage().contains("key parameter is required"));
        }
    }

    @Test
    public void test_put() {
        var mock = new MockKeyValueBlockingStub() {
            @Override
            public KeyValuePutResponse put(KeyValuePutRequest request) {
                var value = Struct.newBuilder().build();
                return KeyValuePutResponse.newBuilder().build();
            }
        };

        var client = KeyValueClient.newBuilder()
                .collection("customers")
                .serviceStub(mock)
                .build();

        client.put(KNOWN_KEY, KNOWN_MAP);

        try {
            client.put(null, KNOWN_MAP);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertTrue(npe.getMessage().contains("key parameter is required"));
        }

        try {
            client.put(KNOWN_KEY, null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertTrue(npe.getMessage().contains("value parameter is required"));
        }
    }

    @Test
    public void test_delete() {
        var mock = new MockKeyValueBlockingStub() {
            @Override
            public KeyValueDeleteResponse delete(KeyValueDeleteRequest request) {
                var value = Struct.newBuilder().build();
                return KeyValueDeleteResponse.newBuilder().build();
            }
        };

        var client = KeyValueClient.newBuilder()
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
}
