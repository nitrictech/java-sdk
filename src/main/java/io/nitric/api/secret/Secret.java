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

import java.nio.charset.StandardCharsets;

import com.google.protobuf.ByteString;

import io.nitric.proto.secret.v1.SecretPutRequest;
import io.nitric.proto.secret.v1.SecretPutResponse;
import io.nitric.util.Contracts;
import io.nitric.util.ProtoUtils;

/**
 * Provides a named Secret class.
 *
 * @see Secrets
 * @see SecretVersion
 * @see SecretValue
 */
public class Secret {

    final String name;

    /*
     * Enforce package builder patterns.
     */
    Secret(String name) {
        Contracts.requireNonBlank(name, "name");
        this.name = name;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the secret name.
     *
     * @return the secret name
     */
    public String getName() {
        return name;
    }

    /**
     * Return a new SecretVersion with the given version number.
     *
     * @param version the secret version number
     * @return a new SecretVersion with the given version number
     */
    public SecretVersion version(String version) {
        Contracts.requireNonBlank(version, "version");
        return new SecretVersion(this, version);
    }

    /**
     * <p>
     * Return the latest SecretVersion alias. Note this latest SecretVersion alias is use to retrieve the latest
     * <code>SecretValue</code> instances. Repeated calls to with this latest alias may result in different
     * <code>SecretValue</code> objects being returned as the secret value may have been updated between calls and a
     * new secret version value is available.
     * </p>
     *
     * <p>
     * Use this method to access the latest secret value for this secret.
     * </p>
     *
     * <pre><code class="code">
     * var password = Secrets.secret("jdbc.password")
     *         .latest()
     *         .access()
     *         .getAsText();
     * </code></pre>
     *
     * @return a new <code>"latest"</code> SecretVersion alias
     */
    public SecretVersion latest() {
        return new SecretVersion(this, SecretVersion.LATEST);
    }

    /**
     * <p>
     * Store a new value for the Secret and return a new SecretVersion object.
     * </p>
     *
     * <pre><code class="code">
     * byte[] keyData = ...
     *
     * SecretVersion secretVersion = Secrets.secret("encryption.key").put(keyData);
     * </code></pre>
     *
     * @param value the secret value to store (required)
     */
    public SecretVersion put(byte[] value) {
        Contracts.requireNonNull(value, "value");

        var secret = io.nitric.proto.secret.v1.Secret.newBuilder()
            .setName(name)
            .build();

        var request = SecretPutRequest.newBuilder()
                .setSecret(secret)
                .setValue(ByteString.copyFrom(value))
                .build();

        SecretPutResponse response = null;
        try {
            response = Secrets.getServiceStub().put(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw ProtoUtils.mapGrpcError(sre);
        }

        return new SecretVersion(this, response.getSecretVersion().getVersion());
    }

    /**
     * <p>
     * Store a new text value for the Secret and return a new SecretVersion object.
     * The secret text value will be stored as UTF-8 encode data.
     * </p>
     *
     * <pre><code class="code">
     * String password = "AU8ezbHiV^NFHI98BqR6OeOf!8@%FKvP";
     *
     * SecretVersion secretVersion = Secrets.secret("jdbc.password").putAsText(password);
     * </code></pre>
     *
     * @param value the secret text value to store (required)
     */
    public SecretVersion putAsText(String value) {
        Contracts.requireNonBlank(value, "value");

        byte[] valueData = value.getBytes(StandardCharsets.UTF_8);
        return put(valueData);
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + "]";
    }

}
