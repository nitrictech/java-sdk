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

import io.nitric.proto.document.v1.DocumentServiceGrpc;
import io.nitric.proto.document.v1.DocumentServiceGrpc.DocumentServiceBlockingStub;
import io.nitric.util.GrpcChannelProvider;

/**
 * <p>
 *  Provides the Document API client.
 * </p>
 *
 * <p>
 *  The example below illustrates the Documents API.
 * </p>
 *
 * <pre><code class="code">
 * import io.nitric.api.document.Documents;
 * import java.util.Map;
 * ...
 *
 * // Create a customers collection
 * var customers = new Documents().collection("customers");
 *
 * // Store a new customer document
 * var customerMap = Map.of("email", "anne.smith@example.com", "status", "active");
 *
 * customers.doc("anne.smith@example.com").set(customerMap);
 *
 * // Get customer document ref and content
 * var customerRef = customers.doc("anne.smith@example.com");
 *
 * customerMap = customerRef.get();
 *
 * // Delete a customer document
 * customers.doc("anne.smith@example.com").delete();
 * </code></pre>
 *
 * <p>
 *  The example below illustrates type mapping with a custom POJO class.
 * </p>
 *
 * <pre><code class="code">
 * package com.example.entity;
 *
 * public class Customer {
 *
 *     private String mobile;
 *     private Boolean active;
 *
 *     public String getMobile() {
 *         return mobile;
 *     }
 *     public void setMobile(String mobile) {
 *         this.mobile = mobile;
 *     }
 *
 *     public Boolean getActive() {
 *         return active;
 *     }
 *     public void setActive(Boolean active) {
 *         this.active = active;
 *     }
 * }
 *
 * package com.example.function;
 *
 * import com.example.entity.Customer;
 *
 * import io.nitric.api.document.Documents;
 * import io.nitric.faas.Faas;
 * import io.nitric.faas.Trigger;
 * import io.nitric.faas.NitricFunction;
 * import io.nitric.faas.Response;
 *
 * public class CustomerFunction implements NitricFunction {
 *
 *     public Response handle(Trigger trigger) {
 *         // Get an customer document reference
 *         String id = request.getParameter("id");
 *         var custRef = new Documents().collection("customers")
 *             .doc(id, Customer.class);
 *
 *         // Update an customer document
 *         Customer customer = custRef.get();
 *         customer.setMobile("0432 321 543");
 *         customer.setActive(false);
 *         custRef.set(customer);
 *
 *        return trigger.buildResponse("OK");
 *     }
 *
 *     public static void main(String... args) {
 *         Faas.start(new CustomerFunction());
 *     }
 *  }
 * </code></pre>
 */
public class Documents {

    static DocumentServiceBlockingStub serviceStub;

    // Public Methods ---------------------------------------------------------

    /**
     * Create a new document collection with the given collection name.
     *
     * @param name the name of the collection (required)
     * @return a new document collection
     */
    public Collection collection(String name) {
        return new Collection(name, null);
    }

    /**
     * Return the Membrane GRPC Document Service Stub.
     *
     * @return the Membrane GRPC Document Service Stub
     */
    public static DocumentServiceBlockingStub getServiceStub() {
        if (serviceStub == null) {
            var channel = GrpcChannelProvider.getChannel();
            serviceStub = DocumentServiceGrpc.newBlockingStub(channel);
        }
        return serviceStub;
    }

    /**
     * Set the Membrane GRPC Document Service Stub.
     *
     * @param stub the Membrane GRPC Document Service Stub
     */
    public static void setServiceStub(DocumentServiceBlockingStub stub) {
        serviceStub = stub;
    }
}
