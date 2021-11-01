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

import io.nitric.util.Contracts;

/**
 * Provides a Secret Value class.
 *
 * @see Secrets
 * @see Secret
 * @see SecretVersion
 */
public class SecretValue {

    final SecretVersion secretVersion;
    final byte[] value;

    /*
     * Enforce package builder patterns.
     */
    SecretValue(SecretVersion version, byte[] value) {
        Contracts.requireNonNull(version, "version");
        Contracts.requireNonNull(value, "value");

        this.secretVersion = version;
        this.value = value;
    }

    // Public Methods ---------------------------------------------------------

    /**
     * Return the secret version.
     *
     * @return the secret version
     */
    public SecretVersion getSecretVersion() {
        return secretVersion;
    }

    /**
     * Return the secret value data.
     *
     * @return the secret value data
     */
    public byte[] get() {
        return value;
    }

    /**
     * Return the secret value as UTF-8 encoded text.
     *
     * @return the secret value text as UTF-8 encoded text
     */
    public String getAsText() {
        return new String(value, StandardCharsets.UTF_8);
    }

    /**
     * Return the string representation of this object.
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()
            + "[secretVersion=" + secretVersion
            + ", value.length=" + value.length
            + "]";
    }

}
