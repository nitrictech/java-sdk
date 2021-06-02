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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
 *  import java.util.List;
 *  import java.util.Map;
 *  ...
 *
 *  KeyValueClient client = KeyValueClient.build(Map.class, "customers");
 *
 *  List&lt;Map&gt; customers = client.newQuery()
 *      .where("status", "=", "active")
 *      .fetch();
 *  </code></pre>
 *
 * <p>
 * The example below illustrates a Query operation with a single table design. In this example we have a single
 * <code>"application"</code> collection which can support multiple data types. The example here maps the collection
 * items to a custom <code>Order</code> class.
 * </p>
 *
 * <pre><code class="code">
 *  import com.example.model.Order;
 *  import io.nitric.api.kv.KeyValueClient;
 *  import java.util.List;
 *  ...
 *
 *  KeyValueClient client = KeyValueClient.build(Order.class, "application");
 *
 *  // Fetch the first 500 Orders this month
 *  List&lt;Order&gt; results = client.newQuery()
 *      .where("sk", "startsWith", "Order#")
 *      .where("created", "&gt;=", startDate)
 *      .limit(500)
 *      .fetch();
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
 *    <td class="codeCenter">=</td>
 *    <td>equality</td>
 *    <td><code>.where("id", "=", customer.getId())</code></td>
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
 *    <td>less than or equal</td>
 *    <td><code>.where("created", "&lt;=", customer.getDate())</code></td>
 *   </tr>
 *   <tr>
 *    <td class="codeCenter">&gt;=</td>
 *    <td>greater than or equal</td>
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
 * @see KeyValueClient
 */
public class Query<T> {

    final KeyValueClient.Builder builder;
    final List<Expression> expressions = new ArrayList<>();
    int limit;

    static final Set VALID_OPERATORS = Set.of("=", "<", ">", "<=", ">=", "startsWith");

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
     * <code> = , &lt; , &gt; , &lt;= , &gt;= , startsWith</code>
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
        if (!VALID_OPERATORS.contains(operator)) {
            throw new IllegalArgumentException("operator '" + operator + "' is not supported");
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
     * Perform the Query operation and return the fetched results.
     *
     * @return the Query operations fetched results.
     */
    public List<T> fetch() {

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

        var request = requestBuilder.build();

        var response = builder.serviceStub.query(request);

        var results = new ArrayList<T>(response.getValuesCount());

        var objectMapper = new ObjectMapper();

        response.getValuesList().forEach(struct -> {
            Map map = ProtoUtils.toMap(struct);

            if (map.getClass().isAssignableFrom(builder.type)) {
                results.add((T) map);

            } else {
                var value = (T) objectMapper.convertValue(map, builder.type);
                results.add(value);
            }
        });

        return results;
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
                + "]";
    }

    // Inner Classes ----------------------------------------------------------

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