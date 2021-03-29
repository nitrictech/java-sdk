package io.nitric.api.storage;

import com.google.protobuf.ByteString;
import io.nitric.proto.storage.v1.*;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class StorageClientTest {

    private static final String KNOWN_KEY = "123456";
    private static final String KNOWN_TEXT = "hello world";
    private static final ByteString KNOWN_BS = ByteString.copyFrom(KNOWN_TEXT .getBytes(StandardCharsets.UTF_8));

    @Test
    public void test_build() {
        var client = StorageClient.build("bucket");

        assertNotNull(client);
        assertEquals("bucket", client.bucket);
        assertNotNull(client.serviceStub);

        try {
            StorageClient.newBuilder().build();
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertEquals("bucket parameter is required", npe.getMessage());
        }
    }

    @Test
    public void test_read() {
        var mock = new MockStorageBlockingStub() {
            @Override
            public StorageReadResponse read(StorageReadRequest request) {
                assertNotNull(request);
                assertNotNull(request.getBucketName());
                assertNotNull(request.getKey());

                if (KNOWN_KEY.equals(request.getKey())) {
                    return StorageReadResponse.newBuilder()
                            .setBody(KNOWN_BS)
                            .build();
                } else {
                    return StorageReadResponse.newBuilder().build();
                }
            }
        };
        var client = StorageClient.newBuilder().bucket("bucket").serviceStub(mock).build();

        byte[] data = client.read(KNOWN_KEY);
        assertNotNull(data);
        assertEquals(KNOWN_TEXT, new String(data));

        data = client.read("unknown key");
        assertNull(data);

        try {
            client.read(null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertEquals("key parameter is required", npe.getMessage());
        }
    }

    @Test
    public void test_write() {
        var mock = new MockStorageBlockingStub() {
            @Override
            public StorageWriteResponse write(StorageWriteRequest request) {
                assertNotNull(request);
                assertNotNull(request.getBucketName());
                assertNotNull(request.getKey());

                return StorageWriteResponse.newBuilder().build();
            }
        };
        var client = StorageClient.newBuilder().bucket("bucket").serviceStub(mock).build();

        byte[] data = "this data".getBytes(StandardCharsets.UTF_8);
        client.write("this key", data);

        try {
            client.write(null, data);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertEquals("key parameter is required", npe.getMessage());
        }

        try {
            client.write("this key", null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertEquals("data parameter is required", npe.getMessage());
        }
    }

    @Test
    public void test_delete() {
        var mock = new MockStorageBlockingStub() {
            @Override
            public StorageDeleteResponse delete(StorageDeleteRequest request) {
                assertNotNull(request);
                assertNotNull(request.getBucketName());
                assertNotNull(request.getKey());

                return StorageDeleteResponse.newBuilder().build();
            }
        };
        var client = StorageClient.newBuilder().bucket("bucket").serviceStub(mock).build();

        client.delete(KNOWN_KEY);

        client.delete("unknown key");

        try {
            client.delete(null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertEquals("key parameter is required", npe.getMessage());
        }
    }
}
