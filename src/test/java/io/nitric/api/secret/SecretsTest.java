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
        var secret = io.nitric.proto.secret.v1.Secret.newBuilder().setName("name").build();
        var secretVersion = io.nitric.proto.secret.v1.SecretVersion.newBuilder()
                .setSecret(secret)
                .setVersion("version")
                .build();

        var mock = Mockito.mock(SecretServiceGrpc.SecretServiceBlockingStub.class);
        Mockito.when(mock.put(Mockito.any(SecretPutRequest.class))).thenReturn(
                SecretPutResponse.newBuilder().setSecretVersion(secretVersion).build()
        );
        Secrets.setServiceStub(mock);

        var value = "password".getBytes(StandardCharsets.UTF_8);

        var sv = Secrets.secret("name").put(value);

        assertNotNull(sv);
        assertEquals("name", sv.getSecret().getName());
        assertEquals("version", sv.getVersion());

        // Verify we actually called the mock object
        Mockito.verify(mock).put(Mockito.any());

        try {
            Secrets.secret("name").put(null);
            fail();
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void test_secret_putText() {
        var secret = io.nitric.proto.secret.v1.Secret.newBuilder().setName("name").build();
        var secretVersion = io.nitric.proto.secret.v1.SecretVersion.newBuilder()
                .setSecret(secret)
                .setVersion("version")
                .build();

        var mock = Mockito.mock(SecretServiceGrpc.SecretServiceBlockingStub.class);
        Mockito.when(mock.put(Mockito.any(SecretPutRequest.class))).thenReturn(
                SecretPutResponse.newBuilder().setSecretVersion(secretVersion).build()
        );
        Secrets.setServiceStub(mock);

        var sv = Secrets.secret("name").putText("password");

        assertNotNull(sv);
        assertEquals("name", sv.getSecret().getName());
        assertEquals("version", sv.getVersion());

        // Verify we actually called the mock object
        Mockito.verify(mock).put(Mockito.any());

        try {
            Secrets.secret("name").putText(" ");
            fail();
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void test_secret_version_value() {
        var secret = io.nitric.proto.secret.v1.Secret.newBuilder().setName("name").build();
        var secretVersion = io.nitric.proto.secret.v1.SecretVersion.newBuilder()
                .setSecret(secret)
                .setVersion("version")
                .build();

        var value = "password".getBytes(StandardCharsets.UTF_8);
        var valueBS = ByteString.copyFrom(value);

        var mock = Mockito.mock(SecretServiceGrpc.SecretServiceBlockingStub.class);
        Mockito.when(mock.access(Mockito.any(SecretAccessRequest.class))).thenReturn(
                SecretAccessResponse.newBuilder().setSecretVersion(secretVersion).setValue(valueBS).build()
        );
        Secrets.setServiceStub(mock);

        var responseValue = Secrets.secret("name").version("version").value();

        assertNotNull(responseValue);
        assertEquals("password", new String(responseValue));

        // Verify we actually called the mock object
        Mockito.verify(mock).access(Mockito.any());
    }

    @Test
    public void test_secret_version_valueText() {
        var secret = io.nitric.proto.secret.v1.Secret.newBuilder().setName("name").build();
        var secretVersion = io.nitric.proto.secret.v1.SecretVersion.newBuilder()
                .setSecret(secret)
                .setVersion("version")
                .build();

        var value = "password".getBytes(StandardCharsets.UTF_8);
        var valueBS = ByteString.copyFrom(value);

        var mock = Mockito.mock(SecretServiceGrpc.SecretServiceBlockingStub.class);
        Mockito.when(mock.access(Mockito.any(SecretAccessRequest.class))).thenReturn(
                SecretAccessResponse.newBuilder().setSecretVersion(secretVersion).setValue(valueBS).build()
        );
        Secrets.setServiceStub(mock);

        var responseValue = Secrets.secret("name").version("version").valueText();

        assertEquals("password", responseValue);

        // Verify we actually called the mock object
        Mockito.verify(mock).access(Mockito.any());
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
            .valueText();

        // Store the new password value, making it the latest version
        String newPassword = "AU8ezbHiV^NFHI98BqR6OeOf!8@%FKvP";
        Secrets.secret("jdbc.password").putText(newPassword);

        // Encrypting PII (Personally Identifying Information) Record Example

        // Store a new secret 'encryption.key'
        byte[] keyData = new byte[32];
        Secrets.secret("encryption.key").put(keyData);

        // Sometime later lookup latest version of 'encryption.key' and use this to encrypt a private record.
        // Note we store the secret key name and version with the record so we can use this later to
        // decrypt the record.
        SecretVersion version = Secrets.secret("encryption.key").latest();
        byte[] latestKey = version.value();

        // Here we encrypt the PII data with encryption key
        byte[] encryptedData = null;

        PiiRecord record1 = new PiiRecord();
        record1.setEncryptedData(encryptedData);
        record1.setKeyName(version.getSecret().getName());
        record1.setKeyVersion(version.getVersion());

        // Retrieve a PII record
        PiiRecord record2 = null;

        byte[] dataKey = Secrets
            .secret(record2.getKeyName())
            .version(record2.getKeyVersion())
            .value();

        // Use the encryption key to decrypt the records PII data
    }

}
