package io.nitric.api.document;

import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nitric.proto.document.v1.DocumentDeleteRequest;
import io.nitric.proto.document.v1.DocumentGetRequest;
import io.nitric.proto.document.v1.DocumentGetResponse;
import io.nitric.proto.document.v1.DocumentSetRequest;
import io.nitric.util.Contracts;
import io.nitric.util.ProtoUtils;

/**
 * Provides an abstract Document Reference class.
 */
public abstract class AbstractDocRef<T> {

    final DocKey key;
    final Class<T> type;

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    AbstractDocRef(DocKey key, Class<T> type) {
        Contracts.requireNonNull(key, "key");
        Contracts.requireNonBlank(key.id, "key.id");
        Contracts.requireNonNull(type, "type");

        this.key = key;
        this.type = type;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * @return the document's unique id
     */
    public String id() {
        return key.id;
    }

    /**
     * Return the collection document reference value.
     *
     * @return the collection document reference value, or null if not found
     */
    public T get() {
        var request = DocumentGetRequest.newBuilder()
                .setKey(key.toKey())
                .build();

            DocumentGetResponse response = null;
            try {
                response = Documents.getServiceStub().get(request);
            } catch (io.grpc.StatusRuntimeException sre) {
                throw ProtoUtils.mapGrpcError(sre);
            }

            if (response.hasDocument()) {
                var map = ProtoUtils.toMap(response.getDocument().getContent());

                if (type.isAssignableFrom(map.getClass())) {
                    return (T) map;

                } else {
                    var objectMapper = new ObjectMapper();
                    return (T) objectMapper.convertValue(map, type);
                }

            } else {
                return null;
            }
    }

    /**
     * Set the document content for this document reference in the database. If the
     * document does not exist an new item will be created, otherwise an
     * existing document will be update with the new value.
     *
     * @param content the document content to store (required)
     */
    public void set(T content) {
        Contracts.requireNonNull(content, "content");

        // Marshal content Struct
        Map<String, Object> contentMap = null;
        if (content instanceof Map) {
            contentMap = (Map) content;

        } else {
            var objectMapper = new ObjectMapper();
            contentMap = objectMapper.convertValue(content, Map.class);
        }
        var contentStruct = ProtoUtils.toStruct(contentMap);

        var request = DocumentSetRequest.newBuilder()
                .setKey(key.toKey())
                .setContent(contentStruct)
                .build();

        try {
            Documents.getServiceStub().set(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw ProtoUtils.mapGrpcError(sre);
        }
    }

    /**
     * Delete this document reference from the database if it exists.
     */
    public void delete() {
        var request = DocumentDeleteRequest.newBuilder()
                .setKey(key.toKey())
                .build();

        try {
            Documents.getServiceStub().delete(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw ProtoUtils.mapGrpcError(sre);
        }
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[key=" + key + ", type=" + type + "]";
    }

}