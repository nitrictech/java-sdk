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

package io.nitric.mock.api.secret;

import com.google.protobuf.ByteString;

import org.mockito.Mockito;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.nitric.api.secret.Secrets;
import io.nitric.proto.secret.v1.Secret;
import io.nitric.proto.secret.v1.SecretAccessResponse;
import io.nitric.proto.secret.v1.SecretPutResponse;
import io.nitric.proto.secret.v1.SecretServiceGrpc;
import io.nitric.proto.secret.v1.SecretVersion;
import io.nitric.util.Contracts;

/**
 * <p>
 *  Provides a Nitric Queue Service API Mockito helper class.
 * </p>
 */
public class MockSecrets {

    SecretServiceGrpc.SecretServiceBlockingStub mock;

    /**
     * Create a new SecretsMock object.
     */
    public MockSecrets() {
        mock = Mockito.mock(SecretServiceGrpc.SecretServiceBlockingStub.class);
        Secrets.setServiceStub(mock);
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the Mockito QueueService stub.
     *
     * @return the Mockito QueueService stub
     */
    public SecretServiceGrpc.SecretServiceBlockingStub getMock() {
        return mock;
    }

    /**
     * Specify what to return when the SecretService Put method is invoked.
     *
     * @param name the secret name response (required)
     * @param version the secret version response (required)
     * @return the MockSecrets object
     */
    public MockSecrets whenPut(String name, String version) {
        Contracts.requireNonBlank(name, "name");
        Contracts.requireNonBlank(version, "version");

        var secret = Secret.newBuilder().setName(name).build();

        var secretVersion = SecretVersion.newBuilder()
            .setSecret(secret)
            .setVersion(version)
            .build();

        Mockito.when(mock.put(Mockito.any())).thenReturn(
            SecretPutResponse.newBuilder()
                .setSecretVersion(secretVersion)
                .build()
        );

        return this;
    }

    /**
     * Specify the error to throw when the SecretService Put method is invoked.
     *
     * @param status the GRPC error status (required)
     * @return the MockSecrets object
     */
    public MockSecrets whenPutError(Status status) {
        Contracts.requireNonNull(status, "status");

        Mockito.when(mock.put(Mockito.any())).thenThrow(
            new StatusRuntimeException(status)
        );

        return this;
    }

    /**
     * Specify what to return when the SecretService Access method is invoked.
     *
     * @param name the secret name response (required)
     * @param version the secret version response (required)
     * @param value the secret value response (required)
     * @return the MockSecrets object
     */
    public MockSecrets whenAccess(String name, String version, byte[] value) {
        Contracts.requireNonBlank(name, "name");
        Contracts.requireNonBlank(version, "version");

        var secret = Secret.newBuilder().setName(name).build();

        var secretVersion = SecretVersion.newBuilder()
            .setSecret(secret)
            .setVersion(version)
            .build();

        Mockito.when(mock.access(Mockito.any())).thenReturn(
            SecretAccessResponse.newBuilder()
                .setSecretVersion(secretVersion)
                .setValue(ByteString.copyFrom(value))
                .build()
        );

        return this;
    }

    /**
     * Specify the error to throw when the SecretService Access method is invoked.
     *
     * @param status the GRPC error status (required)
     * @return the MockSecrets object
     */
    public MockSecrets whenAccessError(Status status) {
        Contracts.requireNonNull(status, "status");

        Mockito.when(mock.access(Mockito.any())).thenThrow(
            new StatusRuntimeException(status)
        );

        return this;
    }

}
