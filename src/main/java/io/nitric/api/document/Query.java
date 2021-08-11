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

import io.nitric.proto.document.v1.Collection;
import io.nitric.proto.document.v1.ExpressionValue;
import io.nitric.util.Contracts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
 *  QueryResults&lt;Customer&gt; results = Documents.collection("customers")
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
 *  QueryResults&lt;Order&gt; results = Documents.collection("customers").doc("jane-smith@server.com")
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
 *  When processing large result sets the Query API provides two options. The first option is to use the
 *  <code>stream()</code> method which will return ResultDoc Stream object. This stream will internally make further
 *  Document Service query calls until there are no more results available from the server.
 * </p>
 *
 * <pre><code class="code">
 *  import java.util.stream.Stream;
 *  import io.nitric.api.document.Documents;
 *  import io.nitric.api.document.ResultDoc;
 *
 *  Stream&lt;ResultDoc&lt;Map&gt;&gt; stream = Documents.collection("customers")
 *      .query()
 *      .stream();
 *
 *  stream.foreach(doc -&gt; {
 *      // Process results...
 *  });
 * </code></pre>
 *
 * <p>
 *   The second option form processing large result sets is to use a paginated query where you pass pagingToken from
 *   the previous query results to the next query to continue. This technique is useful for paged user experiences.
 *   An example is provided below:
 * </p>
 *
 * <pre><code class="code">
 *  import io.nitric.api.document.Documents;
 *  import io.nitric.api.document.Query;
 *  import io.nitric.api.document.QueryResults;
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
 */
public class Query<T> {

    final io.nitric.proto.document.v1.Collection collection;
    final List<Expression> expressions = new ArrayList<>();
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
     * @param operand the left-hand side expression operand (required)
     * @param operator the query expression operator (required)
     * @param value the right-hand side operand (required)
     * @return the Query operation
     */
    public Query<T> where(String operand, String operator, String value) {
        Contracts.requireNonBlank(value, "value");
        return whereObj(operand, operator, value);
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
        return whereObj(operand, operator, value);
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
        return whereObj(operand, operator, value);
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
        return whereObj(operand, operator, value);
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
        return new QueryResults<>(this, false);
    }

    /**
     * Perform the Query operation and return a Stream object. If no fetch limit is specified this stream()
     * object will continue to perform Document Service queries until there are not more results available from the
     * server.
     *
     * @return the Query operations fetched results
     */
    public Stream<ResultDoc<T>> stream() {
        // If no fetch limit specified then paginate all
        boolean paginateAll = (this.limit == 0);

        // TODO: replace with QueryStream service call when implemented
        var iterator = new QueryResults<>(this, paginateAll).iterator();
        var spliterators = Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED);

        return StreamSupport.stream(spliterators, false);
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

    // Package Private Methods ------------------------------------------------

    Query<T> whereObj(String operand, String operator, Object value) {
        Contracts.requireNonBlank(operand, "operand");
        Contracts.requireNonBlank(operator, "operator");
        Contracts.requireNonNull(value, "value");

        expressions.add(new Expression(operand, operator, value));

        return this;
    }

    // Inner Classes ----------------------------------------------------------

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
