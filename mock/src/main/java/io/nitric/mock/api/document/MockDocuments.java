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

package io.nitric.mock.api.document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.mockito.Mockito;

import io.grpc.StatusRuntimeException;
import io.nitric.api.document.Documents;
import io.nitric.proto.document.v1.Document;
import io.nitric.proto.document.v1.DocumentDeleteResponse;
import io.nitric.proto.document.v1.DocumentGetResponse;
import io.nitric.proto.document.v1.DocumentQueryResponse;
import io.nitric.proto.document.v1.DocumentServiceGrpc;
import io.nitric.proto.document.v1.DocumentSetResponse;
import io.nitric.util.Contracts;
import io.nitric.util.ProtoUtils;

/**
 * <p>
 *  Provides a Nitric Document Service API Mockito helper class.
 * </p>
 */
public class MockDocuments {

    DocumentServiceGrpc.DocumentServiceBlockingStub mock;

    /**
     * Create a new MockDocuments object.
     */
    public MockDocuments() {
        mock = Mockito.mock(DocumentServiceGrpc.DocumentServiceBlockingStub.class);
        Documents.setServiceStub(mock);
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the Mockito DocumentService stub.
     *
     * @return the Mockito DocumentService stub
     */
    public DocumentServiceGrpc.DocumentServiceBlockingStub getMock() {
        return mock;
    }

    /**
     * Specify what to return when the DocumentService Get method is invoked.
     *
     * @return the MockDocuments object (required)
     */
    public MockDocuments whenGet(Object object) {
        Contracts.requireNonNull(object, "object");

        var map = new ObjectMapper().convertValue(object, Map.class);
        var struct = ProtoUtils.toStruct(map);

        var document = Document.newBuilder().setContent(struct).build();

        Mockito.when(mock.get(Mockito.any())).thenReturn(
                DocumentGetResponse
                    .newBuilder()
                    .setDocument(document)
                    .build()
        );

        return this;
    }

    /**
     * Specify the error to throw when the DocumentService Get method is invoked.
     *
     * @param status the GRPC error status (required)
     * @return the MockDocuments object
     */
    public MockDocuments whenGetError(io.grpc.Status status) {
        Contracts.requireNonNull(status, "status");

        Mockito.when(mock.get(Mockito.any())).thenThrow(
                new StatusRuntimeException(status)
        );

        return this;
    }

    /**
     * Enable the DocumentService Set method for unit testing.
     *
     * @return the MockDocuments object
     */
    public MockDocuments whenSet() {
        Mockito.when(mock.set(Mockito.any())).thenReturn(
                DocumentSetResponse.newBuilder().build()
        );
        return this;
    }

    /**
     * Specify the error to throw when the DocumentService Set method is invoked.
     *
     * @param status the GRPC error status (required)
     * @return the MockDocuments object
     */
    public MockDocuments whenSetError(io.grpc.Status status) {
        Contracts.requireNonNull(status, "status");

        Mockito.when(mock.set(Mockito.any())).thenThrow(
                new StatusRuntimeException(status)
        );

        return this;
    }

    /**
     * Enable the DocumentService Delete method for unit testing.
     *
     * @return the MockDocuments object
     */
    public MockDocuments whenDelete() {
        Mockito.when(mock.delete(Mockito.any())).thenReturn(
                DocumentDeleteResponse.newBuilder().build()
        );
        return this;
    }

    /**
     * Specify the error to throw when the DocumentService Delete method is invoked.
     *
     * @param status the GRPC error status (required)
     * @return the MockDocuments object
     */
    public MockDocuments whenDeleteError(io.grpc.Status status) {
        Contracts.requireNonNull(status, "status");

        Mockito.when(mock.delete(Mockito.any())).thenThrow(
                new StatusRuntimeException(status)
        );

        return this;
    }

    /**
     * Enable the DocumentService Query method for unit testing. The service
     * will return an empty result set.
     *
     * @return the MockDocuments object
     */
    public MockDocuments whenQuery() {
        Mockito.when(mock.query(Mockito.any())).thenReturn(
                DocumentQueryResponse.newBuilder().build()
        );

        return this;
    }

    /**
     * Specify the results to return when the DocumentService Query method is invoked.
     *
     * @param collection the document collection name (required)
     * @param results the result set list (required)
     * @return the MockDocuments object
     */
    public MockDocuments whenQuery(String collection, List results) {
        Contracts.requireNonBlank(collection, "collection");
        Contracts.requireNonNull(results, "results");

        var documents = createDocuments(collection, results);

        Mockito.when(mock.query(Mockito.any())).thenReturn(
                DocumentQueryResponse.newBuilder().addAllDocuments(documents).build()
        );

        return this;
    }

    /**
     * Specify the error to throw when the DocumentService Query method is invoked.
     *
     * @param status the GRPC error status
     * @return the MockDocuments object
     */
    public MockDocuments whenQueryError(io.grpc.Status status) {
        Contracts.requireNonNull(status, "status");

        Mockito.when(mock.query(Mockito.any())).thenThrow(
                new StatusRuntimeException(status)
        );

        return this;
    }

    // Protected Methods ------------------------------------------------------

    private List<Document> createDocuments(String collectionName, List objects) {

        var collection = io.nitric.proto.document.v1.Collection
                .newBuilder()
                .setName(collectionName)
                .build();

        List<Document> results = new ArrayList<>();

        var objectMapper = new ObjectMapper();

        for (int i = 0; i < objects.size(); i++) {
            var object = objects.get(i);

            var key = io.nitric.proto.document.v1.Key
                    .newBuilder()
                    .setId(String.valueOf(i + 1))
                    .setCollection(collection)
                    .build();

            var contentMap = objectMapper.convertValue(object, Map.class);
            var contentStruct = ProtoUtils.toStruct(contentMap);
            var document = Document.newBuilder()
                    .setKey(key)
                    .setContent(contentStruct)
                    .build();
            results.add(document);
        }

        return results;
    }

}
