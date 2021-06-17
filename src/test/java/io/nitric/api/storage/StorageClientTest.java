package io.nitric.api.storage;

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

import com.google.protobuf.ByteString;
import io.nitric.proto.storage.v1.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
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
        var mock = Mockito.mock(StorageGrpc.StorageBlockingStub.class);
        Mockito.when(mock.read(Mockito.any(StorageReadRequest.class))).thenReturn(
                StorageReadResponse.newBuilder().setBody(KNOWN_BS).build()
        );

        var client = StorageClient.newBuilder().bucket("bucket").serviceStub(mock).build();
        byte[] data = client.read("ANY KEY");
        assertNotNull(data);
        assertEquals(KNOWN_TEXT, new String(data));

        data = client.read("unknown key");
        assertNotNull(data);

        try {
            client.read(null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertEquals("key parameter is required", npe.getMessage());
        }
    }

    @Test
    public void test_write() {
        var mock = Mockito.mock(StorageGrpc.StorageBlockingStub.class);
        Mockito.when(mock.write(Mockito.any(StorageWriteRequest.class))).thenReturn(
                StorageWriteResponse.newBuilder().build()
        );

        var client = StorageClient.newBuilder().bucket("bucket").serviceStub(mock).build();

        byte[] data = "this data".getBytes(StandardCharsets.UTF_8);
        client.write("this key", data);

        // Verify we actually called the mock object
        // TODO: Use Mockito.eq for testing here...
        Mockito.verify(mock).write(Mockito.any());

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
        var mock = Mockito.mock(StorageGrpc.StorageBlockingStub.class);
        Mockito.when(mock.delete(Mockito.any(StorageDeleteRequest.class))).thenReturn(
                StorageDeleteResponse.newBuilder().build()
        );

        var client = StorageClient.newBuilder().bucket("bucket").serviceStub(mock).build();

        client.delete(KNOWN_KEY);

        Mockito.verify(mock).delete(Mockito.any());

        try {
            client.delete(null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertEquals("key parameter is required", npe.getMessage());
        }
    }
}
