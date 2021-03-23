package io.nitric.api.kv;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KeyValueClientTest {

    @Test
    public void test_build() {
        var client = KeyValueClient.newBuilder(String.class)
                .collection("customers")
                .build();

        assertNotNull(client);
        assertEquals("customers", client.collection);
        assertEquals(String.class, client.type);
        assertNotNull(client.kvStub);
    }

//    @Test
//    public void test_get() {
//        var client = KeyValueClient.newBuilder(String.class)
//                .collection("customers")
//                .build();
//
//        var customer = client.get("john.smith@gmail.com");
//    }
//
//    @Test
//    public void test_put() {
//        var client = KeyValueClient.newBuilder(String.class)
//                .collection("customers")
//                .build();
//
//        client.put("john.smith@gmail.com", "{}");
//    }
//
//    @Test
//    public void test_delete() {
//        var client = KeyValueClient.newBuilder(String.class)
//                .collection("customers")
//                .build();
//
//        client.delete("john.smith@gmail.com");
//    }
}
