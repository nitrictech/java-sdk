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

package io.nitric.api.secret;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.nio.charset.StandardCharsets;

import com.google.protobuf.ByteString;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.nitric.proto.secret.v1.SecretAccessRequest;
import io.nitric.proto.secret.v1.SecretAccessResponse;
import io.nitric.proto.secret.v1.SecretPutRequest;
import io.nitric.proto.secret.v1.SecretPutResponse;
import io.nitric.proto.secret.v1.SecretServiceGrpc;

@RunWith(MockitoJUnitRunner.class)
public class SecretsTest {

    @Test
    public void test_serviceStub() {
        Secrets.setServiceStub(null);
        assertNotNull(Secrets.getServiceStub());

        var mock = Mockito.mock(SecretServiceGrpc.SecretServiceBlockingStub.class);

        Secrets.setServiceStub(mock);
        assertEquals(mock, Secrets.getServiceStub());
    }

    @Test
    public void test_secret() {

        var secret = Secrets.secret("name");
        assertNotNull(secret);
        assertEquals("name", secret.getName());
        assertEquals("Secret[name=name]", secret.toString());

        try {
            Secrets.secret(" ");
            fail();
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void test_secret_version() {
        var secretVersion = Secrets.secret("name").version("version");
        assertNotNull(secretVersion);
        assertEquals("name", secretVersion.getSecret().getName());
        assertEquals("version", secretVersion.getVersion());
        assertEquals("SecretVersion[secret=Secret[name=name], version=version]", secretVersion.toString());

        try {
            Secrets.secret("name").version(" ");
            fail();
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void test_secret_latest() {
        var secretVersion = Secrets.secret("name").latest();
        assertNotNull(secretVersion);
        assertEquals("name", secretVersion.getSecret().getName());
        assertEquals("latest", secretVersion.getVersion());
        assertEquals("SecretVersion[secret=Secret[name=name], version=latest]", secretVersion.toString());
    }

    @Test
    public void test_secret_put() {
        var protoSecret = io.nitric.proto.secret.v1.Secret.newBuilder().setName("name").build();
        var protoSecretVersion = io.nitric.proto.secret.v1.SecretVersion.newBuilder()
                .setSecret(protoSecret)
                .setVersion("version")
                .build();

        var mock = Mockito.mock(SecretServiceGrpc.SecretServiceBlockingStub.class);
        Mockito.when(mock.put(Mockito.any(SecretPutRequest.class))).thenReturn(
                SecretPutResponse.newBuilder().setSecretVersion(protoSecretVersion).build()
        );
        Secrets.setServiceStub(mock);

        var value = "password".getBytes(StandardCharsets.UTF_8);

        var secret = Secrets.secret("name").put(value);

        assertNotNull(secret);
        assertEquals("name", secret.getSecret().getName());
        assertEquals("version", secret.getVersion());

        // Verify we actually called the mock object
        var protoRequest = SecretPutRequest.newBuilder()
                .setSecret(protoSecret)
                .setValue(ByteString.copyFrom(value))
                .build();
        Mockito.verify(mock).put(protoRequest);

        var secret2 = Secrets.secret("name").putAsText("password");

        assertNotNull(secret2);
        assertEquals("name", secret2.getSecret().getName());
        assertEquals("version", secret2.getVersion());

        // Verify we actually called the mock object
        Mockito.verify(mock, Mockito.times(2)).put(protoRequest);

        try {
            Secrets.secret("name").put(null);
            fail();
        } catch (IllegalArgumentException iae) {
        }

        // Verify GRPC Failure Mode
        Mockito.when(mock.put(Mockito.any(SecretPutRequest.class))).thenThrow(
                new StatusRuntimeException(Status.INVALID_ARGUMENT)
        );

        try {
            Secrets.secret("name").putAsText("password");
            fail();
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void test_secret_version_access() {
        var protoSecret = io.nitric.proto.secret.v1.Secret.newBuilder().setName("name").build();
        var protoSecretVersion = io.nitric.proto.secret.v1.SecretVersion.newBuilder()
                .setSecret(protoSecret)
                .setVersion("version")
                .build();

        var value = "password".getBytes(StandardCharsets.UTF_8);
        var valueBS = ByteString.copyFrom(value);

        var mock = Mockito.mock(SecretServiceGrpc.SecretServiceBlockingStub.class);
        Mockito.when(mock.access(Mockito.any(SecretAccessRequest.class))).thenReturn(
                SecretAccessResponse.newBuilder().setSecretVersion(protoSecretVersion).setValue(valueBS).build()
        );
        Secrets.setServiceStub(mock);

        var secretValue = Secrets.secret("name").version("version").access();

        assertNotNull(secretValue);
        assertEquals("name", secretValue.getSecretVersion().getSecret().getName());
        assertEquals("version", secretValue.getSecretVersion().getVersion());
        assertEquals("password", secretValue.getAsText());
        assertEquals("password", new String(secretValue.get(), StandardCharsets.UTF_8));
        assertEquals("SecretValue[secretVersion=SecretVersion[secret=Secret[name=name], version=version], value.length=8]", secretValue.toString());

        // Verify we actually called the mock object
        var protoRequest = SecretAccessRequest.newBuilder()
                .setSecretVersion(protoSecretVersion)
                .build();
        Mockito.verify(mock).access(protoRequest);

        // Verify GRPC Failure Mode
        Mockito.when(mock.access(Mockito.any(SecretAccessRequest.class))).thenThrow(
                new StatusRuntimeException(Status.INVALID_ARGUMENT)
        );

        try {
            Secrets.secret("name").version("version").access();
            fail();
        } catch (IllegalArgumentException iae) {
        }
    }

    public class PiiRecord {

        private byte[] encryptedData;
        private String keyName;
        private String keyVersion;

        public byte[] getEncryptedData() { return encryptedData; }
        public void setEncryptedData(byte[] encryptedData) { this.encryptedData = encryptedData; }
        public String getKeyName() {
            return keyName;
        }
        public String getKeyVersion() {
            return keyVersion;
        }
        public void setKeyVersion(String keyVersion) {
            this.keyVersion = keyVersion;
        }
        public void setKeyName(String keyName) {
            this.keyName = keyName;
        }
    }

    public void javadoc_example() {
        // Text Password Example

        // Get the latest secret 'jdbc.password' value
        String password = Secrets.secret("jdbc.password")
                .latest()
                .access()
                .getAsText();

        // Store the new password value, making it the latest version
        String newPassword = "AU8ezbHiV^NFHI98BqR6OeOf!8@%FKvP";
        Secrets.secret("jdbc.password").putAsText(newPassword);

        // Encrypting PII (Personally Identifying Information) Record Example

        // Store a new secret 'encryption.key'
        byte[] keyData = new byte[32];
        Secrets.secret("encryption.key").put(keyData);

        // Sometime later lookup latest version of 'encryption.key' and use this to encrypt a private record.
        // Note we store the secret key name and version with the record so we can use this later to
        // decrypt the record.
        SecretValue value = Secrets.secret("encryption.key").latest().access();
        byte[] dataKey = value.get();

        // Here we encrypt the PII data with encryption data key
        byte[] encryptedData = null;

        PiiRecord record1 = new PiiRecord();
        record1.setEncryptedData(encryptedData);
        record1.setKeyName(value.getSecretVersion().getSecret().getName());
        record1.setKeyVersion(value.getSecretVersion().getVersion());

        // Retrieve a PII record
        PiiRecord record2 = null;

        byte[] dataKey2 = Secrets
                .secret(record2.getKeyName())
                .version(record2.getKeyVersion())
                .access()
                .get();

        // Use the encryption key to decrypt the records PII data
    }
}
