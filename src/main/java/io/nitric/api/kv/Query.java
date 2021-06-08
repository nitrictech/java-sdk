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
 *   Support for query pagination is provided when using the <code>limit</code> and <code>pagingToken</code> attributes.
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
 * @see KeyValueClient
 */
public class Query<T> {

    final KeyValueClient.Builder builder;
    final List<Expression> expressions = new ArrayList<>();
    Map<String, Object> pagingToken;
    int limit;

    /*
     * Enforce builder pattern.
     */
    Query(KeyValueClient.Builder builder) {
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
     * Perform the Query operation and return the fetched results.
     *
     * @return the Query operations fetched results.
     */
    public QueryResult<T> fetch() {

        var requestBuilder = KeyValueQueryRequest.newBuilder()
                .setCollection(builder.collection)
                .setLimit(limit);

        expressions.forEach(e -> {
            var exp = KeyValueQueryRequest.Expression.newBuilder()
                    .setOperand(e.operand)
                    .setOperator(e.operator)
                    .setValue(e.value)
                    .build();
            requestBuilder.addExpressions(exp);
        });

        if (pagingToken != null) {
            var tokenStruct = ProtoUtils.toStruct(pagingToken);
            requestBuilder.setPagingToken(tokenStruct);
        }

        var request = requestBuilder.build();

        var response = builder.serviceStub.query(request);

        var resultList = new ArrayList<T>(response.getValuesCount());

        var objectMapper = new ObjectMapper();

        response.getValuesList().forEach(struct -> {
            Map map = ProtoUtils.toMap(struct);

            if (map.getClass().isAssignableFrom(builder.type)) {
                resultList.add((T) map);

            } else {
                var value = (T) objectMapper.convertValue(map, builder.type);
                resultList.add(value);
            }
        });

        Map<String, Object> resultPagingToken = (response.getPagingToken() != null)
                ? ProtoUtils.toMap(response.getPagingToken()) : null;

        return new QueryResult<T>(resultList, resultPagingToken);
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

        private final Map<String, Object> pagingToken;
        private final List<T> results;

        /**
         * Create a QueryResult object.
         *
         * @param results the query results
         * @param pagingToken the query paging continuation token
         */
        QueryResult(List<T> results, Map<String, Object> pagingToken) {
            this.results = results;
            this.pagingToken = (pagingToken != null && !pagingToken.isEmpty()) ? pagingToken : null;
        }

        /**
         * @return a typed query results iterator
         */
        @Override
        public Iterator<T> iterator() {
            return results.iterator();
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

        /**
         * @return the string representation of this object
         */
        @Override
        public String toString() {
            return getClass().getSimpleName()
                    + "[results.size=" + results.size()
                    + ", pagingToken=" + pagingToken
                    + "]";
        }
    }

    /**
     * <p>
     *  Provides a Query expression class.
     * </p>
     */
    static class Expression {
        final String operand;
        final String operator;
        final String value;

        Expression(String operand, String operator, String value) {
            this.operand = operand;
            this.operator = operator;
            this.value = value;
        }

        /**
         * @return the string representation of this object
         */
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