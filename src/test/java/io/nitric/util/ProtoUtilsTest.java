package io.nitric.util;

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

import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class ProtoUtilsTest {

    @Test
    public void test_mapGrpcError() {
        var re1= ProtoUtils.mapGrpcError(null);
        assertNotNull(re1);
        assertTrue(re1 instanceof NullPointerException);

        var re2 = ProtoUtils.mapGrpcError(new StatusRuntimeException(Status.INVALID_ARGUMENT, null));
        assertNotNull(re2);
        assertTrue(re2 instanceof IllegalArgumentException);

        var re3 = ProtoUtils.mapGrpcError(new StatusRuntimeException(Status.INTERNAL, null));
        assertNotNull(re3);
        assertFalse(re3 instanceof IllegalArgumentException);
    }

    @Test
    public void test_toStruct() {
        Map<String, Object> map = Map.of("field1", "value1");

        var struct = ProtoUtils.toStruct(map);
        assertNotNull(struct);
        assertEquals("value1", struct.getFieldsMap().get("field1").getStringValue());

        try {
            ProtoUtils.toStruct(null);
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("map parameter is required", npe.getMessage());
        }
    }

    @Test
    public void test_toMap() {
        var value = Value.newBuilder().setStringValue("value1").build();

        var fields = Map.of("field1", value);
        var struct = Struct.newBuilder().putAllFields(fields).build();

        var map = ProtoUtils.toMap(struct);
        assertNotNull(map);
        assertEquals("value1", map.get("field1"));

        try {
            ProtoUtils.toMap(null);
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("struct parameter is required", npe.getMessage());
        }
    }

}
