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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nitric.proto.document.v1.DocumentQueryRequest;
import io.nitric.proto.document.v1.DocumentQueryResponse;
import io.nitric.util.ProtoUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Provides an iterable Query Result class.
 *
 * @see Query
 * @see ResultDoc
 */
public class QueryResults<T> implements Iterable<ResultDoc<T>> {

    final Query<T> query;
    final boolean paginateAll;
    Map<String, String> pagingToken;
    List<ResultDoc<T>> queryData;

    /**
     * Create a QueryResults object.
     *
     * @param paginateAll specify whether the iterator paginate through all results
     * @parma query the query to continue
     */
    QueryResults(Query<T> query, boolean paginateAll) {

        this.query = query;
        this.pagingToken = query.pagingToken;
        this.paginateAll = paginateAll;

        // Perform initial query
        var request = buildDocQueryRequest(this.query.expressions);

        DocumentQueryResponse response = null;
        try {
            response = Documents.getServiceStub().query(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw ProtoUtils.mapGrpcError(sre);
        }

        loadPageData(response);
    }

    /**
     * Return a typed query results iterator.
     *
     * @return a typed query results iterator
     */
    @Override
    public Iterator<ResultDoc<T>> iterator() {
        if (!paginateAll) {
            return queryData.iterator();

        } else {
            return new PagingIterator<>(this);
        }
    }

    /**
     * Return the query paging continuation token if there are more items available. If this value is null
     * then no further query results are available.
     *
     * @return the query paging continuation token
     */
    public Map<String, String> getPagingToken() {
        return pagingToken.isEmpty() ? null : pagingToken;
    }

    // Protected Methods --------------------------------------------------

    protected DocumentQueryRequest buildDocQueryRequest(List<Query.Expression> expressions) {
        var requestBuilder = DocumentQueryRequest.newBuilder()
                .setCollection(query.collection.toGrpcCollection());

        expressions.forEach(e -> {
            var exp = io.nitric.proto.document.v1.Expression.newBuilder()
                    .setOperand(e.operand)
                    .setOperator(e.operator)
                    .setValue(e.toExpressionValue())
                    .build();
            requestBuilder.addExpressions(exp);
        });

        int fetchLimit = (paginateAll) ? 1000 : query.limit;
        requestBuilder.setLimit(fetchLimit);

        if (this.pagingToken != null) {
            requestBuilder.putAllPagingToken(this.pagingToken);
        }

        return requestBuilder.build();
    }

    @SuppressWarnings({"unchecked"})
    protected void loadPageData(DocumentQueryResponse response) {

        // Marshall response data
        queryData = new ArrayList<>(response.getDocumentsCount());

        var objectMapper = new ObjectMapper();

        response.getDocumentsList().forEach(doc -> {
            var key = Key.buildFromGrpcKey(doc.getKey());
            var map = ProtoUtils.toMap(doc.getContent());

            if (query.type.isAssignableFrom(map.getClass())) {
                queryData.add(new ResultDoc(key, map));

            } else {
                var value = objectMapper.convertValue(map, query.type);
                queryData.add(new ResultDoc<>(key, value));
            }
        });

        this.pagingToken = response.getPagingTokenMap();
    }

    // Package Private Classes ------------------------------------------------

    static class PagingIterator<T> implements Iterator<ResultDoc<T>> {

        private QueryResults<T> queryResults;
        private int index = 0;

        public PagingIterator(QueryResults<T> queryResults) {
            this.queryResults = queryResults;
        }

        @Override
        public boolean hasNext() {

            if (index < queryResults.queryData.size()) {
                return true;

            } else if (index == queryResults.queryData.size()) {

                if (queryResults.pagingToken != null && !queryResults.pagingToken.isEmpty()) {
                    index = 0;

                    // Load next page of data
                    var request = queryResults.buildDocQueryRequest(queryResults.query.expressions);

                    DocumentQueryResponse response = null;
                    try {
                        response = Documents.getServiceStub().query(request);
                    } catch (io.grpc.StatusRuntimeException sre) {
                        throw ProtoUtils.mapGrpcError(sre);
                    }

                    queryResults.loadPageData(response);

                    return !queryResults.queryData.isEmpty();

                } else {
                    return false;
                }

            } else {
                return false;
            }
        }

        @Override
        public ResultDoc<T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator has no more elements");
            }
            return queryResults.queryData.get(index++);
        }
    }

}