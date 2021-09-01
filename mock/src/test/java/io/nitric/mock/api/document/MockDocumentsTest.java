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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.grpc.Status;
import io.nitric.api.NitricException;
import io.nitric.api.NotFoundException;
import io.nitric.api.document.Documents;

public class MockDocumentsTest {

    @Test
    public void test_init() {
        var md = new MockDocuments();
        assertNotNull(md);
        assertNotNull(md.getMock());
    }

    @Test
    public void test_whenGet() {
        var md = new MockDocuments();

        var customer1 = new Customer("jd@email.com", "John", "Doe");

        md.whenGet(customer1);

        var customer2 = Documents.collection("customers").doc("jd@email.com", Customer.class).get();

        assertNotNull(customer2);
        assertEquals(customer1.toString(), customer2.toString());

        Mockito.verify(md.getMock(), Mockito.times(1)).get(Mockito.any());
    }

    @Test
    public void test_whenGetError() {
        var md = new MockDocuments();

        md.whenGetError(Status.NOT_FOUND);

        try {
            Documents.collection("customers").doc("jd@email.com", Customer.class).get();
            fail();
        } catch (NotFoundException nfe) {
            Mockito.verify(md.getMock(), Mockito.times(1)).get(Mockito.any());
        }
    }

    @Test
    public void test_whenSet() {
        var md = new MockDocuments();
        md.whenSet();

        var customer1 = new Customer("jd@email.com", "John", "Doe");

        Documents.collection("customers").doc("jd@email.com", Customer.class).set(customer1);

        Mockito.verify(md.getMock(), Mockito.times(1)).set(Mockito.any());
    }

    @Test
    public void test_whenSetError() {
        var md = new MockDocuments();

        md.whenSetError(Status.INTERNAL);

        var customer1 = new Customer("jd@email.com", "John", "Doe");

        try {
            Documents.collection("customers").doc("jd@email.com", Customer.class).set(customer1);
            fail();
        } catch (NitricException ne) {
            Mockito.verify(md.getMock(), Mockito.times(1)).set(Mockito.any());
        }
    }

    @Test
    public void test_whenDelete() {
        var md = new MockDocuments();
        md.whenDelete();

        Documents.collection("customers").doc("jd@email.com").delete();

        Mockito.verify(md.getMock(), Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    public void test_whenDeleteError() {
        var md = new MockDocuments();

        md.whenDeleteError(Status.INTERNAL);

        try {
            Documents.collection("customers").doc("jd@email.com").delete();
            fail();
        } catch (NitricException ne) {
            Mockito.verify(md.getMock(), Mockito.times(1)).delete(Mockito.any());
        }
    }

    @Test
    public void test_whenQuery() {
        // TOOD...
    }

    @Test
    public void test_whenQueryError() {
        // TOOD...
    }

}
