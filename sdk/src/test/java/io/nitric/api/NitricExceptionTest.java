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

import static org.junit.jupiter.api.Assertions.*;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;

import static io.nitric.api.NitricException.Code;

public class NitricExceptionTest {

    @Test
    public void test_init() {
        var ne1 = new NitricException("message");
        assertEquals("message", ne1.getMessage());
        assertEquals(Code.UNKNOWN, ne1.getCode());
        assertNull(ne1.getCause());

        var npe = new NullPointerException();
        var ne2 = new NitricException("message", npe);
        assertEquals("message", ne2.getMessage());
        assertEquals(Code.UNKNOWN, ne2.getCode());
        assertEquals(npe, ne2.getCause());

        var sre = new StatusRuntimeException(Status.NOT_FOUND);
        var ne3 = new NitricException(Code.NOT_FOUND, "message", sre);
        assertEquals("message", ne3.getMessage());
        assertEquals(Code.NOT_FOUND, ne3.getCode());
        assertEquals(sre, ne3.getCause());

        assertEquals("io.nitric.api.NitricException: NOT_FOUND: message", ne3.toString());
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

        var iae = NitricException.build(Status.INVALID_ARGUMENT.asRuntimeException());
        assertTrue(iae instanceof InvalidArgumentException);

        var nfe = NitricException.build(Status.NOT_FOUND.asRuntimeException());
        assertTrue(nfe instanceof NotFoundException);

        var ue = NitricException.build(Status.UNAVAILABLE.asRuntimeException());
        assertTrue(ue instanceof UnavailableException);

        try {
            NitricException.build(Status.OK.asRuntimeException());
            fail();
        } catch (UnsupportedOperationException uoe) {
            // Ignore
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