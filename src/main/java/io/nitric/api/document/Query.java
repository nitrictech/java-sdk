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
 *  Provides a Document Query class.
 * </p>
 *
 * <h3>Query Examples</h3>
 *
 * <p>
 *  The example below illustrates using the Document <code>Query</code> class.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.document.Documents;
 *  import io.nitric.api.document.Key;
 *  import io.nitric.api.document.QueryResults;
 *  import java.util.Map;
 *
 *  // Fetch the first 100 customers
 *  QueryResults results = Documents.collection("customers")
 *      .query()
 *      .limit(100)
 *      .fetch();
 *
 *  // Process results
 *  results.foreach(doc -&gt; {
 *      Key key = doc.getKey();
 *      Map customer = doc.getContent();
 *  });
 * </code></pre>
 *
 * <p>
 *  In the example a <code>QueryResults</code> object is returned. The QueryResults performs a
 *  lazy execution of the where when it is iterated over in the <code>foreach</code>. The query results also
 *  return a typed <code>ResultDoc</code> object which provides access to the documents content and key.
 * </p>
 *
 * <p>
 *   To use typed results content class, specify the content type in the query method. The example code
 *   marshals the document contents into the specified <code>Customer</code> class. If a query type is
 *   not specified a Map type is used.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.document.Documents;
 *  import io.nitric.api.document.QueryResults;
 *  import com.example.entity.Customer;
 *
 *  QueryResults results = Documents.collection("customers")
 *      .query(Customer.class)
 *      .fetch();
 *
 *  results.foreach(doc -&gt; {
 *      Customer customer = doc.getContent();
 *  });
 * </code></pre>
 *
 * <h3>Sub Collection Queries</h3>
 *
 * <p>
 *  You can create a sub collection query under a top level collection or a top level collection document.
 *  For example:
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.document.Documents;
 *  import io.nitric.api.document.QueryResults;
 *  import com.example.entity.Order;
 *
 *  // Fetch all customers orders
 *  QueryResults results = Documents.collection("customers").collection("orders")
 *      .query(Order.class)
 *      .fetch();
 *
 *  results.foreach(doc -&gt; {
 *      Order order = doc.getContent();
 *  });
 *
 *  // Fetch all orders for a specific customer
 *  QueryResults results = Documents.collection("customers").doc("jane-smith@server.com")
 *      .collection("orders")
 *      .query(Order.class)
 *      .fetch();
 *
 *  results.foreach(doc -&gt; {
 *      Order order = doc.getContent();
 *  });
 * </code></pre>
 *
 * <h3>Where Filter Expressions</h3>
 *
 * <p>
 *  The Query class supports chaining <code>where</code> clause filter expressions.
 *  All where clauses are explicitly AND together.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.document.Documents;
 *  import io.nitric.api.document.QueryResults;
 *
 *  // Fetch all the customers based in US which are active and over 21
 *  QueryResults results = Documents.collection("customers")
 *      .query()
 *      .where("status", "==", "active")
 *      .where("country", "==", "US")
 *      .where("age", "&gt;", "21")
 *      .fetch();
 * </code></pre>
 *
 * <p>The available where clause operators are provide listed below:</p>
 *
 * <table class="striped">
 *     <tr>
 *         <th style="font-weight:bold">Operator</th>
 *         <th style="font-weight:bold">Description</th>
 *         <th style="font-weight:bold">Example</th>
 *     </tr>
 *     <tr>
 *         <td style="text-align:center"><code>==</code></td>
 *         <td>Equals</td>
 *         <td><code>.where("status", "==", "active")</code></td>
 *     </tr>
 *     <tr>
 *         <td style="text-align:center"><code>&lt;</code></td>
 *         <td>Less Than</td>
 *         <td><code>.where("count", "&lt;", 10)</code></td>
 *     </tr>
 *     <tr>
 *         <td style="text-align:center"><code>&gt;</code></td>
 *         <td>Greater Than</td>
 *         <td><code>.where("price", "&gt;", 100.0)</code></td>
 *     </tr>
 *     <tr>
 *         <td style="text-align:center"><code>&lt;=</code></td>
 *         <td>Less Than or Equals</td>
 *         <td><code>.where("count", "&lt;", 10)</code></td>
 *     </tr>
 *     <tr>
 *         <td style="text-align:center"><code>&gt;=</code></td>
 *         <td>Greater Than or Equals</td>
 *         <td><code>.where("price", "&gt;=", 50.0)</code></td>
 *     </tr>
 *     <tr>
 *         <td style="text-align:center"><code>startsWith</code></td>
 *         <td>Starts With</td>
 *         <td><code>.where("type", "startsWith", "Inverter/Hybrid")</code></td>
 *     </tr>
 * </table>
 *
 * <h3>Large Result Sets</h3>
 *
 * <p>
 *   When processing large result sets the Query API provides two options. The first is to use a paginated query
 *   where you pass pagingToken from the previous query results to the next query to continue. This technique is
 *   useful for paged user experiences. An example is provided below:
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.document.Documents;
 *  import io.nitric.api.document.QueryResults;
 *  import java.util.Map;
 *
 *  Query query = Documents.collection("customers")
 *      .query()
 *      .where("status", "==", "active")
 *      .limit(100);
 *
 *  QueryResults results = query().fetch();
 *
 *  // Fetch the first page of 100 customers
 *  results.foreach(doc -&gt; {
 *      // Process results...
 *  });
 *
 *  // Fetch next page of 100 results
 *  Map pagingToken = results.getPagingToken()
 *
 *  results = query().pagingFrom(pagingToken).fetch();
 *
 *  results.foreach(doc -&gt; {
 *      // Process results...
 *  });
 * </code></pre>
 *
 * <p>
 *  The second option for processing large result sets is to use the <code>fetchAll()</code> method which will
 *  return a paging iterator which will internally perform queries to fetch the next set of results to process.
 *  A code example is provided below:
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.document.Documents;
 *  import io.nitric.api.document.QueryResults;
 *
 *  QueryResults results = Documents.collection("customers")
 *      .query()
 *      .fetchAll();
 *
 *  results.foreach(doc -&gt; {
 *      // Process results...
 *  });
 * </code></pre>
 */
