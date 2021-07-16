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
public class StorageTest {

    private static final String KNOWN_KEY = "123456";
    private static final String KNOWN_TEXT = "hello world";
    private static final ByteString KNOWN_BS = ByteString.copyFrom(KNOWN_TEXT .getBytes(StandardCharsets.UTF_8));

    @Test
    public void test_serviceStub() {
        assertNotNull(Storage.getServiceStub());

        var mock = Mockito.mock(StorageServiceGrpc.StorageServiceBlockingStub.class);

        Storage.setServiceStub(mock);
        assertEquals(mock, Storage.getServiceStub());
    }

    @Test
    public void test_bucket() {
        var bucket = Storage.bucket("images");

        assertNotNull(bucket);
        assertEquals("images", bucket.name);
        assertEquals("images", bucket.getName());
        assertEquals("Bucket[name=images]", bucket.toString());

        try {
            Storage.bucket(" ");
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-blank name", iae.getMessage());
        }
    }

    @Test
    public void test_read() {
        var mock = Mockito.mock(StorageServiceGrpc.StorageServiceBlockingStub.class);
        Mockito.when(mock.read(Mockito.any(StorageReadRequest.class))).thenReturn(
                StorageReadResponse.newBuilder().setBody(KNOWN_BS).build()
        );
        Storage.setServiceStub(mock);
        var bucket = Storage.bucket("bucket");

        byte[] data = bucket.read("ANY KEY");
        assertNotNull(data);
        assertEquals(KNOWN_TEXT, new String(data));

        try {
            bucket.read(" ");
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-blank key", iae.getMessage());
        }
    }

    @Test
    public void test_read_null_key() {
        try {
            Storage.bucket("bucket").read(null);
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-null key", iae.getMessage());
        }
    }

    @Test
    public void test_read_unknown_key() {
        var mock = Mockito.mock(StorageServiceGrpc.StorageServiceBlockingStub.class);
        Mockito.when(mock.read(Mockito.any(StorageReadRequest.class))).thenReturn(
            StorageReadResponse.newBuilder().build()
        );
        Storage.setServiceStub(mock);

        var data = Storage.bucket("bucket").read("unknown key");
        assertNull(data);
    }

    @Test
    public void test_write() {
        var mock = Mockito.mock(StorageServiceGrpc.StorageServiceBlockingStub.class);
        Mockito.when(mock.write(Mockito.any(StorageWriteRequest.class))).thenReturn(
                StorageWriteResponse.newBuilder().build()
        );
        Storage.setServiceStub(mock);
        var bucket = Storage.bucket("bucket");

        byte[] data = "this data".getBytes(StandardCharsets.UTF_8);
        bucket.write("this key", data);

        // Verify we actually called the mock object
        // TODO: Use Mockito.eq for testing here...
        Mockito.verify(mock).write(Mockito.any());

        try {
            bucket.write(null, data);
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-null key", iae.getMessage());
        }

        try {
            bucket.write("this key", null);
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-null data", iae.getMessage());
        }
    }

    @Test
    public void test_delete() {
        var mock = Mockito.mock(StorageServiceGrpc.StorageServiceBlockingStub.class);
        Mockito.when(mock.delete(Mockito.any(StorageDeleteRequest.class))).thenReturn(
                StorageDeleteResponse.newBuilder().build()
        );
        Storage.setServiceStub(mock);

        var bucket = Storage.bucket("bucket");

        bucket.delete(KNOWN_KEY);

        Mockito.verify(mock).delete(Mockito.any());

        try {
            bucket.delete(null);
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-null key", iae.getMessage());
        }
    }

}