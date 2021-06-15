package io.nitric.api.kv;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nitric.proto.kv.v1.KeyValueQueryRequest;
import io.nitric.proto.kv.v1.KeyValueQueryResponse;
import io.nitric.util.ProtoUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * Provides a Query operation class. The Query operation provides a fluent API creating and executing Key Value database
 * query operations.
 * </p>
 *
 * <h3>Query Examples</h3>
 *
 * <p>
 * The example below illustrates a Query operation with a <code>"customers"</code> collection and an object
 * mapping to the Java <code>Map</code> class.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.kv.KeyValueClient;
 *  import java.util.Map;
 *  ...
 *
 *  KeyValueClient client = KeyValueClient.build(Map.class, "customers");
 *
 *  client.newQuery().fetch().foreach(customer -&gt; {
 *      // Process customer...
 *  });
 * </code></pre>
 *
 * <p>
 *  Extending our example we are now filtering our results to only include active customers and we also set
 *  a fetch limit to return a maximum of 100 items.
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.kv.KeyValueClient;
 *  import io.nitric.api.kv.Query.QueryResult;
 *  import java.util.Map;
 *  ...
 *
 *  KeyValueClient client = KeyValueClient.build(Map.class, "customers");
 *
 *  QueryResult results = client.newQuery()
 *      .where("status", "==", "active")
 *      .limit(100)
 *      .fetch();
 *
 *  results.foreach(customer -&gt; {
 *      // Process customer...
 *  });
 * </code></pre>
 *
 * <p>
 * The example below illustrates a Query operation with a single table design. In this example we have a single
 * <code>"application"</code> collection which can support multiple data types. This example maps the collection
 * items to a custom <code>Order</code> POJO class.
 * </p>
 *
 * <pre><code class="code">
 *  import com.example.model.Order;
 *  import io.nitric.api.kv.KeyValueClient;
 *  ...
 *
 *  var client = KeyValueClient.build(Order.class, "application");
 *
 *  // Fetch the first ten orders of the specified customer
 *  var results = client.newQuery()
 *      .where("pk", "==", "Customer#" + customerId)
 *      .where("sk", "startsWith", "Order#")
 *      .limit(100)
 *      .fetch();
 *
 *  results.foreach(order -&gt; {
 *     // Process order...
 *  });
 *  </code></pre>
 *
 * <h3>Where Expression Operators</h3>
 *
 * <p>
 * The Query class supports a restricted set of where expression operators to ensure code portability across the
 * leading Cloud vendor Document and Key Value databases. Supported operators are listed below:
 * </p>
 *
 * <table class="simple">
 *  <thead>
 *   <tr>
 *    <th>Operator</th>
 *    <th>Description</th>
 *    <th>Example</th>
 *   </tr>
 *  </thead>
 *  <tbody>
 *   <tr>
 *    <td class="codeCenter">==</td>
 *    <td>equality</td>
 *    <td><code>.where("id", "==", customer.getId())</code></td>
 *   </tr>
 *   <tr>
 *    <td class="codeCenter">&lt;</td>
 *    <td>less than</td>
 *    <td><code>.where("created", "&lt;", customer.getDate())</code></td>
 *   </tr>
 *   <tr>
 *    <td class="codeCenter">&gt;</td>
 *    <td>greater than</td>
 *    <td><code>.where("amount", "&gt;", product.getRetail())</code></td>
 *   </tr>
 *   <tr>
 *    <td class="codeCenter">&lt;=</td>
 *    <td>less than or equal to</td>
 *    <td><code>.where("created", "&lt;=", customer.getDate())</code></td>
 *   </tr>
 *   <tr>
 *    <td class="codeCenter">&gt;=</td>
 *    <td>greater than or equal to</td>
 *    <td><code>.where("amount", "&gt;=", product.getRetail())</code></td>
 *   </tr>
 *   <tr>
 *    <td class="codeCenter">startsWith</td>
 *    <td>value starts with</td>
 *    <td><code>.where("pk", "startsWith", "Order#")</code></td>
 *   </tr>
 *  </tbody>
 * </table>
 *
 * <p>
 * Queries with multiple <code>where()</code> expressions are implicitly AND together when executed.
 * </p>
 *
 * <h3>Query Restrictions</h3>
 *
 * <p>
 * To ensure multi-cloud portability we have the following additional query restrictions. Please note these may be
 * removed in future releases.
 * </p>
 *
 * <ol>
 *  <li style="padding-bottom:0.5em;">
 *   Only one property can have an inequality expression ( <code>&lt;, &gt;, &lt;=, &gt;=, startsWith</code> ). <br/>
 *   For example: <code>where("id", "==", id).where("number", "&gt;", "10)</code> <br/>
 *   This is a Firestore composite indexes restriction.
 *  </li>
 *  <li>
 *   Single property range expressions are limited to the operators <code>&gt;=</code> and <code>&lt;=</code>. <br/>
 *   For example: <code>where("id", "==", id).where("number", "&gt;=", "10).where("number", "&lt;=", "20)</code> <br/>
 *   This is a DynamoDB <code>BETWEEN</code> function mapping restriction.
 *  </li>
 * </ol>
 *
 * <p>
 * Please note these restrictions may be removed in future releases.
 * </p>
 *
 * <h3>Query Pagination</h3>
 *
 * <p>
 *   Query pagination support is provided with the <code>limit</code> and <code>pagingToken</code> attributes.
 *   To perform query paging first set a maximum query page size with the <code>limit</code> attribute.
 *   Once the first query has executed and processed, if there are more results available in the KV database the
 *   <code>QueryResult.getPagingToken()</code> will be defined. This value should then be used to set the
 *   <code>Query.pagingFrom()</code> in the next <code>Query.fetch()</code> call.
 * </p>
 *
 * <pre><code class="code">
 *  import com.example.model.Customer;
 *  import io.nitric.api.kv.KeyValueClient;
 *  ...
 *
 *  var client = KeyValueClient.build(Customer.class, "customer");
 *
 *  var query = client.newQuery()
 *          .where("active", "==", "true")
 *          .limit(100);
 *
 *  var results = query.fetch();
 *
 *  // Process the first page of results
 *  results.forEach(customer -&gt; {
 *      // Process customer...
 *  });
 *
 *  // Continue querying from the last pagingToken until none returned
 *  while (results.getPagingToken() != null) {
 *
 *      results = query
 *          .pagingFrom(result.getPagingToken())
 *          .fetch();
 *
 *      results.forEach(customer -&gt; {
 *          // Process customer...
 *      });
 *  }
 * </code></pre>
 *
 * <h3>Query Fetch All</h3>
 *
 * <p>
 * For processing large paginated result use the <code>fetchAll()</code> method
 * which provides an QueryResult iterator which automatically performs query
 * pagination for you. The equivalent code using the <code>fetchAll()</code> is
 * provided below.
 * </p>
 *
 * <pre><code class="code">
 *  import com.example.model.Customer;
 *  import io.nitric.api.kv.KeyValueClient;
 *  ...
 *
 *  var client = KeyValueClient.build(Customer.class, "customer");
 *
 *  var query = client.newQuery()
 *          .where("active", "==", "true")
 *          .limit(100);
 *
 *  // Process all the customer records
 *  query.fetchAll().forEach(customer -&gt; {
 *      // Process customer...
 *  });
 * </code></pre>
 *
 * <p>
 * Note processing upbounded results sets may lead to out of memory errors, so
 * please use stream procesing style design patterns and do not accumulate large
 * amounts of objects.
 * </p>
 *
 * @see KeyValueClient
 */
public class Query<T> {

    final KeyValueClient.Builder<T> builder;
    final List<Expression> expressions = new ArrayList<Expression>();
    Map<String, Object> pagingToken;
    int limit;

    /*
     * Enforce builder pattern.
     */
    Query(KeyValueClient.Builder<T> builder) {
        this.builder = builder;
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
        Objects.requireNonNull(operand, "operand parameter is required");
        Objects.requireNonNull(operator, "operator parameter is required");
        Objects.requireNonNull(operator, "operator parameter is required");

        if (operand.isBlank()) {
            throw new IllegalArgumentException("non blank operand parameter is required");
        }
        if (operator.isBlank()) {
            throw new IllegalArgumentException("non blank operator parameter is required");
        }
        if (value.isBlank()) {
            throw new IllegalArgumentException("non blank value parameter is required");
        }

        expressions.add(new Expression(operand, operator, value));

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
     * Set the query paging continuation token.
     *
     * @param pagingToken the query paging continuation token
     * @return the Query operation
     */
    public Query<T> pagingFrom(Map<String, Object> pagingToken) {
        this.pagingToken = pagingToken;
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
                + "[builder=" + builder
                + ", expressions=" + expressions
                + ", limit=" + limit
                + ", pagingToken=" + pagingToken
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
        Map<String, Object> pagingToken;
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
            var request = buildKeyValueRequest(this.query.expressions);
            var response = this.query.builder.serviceStub.query(request);

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
        public Map<String, Object> getPagingToken() {
            return pagingToken;
        }

        // Protected Methods --------------------------------------------------

        protected KeyValueQueryRequest buildKeyValueRequest(List<Expression> expressions) {
            var requestBuilder = KeyValueQueryRequest.newBuilder()
                    .setCollection(this.query.builder.collection)
                    .setLimit(this.query.limit);

            expressions.forEach(e -> {
                var exp = KeyValueQueryRequest.Expression.newBuilder()
                        .setOperand(e.operand)
                        .setOperator(e.operator)
                        .setValue(e.value)
                        .build();
                requestBuilder.addExpressions(exp);
            });

            if (this.pagingToken != null) {
                var pagingMap = ProtoUtils.toKeyMap(this.pagingToken);
                requestBuilder.putAllPagingToken(pagingMap);
            }

            return requestBuilder.build();
        }

        @SuppressWarnings({"unchecked"})
        protected void loadPageData(KeyValueQueryResponse response) {

            // Marshall response data
            queryData = new ArrayList<T>(response.getValuesCount());

            var objectMapper = new ObjectMapper();

            response.getValuesList().forEach(struct -> {
                var map = ProtoUtils.toMap(struct);

                if (map.getClass().isAssignableFrom(query.builder.type)) {
                    queryData.add((T) map);

                } else {
                    var value = (T) objectMapper.convertValue(map, query.builder.type);
                    queryData.add(value);
                }
            });

            // Marshal the response paging token
            this.pagingToken = ProtoUtils.fromKeyMap(response.getPagingTokenMap());
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

                if (queryResult.pagingToken != null) {
                    index = 0;

                    // Load next page of data
                    var request = queryResult.buildKeyValueRequest(queryResult.query.expressions);
                    var response = queryResult.query.builder.serviceStub.query(request);

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
        final String value;

        Expression(String operand, String operator, String value) {
            this.operand = operand;
            this.operator = operator;
            this.value = value;
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
