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

package io.nitric.api;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.Test;

import static io.nitric.api.NitricException.Code;
import static junit.framework.TestCase.*;

public class NitricExceptionTest {

    @Test
    public void test_init() {
        var ne1 = new NitricException("message");
        assertEquals("message", ne1.getMessage());
        assertEquals(Code.UNKNOWN, ne1.getCode());
        assertNull(ne1.getCause());
        System.out.println(ne1.toString());
        assertEquals("io.nitric.api.NitricException: UNKNOWN: message", ne1.toString());

        var npe = new NullPointerException();
        var ne2 = new NitricException("message", npe);
        assertEquals("message", ne2.getMessage());
        assertEquals(Code.UNKNOWN, ne2.getCode());
        assertEquals(npe, ne2.getCause());
        System.out.println(ne2.toString());
        assertEquals("io.nitric.api.NitricException: UNKNOWN: message", ne2.toString());

        var sre = new StatusRuntimeException(Status.NOT_FOUND);
        var ne3 = new NitricException(Code.NOT_FOUND, "message", sre);
        assertEquals("message", ne3.getMessage());
        assertEquals(Code.NOT_FOUND, ne3.getCode());
        assertEquals(sre, ne3.getCause());
        assertEquals("io.nitric.api.NitricException: NOT_FOUND: message", ne3.toString());

        var ne4 = new NitricException(null, null, null);
        assertNull(ne4.getMessage());
        assertEquals(Code.UNKNOWN, ne4.getCode());
        assertNull(ne4.getCause());
        System.out.println(ne4.toString());
        assertEquals("io.nitric.api.NitricException: UNKNOWN", ne4.toString());
    }

    @Test
    public void test_build() {
        test_status(Status.ABORTED, Code.ABORTED);
        test_status(Status.ALREADY_EXISTS, Code.ALREADY_EXISTS);
        test_status(Status.CANCELLED, Code.CANCELLED);
        test_status(Status.DATA_LOSS, Code.DATA_LOSS);
        test_status(Status.DEADLINE_EXCEEDED, Code.DEADLINE_EXCEEDED);
        test_status(Status.FAILED_PRECONDITION, Code.FAILED_PRECONDITION);
        test_status(Status.INTERNAL, Code.INTERNAL);
        test_status(Status.INVALID_ARGUMENT, Code.INVALID_ARGUMENT);
        test_status(Status.NOT_FOUND, Code.NOT_FOUND);
        test_status(Status.OUT_OF_RANGE, Code.OUT_OF_RANGE);
        test_status(Status.PERMISSION_DENIED, Code.PERMISSION_DENIED);
        test_status(Status.RESOURCE_EXHAUSTED, Code.RESOURCE_EXHAUSTED);
        test_status(Status.UNAVAILABLE, Code.UNAVAILABLE);
        test_status(Status.UNAUTHENTICATED, Code.UNAUTHENTICATED);
        test_status(Status.UNIMPLEMENTED, Code.UNIMPLEMENTED);
        test_status(Status.UNKNOWN, Code.UNKNOWN);

        try {
            NitricException.build(Status.OK.asRuntimeException());
            fail();
        } catch (UnsupportedOperationException uoe) {
        }
    }

    private void test_status(Status status, Code code) {
        StatusRuntimeException sre = status.asRuntimeException();
        NitricException ne = NitricException.build(sre);

        assertNotNull(ne);
        assertEquals(code, ne.getCode());
        assertEquals(sre, ne.getCause());
    }
}
