package io.nitric.api.document;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.nitric.proto.document.v1.DocumentQueryRequest;
import io.nitric.proto.document.v1.DocumentQueryResponse;
import io.nitric.proto.document.v1.ExpressionValue;
import io.nitric.util.Contracts;
import io.nitric.util.ProtoUtils;

/**
 * <p>
 *  Provides an document Query class.
 * </p>
 */
public class Query<T> {

    final DocColl collection;
    final List<Expression> expressions = new ArrayList<Expression>();
    int limit;
    Map<String, String> pagingToken;
    final Class<T> type;

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    Query(DocColl collection, Class<T> type) {
        Contracts.requireNonNull(collection, "collection");
        Contracts.requireNonNull(type, "type");

        this.collection = collection;
        this.type = type;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * <p>
     * Add a where expression to the query. Valid expression operators include:
     * <code> == , &lt; , &gt; , &lt;= , &gt;= , startsWith</code>
     * </p>
     *
     * <p>
     * Queries with multiple <code>where()</code> expressions are implicitly AND together when executed.
     * </p>
     *
     * @param operand the left hand side expression operand (required)
     * @param operator the query expression operator (required)
     * @param value the right hand side operand (required)
     * @return the Query operation
     */
    public Query<T> where(String operand, String operator, String value) {
        Contracts.requireNonBlank(operand, "operand");
        Contracts.requireNonBlank(operator, "operator");
        Contracts.requireNonBlank(value, "value");

        expressions.add(new Expression(operand, operator, value));

        return this;
    }

    /**
     * <p>
     * Add a where expression to the query. Valid expression operators include:
     * <code> == , &lt; , &gt; , &lt;= , &gt;= , startsWith</code>
     * </p>
     *
     * <p>
     * Queries with multiple <code>where()</code> expressions are implicitly AND together when executed.
     * </p>
     *
     * @param operand the left hand side expression operand (required)
     * @param operator the query expression operator (required)
     * @param value the right hand side operand (required)
     * @return the Query operation
     */
    public Query<T> where(String operand, String operator, Double value) {
        Contracts.requireNonBlank(operand, "operand");
        Contracts.requireNonBlank(operator, "operator");
        Contracts.requireNonNull(value, "value");

        expressions.add(new Expression(operand, operator, value));

        return this;
    }

    /**
     * <p>
     * Add a where expression to the query. Valid expression operators include:
     * <code> == , &lt; , &gt; , &lt;= , &gt;= , startsWith</code>
     * </p>
     *
     * <p>
     * Queries with multiple <code>where()</code> expressions are implicitly AND together when executed.
     * </p>
     *
     * @param operand the left hand side expression operand (required)
     * @param operator the query expression operator (required)
     * @param value the right hand side operand (required)
     * @return the Query operation
     */
    public Query<T> where(String operand, String operator, Integer value) {
        Contracts.requireNonBlank(operand, "operand");
        Contracts.requireNonBlank(operator, "operator");
        Contracts.requireNonNull(value, "value");

        expressions.add(new Expression(operand, operator, value));

        return this;
    }

    /**
     * <p>
     * Add a where expression to the query. Valid expression operators include:
     * <code> == , &lt; , &gt; , &lt;= , &gt;= , startsWith</code>
     * </p>
     *
     * <p>
     * Queries with multiple <code>where()</code> expressions are implicitly AND together when executed.
     * </p>
     *
     * @param operand the left hand side expression operand (required)
     * @param operator the query expression operator (required)
     * @param value the right hand side operand (required)
     * @return the Query operation
     */
    public Query<T> where(String operand, String operator, Boolean value) {
        Contracts.requireNonBlank(operand, "operand");
        Contracts.requireNonBlank(operator, "operator");
        Contracts.requireNonNull(value, "value");

        expressions.add(new Expression(operand, operator, value));

        return this;
    }

    /**
     * Set the query paging continuation token.
     *
     * @param pagingToken the query paging continuation token
     * @return the Query operation
     */
    public Query<T> pagingFrom(Map<String, String> pagingToken) {
        this.pagingToken = pagingToken;
        return this;
    }

    /**
     * Set the query fetch limit.
     *
     * @param limit the query fetch limit
     * @return the Query operation
     */
    public Query<T> limit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Perform the Query operation and return the fetched results. If a fetch limit is specified then only the
     * specified number of items will be returned. If a pagingToken is also specified then the results will be for the
     * next page of results from the token offset.
     *
     * @return the Query operations fetched results.
     */
    public QueryResult<T> fetch() {
        return new QueryResult<T>(this, false);
    }

    /**
     * Perform the Query operation and return all the fetched results. The QueryResult iterator will continue to process
     * all the pages of results until no more are available from the server. If no fetch limit is specified, this
     * method will set the query fetch limit to 1000.
     *
     * @return the Query operations fetched results.
     */
    public QueryResult<T> fetchAll() {
        if (this.limit <= 0) {
            this.limit = 1000;
        }

        return new QueryResult<T>(this, true);
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[collection=" + collection
                + ", expressions=" + expressions
                + ", limit=" + limit
                + ", pagingToken=" + pagingToken
                + ", type=" + type
                + "]";
    }

    // Inner Classes ----------------------------------------------------------

    /**
     * Provides a Query Result class.
     *
     * <p>
     * The example below illustrates using the <code>QueryResults</code> class.
     * </p>
     *
     * <pre><code class="code">
     *  import com.example.model.Order;
     *  import io.nitric.api.kv.KeyValueClient;
     *  import io.nitric.api.kv.Query.QueryResult;
     *  import java.util.Map;
     *  ...
     *
     *  // Create a 'orders' collection KV client
     *  KeyValueClient client = KeyValueClient.build(Order.class, "orders");
     *
     *  // Fetch first 100 orders records
     *  QueryResult results = client.newQuery()
     *      .limit(100)
     *      .fetch();
     *
     *  results.foreach(order -&gt; {
     *      // Process order...
     *  });
     * </code></pre>
     */
    public static class QueryResult<T> implements Iterable<T> {

        final Query<T> query;
        final boolean paginateAll;
        Map<String, String> pagingToken;
        List<T> queryData;

        /**
         * Create a QueryResult object.
         *
         * @parma query the query to continue
         * @param paginateAll specify whether the iterator paginate through all results
         */
        QueryResult(Query<T> query, boolean paginateAll) {

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
         * @return a typed query results iterator
         */
        @Override
        public Iterator<T> iterator() {
            if (!paginateAll) {
                return queryData.iterator();

            } else {
                return new PagingIterator<T>(this);
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

        protected DocumentQueryRequest buildDocQueryRequest(List<Expression> expressions) {
            var requestBuilder = DocumentQueryRequest.newBuilder()
                    .setCollection(query.collection.toCollection());

            expressions.forEach(e -> {
                var exp = io.nitric.proto.document.v1.Expression.newBuilder()
                        .setOperand(e.operand)
                        .setOperator(e.operator)
                        .setValue(e.toExpressionValue())
                        .build();
                requestBuilder.addExpressions(exp);
            });

            requestBuilder.setLimit(query.limit);

            if (this.pagingToken != null) {
                requestBuilder.putAllPagingToken(this.pagingToken);
            }

            return requestBuilder.build();
        }

        @SuppressWarnings({"unchecked"})
        protected void loadPageData(DocumentQueryResponse response) {

            // Marshall response data
            queryData = new ArrayList<T>(response.getDocumentsCount());

            var objectMapper = new ObjectMapper();

            response.getDocumentsList().forEach(doc -> {
                var map = ProtoUtils.toMap(doc.getContent());

                if (query.type.isAssignableFrom(map.getClass())) {
                    queryData.add((T) map);

                } else {
                    var value = (T) objectMapper.convertValue(map, query.type);
                    queryData.add(value);
                }
            });

            this.pagingToken = response.getPagingTokenMap();
        }
    }

    // Package Private Classes ------------------------------------------------

    static class PagingIterator<T> implements Iterator<T> {

        private QueryResult<T> queryResult;
        private int index = 0;

        public PagingIterator(QueryResult<T> queryResult) {
            this.queryResult = queryResult;
        }

        @Override
        public boolean hasNext() {

            if (index < queryResult.queryData.size()) {
                return true;

            } else if (index == queryResult.queryData.size()) {

                if (queryResult.pagingToken != null && !queryResult.pagingToken.isEmpty()) {
                    index = 0;

                    // Load next page of data
                    var request = queryResult.buildDocQueryRequest(queryResult.query.expressions);

                    DocumentQueryResponse response = null;
                    try {
                        response = Documents.getServiceStub().query(request);
                    } catch (io.grpc.StatusRuntimeException sre) {
                        throw ProtoUtils.mapGrpcError(sre);
                    }

                    queryResult.loadPageData(response);

                    return queryResult.queryData.size() > 0;

                } else {
                    return false;
                }

            } else {
                return false;
            }
        }

        @Override
        public T next() {
            return queryResult.queryData.get(index++);
        }
    }

    static class Expression {
        final String operand;
        final String operator;
        final Object value;

        Expression(String operand, String operator, Object value) {
            this.operand = operand;
            this.operator = operator;
            this.value = value;
        }

        /**
         * @return a mapped GRPC ExpressionValue object
         */
        ExpressionValue toExpressionValue() {
            if (value instanceof String) {
                return ExpressionValue.newBuilder().setStringValue(value.toString()).build();
            } else if (value instanceof Double) {
                return ExpressionValue.newBuilder().setDoubleValue((Double) value).build();
            } else if (value instanceof Integer) {
                return ExpressionValue.newBuilder().setIntValue((Integer) value).build();
            } else if (value instanceof Boolean) {
                return ExpressionValue.newBuilder().setBoolValue((Boolean) value).build();
            } else {
                String msg = value.getClass().getSimpleName()
                        + " type is not supported. Please use: string, double, integer, boolean";
                throw new IllegalArgumentException(msg);
            }
        }

        @Override
        public String toString() {
            return getClass().getSimpleName()
                    + "[operand=" + operand
                    + ", operator=" + operator
                    + ", value=" + value
                    + "]";
        }
    }
}