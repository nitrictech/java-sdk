package io.nitric.util;

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

import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import org.junit.jupiter.api.Test;

import io.nitric.proto.kv.v1.Key;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProtoUtilsTest {

    @Test
    public void test_toKeyMap() {
        try {
            ProtoUtils.toKeyMap(null);
            assertTrue(false);

        } catch (NullPointerException npe) {
            assertEquals("key parameter is required", npe.getMessage());
        }

        var keyMap = ProtoUtils.toKeyMap(Collections.emptyMap());
        assertNotNull(keyMap);
        assertTrue(keyMap.isEmpty());

        keyMap = ProtoUtils.toKeyMap(Map.of("key", "john.smith@server.com"));
        Key key = keyMap.get("key");
        assertNotNull(key);
        assertEquals("john.smith@server.com", key.getString());
        assertEquals(0, key.getNumber());

        keyMap = ProtoUtils.toKeyMap(Map.of("key", 12345));
        key = keyMap.get("key");
        assertNotNull(key);
        assertEquals("", key.getString());
        assertEquals(12345, key.getNumber());
    }

    public void test_fromKeyMap() {
        var keyMap = ProtoUtils.fromKeyMap(null);
        assertNull(keyMap);

        keyMap = ProtoUtils.fromKeyMap(Collections.emptyMap());
        assertNull(keyMap);

        var stringKey = Key.newBuilder().setString("string").build();
        var numberKey = Key.newBuilder().setNumber(1234567890L).build();

        var protoMap = Map.of("key", stringKey);
        keyMap = ProtoUtils.fromKeyMap(protoMap);
        assertNotNull(keyMap);
        assertEquals("string", keyMap.get("key"));

        protoMap = Map.of("key", numberKey);
        keyMap = ProtoUtils.fromKeyMap(protoMap);
        assertNotNull(keyMap);
        assertEquals(1234567890L, keyMap.get("key"));
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
