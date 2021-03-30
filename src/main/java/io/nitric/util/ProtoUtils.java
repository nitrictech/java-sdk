package io.nitric.util;

import com.google.protobuf.ListValue;
import com.google.protobuf.NullValue;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  Provides protobuf utility functions.
 * </p>
 *
 * <p>
 *  This class has code derived from method in the <code>com.google.api.graphql.grpc</code> library
 * </p>
 *
 * @since 1.0
 */
public class ProtoUtils {

    /*
     * Enforce static method usage.
     */
    ProtoUtils() {
    }

    /**
     * Return a new Map built from the given struct.
     *
     * @param struct the protobuf Struct object
     * @return a Map object.
     */
    public static Map<String, Object> toMap(Struct struct) {
        Objects.requireNonNull(struct, "struct parameter is null");

        return struct
                .getFieldsMap()
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey(),
                                entry -> {
                                    Value value = entry.getValue();
                                    switch (value.getKindCase()) {
                                        case STRUCT_VALUE:
                                            return toMap(value.getStructValue());
                                        case LIST_VALUE:
                                            return toList(value.getListValue());
                                        default:
                                            return getScalarValue(value);
                                    }
                                }));

    }

    /**
     * Return a new Struct built from the given Map.
     *
     * @param map the map object
     * @return a new protobuf Struct object
     */
    public static Struct toStruct(Map<String, Object> map) {
        return mapToStructBuilder(map).build();
    }

    // Package Private Methods ------------------------------------------------

    static Object getScalarValue(Value value) {
        switch (value.getKindCase()) {
            case STRUCT_VALUE:
            case LIST_VALUE:
                throw new AssertionError("value should be scalar");
            case BOOL_VALUE:
                return value.getBoolValue();
            case NUMBER_VALUE:
                // Note: this assumes all numbers are doubles. Downstream code that have access to the
                // schema can convert this number to the correct number type.
                return value.getNumberValue();
            case STRING_VALUE:
                return value.getStringValue();
            default:
                break;
        }
        return value;
    }

    static List<Object> toList(ListValue listValue) {
        return listValue
                .getValuesList()
                .stream()
                .map(value -> Value.KindCase.STRUCT_VALUE.equals(value.getKindCase())
                                ? toMap(value.getStructValue())
                                : getScalarValue(value))
                .collect(Collectors.toList());
    }

    static Struct.Builder mapToStructBuilder(Map<String, Object> map) {
        Struct.Builder builder = Struct.newBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Value structValue = value(entry.getValue());
            builder.putFields(entry.getKey(), structValue);
        }
        return builder;
    }

    static Value value(Object value) {
        if (value instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) value;
            return Value.newBuilder().setStructValue(toStruct(map)).build();

        } else if (value instanceof Value) {
            return (Value) value;

        } else if (value instanceof String) {
            return Value.newBuilder().setStringValue((String) value).build();

        } else if (value instanceof Boolean) {
            return Value.newBuilder().setBoolValue((Boolean) value).build();

        } else if (value instanceof Number) {
            return Value.newBuilder().setNumberValue(((Number) value).doubleValue()).build();

        } else if (value instanceof Iterable<?>) {
            return listValue((Iterable<?>) value);

        } else if (value instanceof Struct) {
            return Value.newBuilder().setStructValue((Struct) value).build();

        } else if (value == null) {
            return Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build();

        } else {
            throw new IllegalArgumentException("Cannot convert " + value + " to a protobuf `Value`");
        }
    }

    static Value listValue(Iterable<?> value) {
        ListValue.Builder listValue = ListValue.newBuilder();
        for (Object item : value) {
            listValue.addValues(value(item));
        }
        return Value.newBuilder().setListValue(listValue.build()).build();
    }

}
