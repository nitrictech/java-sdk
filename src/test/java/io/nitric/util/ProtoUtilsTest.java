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

package io.nitric.util;

import java.util.Map;

import com.google.protobuf.NullValue;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;

import org.junit.Test;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import static org.junit.Assert.*;

public class ProtoUtilsTest {

    @Test
    public void test_toStruct() {
        Map<String, Object> map = Map.of("field1", "value1");

        var struct = ProtoUtils.toStruct(map);
        assertNotNull(struct);
        assertEquals("value1", struct.getFieldsMap().get("field1").getStringValue());

        try {
            ProtoUtils.toStruct(null);
            fail();

        } catch (IllegalArgumentException iae) {
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
            fail();

        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void test_getScalarValue_bool() {
        var origVal = Value.newBuilder()
                .setBoolValue(true)
                .build();
        var val = ProtoUtils.getScalarValue(origVal);

        assertEquals(val, origVal.getBoolValue());
    }

    @Test
    public void test_getScalarValue_number() {
        var origVal = Value.newBuilder()
                .setNumberValue(100)
                .build();
        var val = ProtoUtils.getScalarValue(origVal);

        assertEquals(val, origVal.getNumberValue());
    }

    @Test
    public void test_getScalarValue_string() {
        var origVal = Value.newBuilder()
                .setStringValue("test")
                .build();
        var val = ProtoUtils.getScalarValue(origVal);

        assertEquals(val, origVal.getStringValue());
    }

    @Test
    public void test_getScalarValue_null() {
        var origVal = Value.newBuilder()
                .setNullValue(NullValue.NULL_VALUE)
                .build();
        var val = ProtoUtils.getScalarValue(origVal);

        assertNull(val);
    }

}
