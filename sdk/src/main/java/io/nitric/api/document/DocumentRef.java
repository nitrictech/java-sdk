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

package io.nitric.api.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.nitric.api.NitricException;
import io.nitric.api.NotFoundException;
import io.nitric.proto.document.v1.DocumentDeleteRequest;
import io.nitric.proto.document.v1.DocumentGetRequest;
import io.nitric.proto.document.v1.DocumentGetResponse;
import io.nitric.proto.document.v1.DocumentSetRequest;
import io.nitric.util.Contracts;
import io.nitric.util.ProtoUtils;

import java.util.Map;

/**
 * Provides an Document Reference class.
 */
public class DocumentRef<T> {

    final Key key;
    final Class<T> type;
    ObjectMapper objectMapper;

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    DocumentRef(Key key, Class<T> type) {
        Contracts.requireNonNull(key, "key");
        Contracts.requireNonBlank(key.id, "key.id");
        Contracts.requireNonNull(type, "type");

        this.key = key;
        this.type = type;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the document's key.
     *
     * @return the document's key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Return the document reference content value.
     *
     * @return the document reference content value
     * @throws NotFoundException if the document was not found
     * @throws NitricException if a Document Service API error occurs
     */
    public T get() throws NotFoundException, NitricException {
        var request = DocumentGetRequest.newBuilder()
                .setKey(key.toGrpcKey())
                .build();

            DocumentGetResponse response = null;
            try {
                response = Documents.getServiceStub().get(request);
            } catch (io.grpc.StatusRuntimeException sre) {
                throw NitricException.build(sre);
            }

            if (response.hasDocument()) {
                var map = ProtoUtils.toMap(response.getDocument().getContent());

                if (type.isAssignableFrom(map.getClass())) {
                    return (T) map;

                } else {
                    if (objectMapper == null) {
                        objectMapper = new ObjectMapper();
                    }
                    return objectMapper.convertValue(map, type);
                }

            } else {
                return null;
            }
    }

    /**
     * Return the document reference content as JSON text.
     *
     * @return the document reference content as JSON text
     * @throws NotFoundException if the document was not found
     * @throws NitricException if a Document Service API error occurs
     */
    public String getJson() throws NotFoundException, NitricException {
        Object object = get();

        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return new ObjectMapper().writeValueAsString(object);

        } catch (JsonProcessingException jpe) {
            String msg = "error fetching JSON content for doc ref: " + getKey();
            throw new RuntimeException(msg, jpe);
        }
    }

    /**
     * Set the document content for this document reference in the database. If the
     * document does not exist an new item will be created, otherwise an
     * existing document will be update with the new value.
     *
     * @param content the document content to store (required)
     * @throws NitricException if a Document Service API error occurs
     */
    public void set(T content) throws NitricException {
        Contracts.requireNonNull(content, "content");

        // Marshal content Struct
        Map<String, Object> contentMap = null;
        if (content instanceof Map) {
            contentMap = (Map) content;

        } else {
            if (objectMapper == null) {
                objectMapper = new ObjectMapper();
            }
            contentMap = objectMapper.convertValue(content, Map.class);
        }
        var contentStruct = ProtoUtils.toStruct(contentMap);

        var request = DocumentSetRequest.newBuilder()
                .setKey(key.toGrpcKey())
                .setContent(contentStruct)
                .build();

        try {
            Documents.getServiceStub().set(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw NitricException.build(sre);
        }
    }

    /**
     * Delete this document reference from the database if it exists.
     *
     * @throws NitricException if a Document Service API error occurs
     */
    public void delete() throws NitricException {
        var request = DocumentDeleteRequest.newBuilder()
                .setKey(key.toGrpcKey())
                .build();

        try {
            Documents.getServiceStub().delete(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw NitricException.build(sre);
        }
    }

    /**
     * Create a new sub collection under this document.
     *
     * @param name the name of the sub collection (required)
     * @return a new sub collection for the parent collection
     */
    public Collection collection(String name) {
        Contracts.requireNonBlank(name, "name");

        if (key.collection.parent != null) {
            throw new IllegalArgumentException("cannot make a collection a under sub collection document");
        }

        return new Collection(name, key);
    }

    /**
     * Create a new sub collection query for this Document Ref.
     * The query object will have a <code>Map</code> value type.
     *
     * @param name the name of the sub collection (required)
     * @return a new collection query object
     */
    public Query<Map> query(String name) {
        Contracts.requireNonBlank(name, "name");

        if (key.collection.parent != null) {
            throw new IllegalArgumentException("Max collection depth 1 exceeded");
        }

        var collectionGroup = new CollectionGroup(name, key);
        return new Query<>(collectionGroup.toGrpcCollection(), Map.class);
    }

    /**
     * Create a new sub collection query for this Document Ref, and with the given value type.
     *
     * @param name the name of the sub collection (required)
     * @param type the query value type (required)
     * @return a new collection query object
     */
    public <K> Query<K> query(String name, Class<K> type) {
        Contracts.requireNonBlank(name, "name");
        Contracts.requireNonNull(type, "type");

        if (key.collection.parent != null) {
            throw new IllegalArgumentException("Max collection depth 1 exceeded");
        }

        var collectionGroup = new CollectionGroup(name, key);
        return new Query<>(collectionGroup.toGrpcCollection(), type);
    }

    /**
     * Set the Document content object marshalling ObjectMapper.
     *
     * @param objectMapper the Document content object marshalling ObjectMapper
     * @return this Document Ref object
     */
    public DocumentRef<T> objectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        return this;
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
            + "[key=" + key
            + ", type=" + type
            + ", objectMapper=" + objectMapper
            + "]";
    }

}
