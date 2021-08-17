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

import io.nitric.api.exception.ApiException;
import io.nitric.proto.secret.v1.SecretAccessRequest;
import io.nitric.proto.secret.v1.SecretAccessResponse;
import io.nitric.util.Contracts;
import io.nitric.util.ProtoUtils;

/**
 * <p>
 * Provides a Secret Version class. The Secret Version is used to specify a particular secret version stored
 * with the Secret API. To obtain the latest secret version value use the <code>Secret.latest().access()</code>
 * methods.
 * </p>
 *
 * @see Secrets
 * @see Secret
 * @see SecretValue
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
     * Return the version's secret value if found, or throws an exception otherwise.
     * </p>
     *
     * <pre><code class="code">
     * String keyVersion = ...
     *
     * SecretValue secretValue = Secrets.secret("encryption.key")
     *     .version(keyVersion)
     *     .access();
     *
     * byte[] keyData = secretValue.get();
     * </code></pre>
     *
     * @return the version's secret value if found, or throws an exception otherwise
     * @throws io.grpc.StatusRuntimeException if the secret value was not found
     */
    public SecretValue access() {
        var protoSecret = io.nitric.proto.secret.v1.Secret.newBuilder()
            .setName(getSecret().getName())
            .build();

        var protoVersion = io.nitric.proto.secret.v1.SecretVersion.newBuilder()
            .setSecret(protoSecret)
            .setVersion(getVersion())
            .build();

        var request = SecretAccessRequest.newBuilder()
                .setSecretVersion(protoVersion)
                .build();

        SecretAccessResponse response = null;
        try {
            response = Secrets.getServiceStub().access(request);
        } catch (io.grpc.StatusRuntimeException sre) {
            throw ApiException.fromGrpcServiceException(sre);
        }

        var secretVersion = new SecretVersion(
            secret,
            response.getSecretVersion().getVersion());

        var body = response.getValue();
        var value = (!body.isEmpty()) ? body.toByteArray() : new byte[0];

        return new SecretValue(secretVersion, value);
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