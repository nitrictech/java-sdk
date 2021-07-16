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

import io.nitric.proto.document.v1.DocumentServiceGrpc;
import io.nitric.proto.document.v1.DocumentServiceGrpc.DocumentServiceBlockingStub;
import io.nitric.util.Contracts;
import io.nitric.util.GrpcChannelProvider;

/**
 * <p>
 *  Provides a Document API client.
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
 * var customers = Documents.collection("customers");
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
    public static Collection collection(String name) {
        Contracts.requireNonBlank(name, "name");

        var collection = new DocColl(name, null);
        return new Collection(collection);
    }

    // Package Private Methods ------------------------------------------------

    /**
     * @return the Document GRPC service stub
     */
    static DocumentServiceBlockingStub getServiceStub() {
        if (serviceStub == null) {
            var channel = GrpcChannelProvider.getChannel();
            serviceStub = DocumentServiceGrpc.newBlockingStub(channel);
        }
        return serviceStub;
    }

    /**
     * Set the Document GRPC service stub.
     *
     * @param stub the Document GRPC service stub
     */
    static void setServiceStub(DocumentServiceBlockingStub stub) {
        serviceStub = stub;
    }
}
