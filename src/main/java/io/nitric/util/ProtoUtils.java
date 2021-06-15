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

import com.google.protobuf.ListValue;
import com.google.protobuf.NullValue;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;

import io.nitric.proto.kv.v1.Key;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * <p>
 * Provides protobuf utility functions.
 * </p>
 *
 * <p>
 * This class has code derived from method in the <code>com.google.api.graphql.grpc</code> library
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
     * Return a proto API Key map from the given pojo map.
     *
     * @param key the source pojo map (required)
     * @return a proto API Key map
     */
    public static Map<String, Key> toKeyMap(final Map<String, Object> key) {
        Objects.requireNonNull(key, "key parameter is required");

        var protoKeyMap = new HashMap<String, Key>();

        key.entrySet().forEach(entry -> {
            String name = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                var keyValue = Key.newBuilder().setString(value.toString()).build();
                protoKeyMap.put(name, keyValue);

            } else if (value instanceof Long || value instanceof Integer) {
                var number = ((Number) value).longValue();
                var keyValue = Key.newBuilder().setNumber(number).build();
                protoKeyMap.put(name, keyValue);

            } else {
                String msg = String.format("key %s value type is not supported %s",
                    name,
                    value.getClass().getName());
                throw new IllegalArgumentException(msg);
            }
        });

        return protoKeyMap;
    }

    /**
     * Return a pojo map from the given proto API Key map. Please note if the given keyMap is null
     * or empty this method will return a null map.
     *
     * @param keyMap the source proto key map
     * @return a pojo map if the source key map is not null and contains entries, otherwise returns null
     */
    public static Map<String, Object> fromKeyMap(final  Map<String, Key> keyMap) {
        if (keyMap == null || keyMap.isEmpty()) {
            return null;
        }

        var pojoMap = new HashMap<String, Object>();

        keyMap.keySet().forEach(keyName -> {
            Key key = keyMap.get(keyName);
            // Review if more key types added, this conditional check is fragile
            if (key.getString().isEmpty()) {
                pojoMap.put(keyName, key.getNumber());
            } else {
                pojoMap.put(keyName, key.getString());
            }
        });

        return pojoMap;
    }

    /**
     * Return a new Map built from the given struct.
     *
     * @param struct the protobuf Struct object
     * @return a Map object.
     */
    public static Map<String, Object> toMap(Struct struct) {
        Objects.requireNonNull(struct, "struct parameter is required");

        return struct
                .getFieldsMap()
                .entrySet()
                .stream()
                .collect(
                        toMapNullFriendly(
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
     * @param map the map object (required)
     * @return a new protobuf Struct object
     */
    public static Struct toStruct(Map<String, Object> map) {
        Objects.requireNonNull(map, "map parameter is required");

        return mapToStructBuilder(map).build();
    }

    // Package Private Methods ------------------------------------------------

    static Struct toStructNullFriendly(Map<String, Object> map) {
        return mapToStructBuilder(map).build();
    }

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
            case NULL_VALUE:
                return null;
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
            return Value.newBuilder().setStructValue(toStructNullFriendly(map)).build();

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

    static <T, K, U> Collector<T, ?, Map<K, U>> toMapNullFriendly(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper) {

        @SuppressWarnings("unchecked")
        U none = (U) new Object();
        return Collectors.collectingAndThen(
                Collectors.<T, K, U>toMap(keyMapper,
                        valueMapper.andThen(v -> v == null ? none : v)), map -> {
                    map.replaceAll((k, v) -> v == none ? null : v);
                    return map;
                });
    }

}
