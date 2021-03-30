package io.nitric.util;

import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProtoUtilsTest {

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
