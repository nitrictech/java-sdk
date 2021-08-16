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

package io.nitric.api.exception;

import io.grpc.Status;
import org.junit.Test;

import static junit.framework.TestCase.fail;

public class ApiExceptionTest {

    @Test
    public void test_cancelled() {
        var e = ApiException.fromGrpcServiceException(
                Status.CANCELLED.asRuntimeException()
        );

        if (!(e instanceof CancelledException)) {
            fail();
        }
    }

    @Test
    public void test_unknown() {
        var e = ApiException.fromGrpcServiceException(
                Status.UNKNOWN.asRuntimeException()
        );

        if (!(e instanceof UnknownException)) {
            fail();
        }
    }

    @Test
    public void test_invalid_argument() {
        var e = ApiException.fromGrpcServiceException(
                Status.INVALID_ARGUMENT.asRuntimeException()
        );

        if (!(e instanceof InvalidArgumentException)) {
            fail();
        }
    }

    @Test
    public void test_deadline_exceeded() {
        var e = ApiException.fromGrpcServiceException(
                Status.DEADLINE_EXCEEDED.asRuntimeException()
        );

        if (!(e instanceof DeadlineExceededException)) {
            fail();
        }
    }

    @Test
    public void test_not_found() {
        var e = ApiException.fromGrpcServiceException(
                Status.NOT_FOUND.asRuntimeException()
        );

        if (!(e instanceof NotFoundException)) {
            fail();
        }
    }

    @Test
    public void test_already_exists() {
        var e = ApiException.fromGrpcServiceException(
                Status.ALREADY_EXISTS.asRuntimeException()
        );

        if (!(e instanceof AlreadyExistsException)) {
            fail();
        }
    }

    @Test
    public void permission_denied() {
        var e = ApiException.fromGrpcServiceException(
                Status.PERMISSION_DENIED.asRuntimeException()
        );

        if (!(e instanceof PermissionDeniedException)) {
            fail();
        }
    }

    @Test
    public void test_resource_exhausted() {
        var e = ApiException.fromGrpcServiceException(
                Status.RESOURCE_EXHAUSTED.asRuntimeException()
        );

        if (!(e instanceof ResourceExhaustedException)) {
            fail();
        }
    }

    @Test
    public void test_failed_precondition() {
        var e = ApiException.fromGrpcServiceException(
                Status.FAILED_PRECONDITION.asRuntimeException()
        );

        if (!(e instanceof FailedPreconditionException)) {
            fail();
        }
    }

    @Test
    public void test_aborted() {
        var e = ApiException.fromGrpcServiceException(
                Status.ABORTED.asRuntimeException()
        );

        if (!(e instanceof AbortedException)) {
            fail();
        }
    }

    @Test
    public void test_out_of_range() {
        var e = ApiException.fromGrpcServiceException(
                Status.OUT_OF_RANGE.asRuntimeException()
        );

        if (!(e instanceof OutOfRangeException)) {
            fail();
        }
    }

    @Test
    public void test_unimplemented() {
        var e = ApiException.fromGrpcServiceException(
                Status.UNIMPLEMENTED.asRuntimeException()
        );

        if (!(e instanceof UnimplementedException)) {
            fail();
        }
    }

    @Test
    public void test_internal() {
        var e = ApiException.fromGrpcServiceException(
                Status.INTERNAL.asRuntimeException()
        );

        if (!(e instanceof InternalException)) {
            fail();
        }
    }

    @Test
    public void test_unavailable() {
        var e = ApiException.fromGrpcServiceException(
                Status.UNAVAILABLE.asRuntimeException()
        );

        if (!(e instanceof UnavailableException)) {
            fail();
        }
    }

    @Test
    public void test_data_loss() {
        var e = ApiException.fromGrpcServiceException(
                Status.DATA_LOSS.asRuntimeException()
        );

        if (!(e instanceof DataLossException)) {
            fail();
        }
    }

    @Test
    public void test_unauthenticated() {
        var e = ApiException.fromGrpcServiceException(
                Status.UNAUTHENTICATED.asRuntimeException()
        );

        if (!(e instanceof UnauthenticatedException)) {
            fail();
        }
    }
}
