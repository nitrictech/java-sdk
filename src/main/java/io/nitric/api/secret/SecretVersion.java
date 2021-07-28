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

import io.nitric.proto.secret.v1.SecretAccessRequest;
import io.nitric.proto.secret.v1.SecretAccessResponse;
import io.nitric.util.Contracts;
import io.nitric.util.ProtoUtils;

/**
 * <p>
 * Provides a Secret Version class. The Secret Version is used to specify a particular secret version stored
 * with the Secret API. To obtain the latest version of a secret use the <code>Secret.latest()</code> method.
 * </p>
 *
 * @see Secret
 * @see Secrets
 */
public class SecretVersion {

    /** The <code>"latest"</code> secret version number. */
    public static final String LATEST = "latest";

    final Secret secret;
    final String version;

    /*
     * Enforce package builder pattern.
     */
    SecretVersion(Secret secret, String version) {
        Contracts.requireNonNull(secret, "secret");
        Contracts.requireNonBlank(version, "version");

        this.secret = secret;
        this.version = version;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the version secret.
     *
     * @return the version secret
     */
    public Secret getSecret() {
        return secret;
    }

    /**
     * Return the secret version.
     *
     * @return the secret version
     */
    public String getVersion() {
        return version;
    }

    /**
     * <p>
     * Return the secret version's value data.
     * </p>
     *
     * <pre><code class="code">
     * String keyVersion = ...
     *
     * SecretVersion secretVersion = Secrets.secret("encryption.key").version(keyVersion);
     *
     * byte[] keyData = secretVersion.value();
     * </code></pre>
     *
     * @return the secret version's value data
     */
    public byte[] value() {
        var secret = io.nitric.proto.secret.v1.Secret.newBuilder()
            .setName(getSecret().getName())
            .build();

        var secretVersion = io.nitric.proto.secret.v1.SecretVersion.newBuilder()
            .setSecret(secret)
            .setVersion(getVersion())
            .build();

        var request = SecretAccessRequest.newBuilder()
                .setSecretVersion(secretVersion)
                .build();

        SecretAccessResponse response = null;
        try {
            response = Secrets.getServiceStub().access(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw ProtoUtils.mapGrpcError(sre);
        }

        var body = response.getValue();
        var value = (!body.isEmpty()) ? body.toByteArray() : new byte[0];

        return value;
    }

    /**
     * <p>
     * Return the secret version's value as a UTF-8 encoded string.
     * </p>
     *
     * <pre><code class="code">
     * SecretVersion secretVersion = Secrets.secret("jdbc.password").latest();
     *
     * String password = secretVersion.valueText();
     * </code></pre>
     *
     * @return the secret version's value as a UTF-8 encoded string
     */
    public String valueText() {
        return new String(value(), StandardCharsets.UTF_8);
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    public String toString() {
        return getClass().getSimpleName()
            + "[secret=" + secret
            + ", version=" + version
            + "]";
    }

}