public class Query<T> {

    final Collection collection;
    final List<Expression> expressions = new ArrayList<Expression>();
    int limit;
    Map<String, String> pagingToken;
    final Class<T> type;

    // Constructor ------------------------------------------------------------

    /*
     * Enforce package builder patterns.
     */
    Query(Collection collection, Class<T> type) {
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
    public QueryResults<T> fetch() {
        return new QueryResults<T>(this, false);
    }

    /**
     * Perform the Query operation and return all the fetched results. The QueryResults iterator will continue to
     * process all the pages of results until no more are available from the server. If no fetch limit is specified,
     * this method will set the query fetch limit to 1000.
     *
     * @return the Query operations fetched results.
     */
    public QueryResults<T> fetchAll() {
        if (this.limit <= 0) {
            this.limit = 1000;
        }

        return new QueryResults<T>(this, true);
    }

    /**
     * Return the string representation of this object.
     *
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
     * Provides a Query Result Document class.
     */
    public static class ResultDoc<T> {

        final Key key;
        final T content;

        /**
         * Enforce package builder patterns.
         */
        ResultDoc(Key key, T content) {
            this.key = key;
            this.content = content;
        }

        /**
         * @return the document key
         */
        public Key getKey() {
            return key;
        }

        /**
         * @return the document content
         */
        public T getContent() {
            return content;
        }

        /**
         * @return the string representation of this object
         */
        @Override
        public String toString() {
            return getClass().getSimpleName() + "[key=" + key + ", content=" + content + "]";
        }
    }

    /**
     * Provides a Query Result class.
     */
    public static class QueryResults<T> implements Iterable<ResultDoc<T>> {

        final Query<T> query;
        final boolean paginateAll;
        Map<String, String> pagingToken;
        List<ResultDoc<T>> queryData;

        /**
         * Create a QueryResults object.
         *
         * @parma query the query to continue
         * @param paginateAll specify whether the iterator paginate through all results
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
                    .setCollection(query.collection.toGrpcCollection());

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
            queryData = new ArrayList<ResultDoc<T>>(response.getDocumentsCount());

            var objectMapper = new ObjectMapper();

            response.getDocumentsList().forEach(doc -> {
                var key = Key.buildFromGrpcKey(doc.getKey());
                var map = ProtoUtils.toMap(doc.getContent());

                if (query.type.isAssignableFrom(map.getClass())) {
                    queryData.add(new ResultDoc(key, map));

                } else {
                    var value = (T) objectMapper.convertValue(map, query.type);
                    queryData.add(new ResultDoc(key, value));
                }
            });

            this.pagingToken = response.getPagingTokenMap();
        }
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

                    return queryResults.queryData.size() > 0;

                } else {
                    return false;
                }

            } else {
                return false;
            }
        }

        @Override
        public ResultDoc<T> next() {
            return queryResults.queryData.get(index++);
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
