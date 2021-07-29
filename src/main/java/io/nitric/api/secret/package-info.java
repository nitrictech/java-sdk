/**
 * <p>
 *  Provides the Secret Service API.
 * </p>
 *
 * <p>
 *  The examples below illustrates the Secret API.
 * </p>
 *
 * <h3>Text Password Example</h3>
 *
 * <p>
 *  This example uses the Secret API to store a database <code>'jdbc.password'</code> value.
 * </p>
 *
 * <pre><code class="code">
 * import io.nitric.api.secret.Secrets;
 * ...
 *
 * // Get the latest secret 'jdbc.password' value
 * String password = Secrets.secret("jdbc.password")
 *         .latest()
 *         .access()
 *         .getAsText();
 *
 * // Store the new password value, making it the latest version
 * String newPassword = "AU8ezbHiV^NFHI98BqR6OeOf!8@%FKvP";
 * Secrets.secret("jdbc.password").putAsText(newPassword);
 * </code></pre>
 *
 * <h3>Encryption Key Example</h3>
 *
 * <p>
 * This example uses the Secret API to store a data encryption key. This key is used to encrypt and
 * decrypt PII data records (Personally Identifying Information). The encryption key's secret name and
 * version is stored with the PII record so that the key can be retrieved from the Secret API at some later point
 * to decrypt the PII record.
 * </p>
 *
 * <p>
 * Using this strategy individual records can have their own data encryption key which limits data exposure if an
 * individual key is compromised.
 * </p>
 *
 * <pre><code class="code">
 * import io.nitric.api.secret.Secrets;
 * import io.nitric.api.secret.SecretValue;
 * import com.example.entity.PiiRecord;
 * ...
 *
 * // Store a new AES-256 secret data 'encryption.key'
 * byte[] dataKey = ...
 * Secrets.secret("encryption.key").put(dataKey);
 *
 * // Sometime later lookup latest version of 'encryption.key' and use this to encrypt a private record.
 * // Note we store the secret key name and version with the record so we can use this later to
 * // decrypt the record.
 * SecretValue value = Secrets.secret("encryption.key")
 *         .latest()
 *         .access();
 *
 * byte[] dataKey = value.get();
 *
 * // Here we encrypt the PII data with encryption data key
 * byte[] encryptedData = ...
 *
 * PiiRecord record = new PiiRecord();
 * record.setEncryptedData(encryptedData);
 * record.setKeyName(value.getSecretVersion().getSecret().getName());
 * record.setKeyVersion(value.getSecretVersion().getVersion());
 *
 * // Retrieve a customer PII record
 * PiiRecord custRecord = ...
 *
 * byte[] custKey = Secrets
 *         .secret(custRecord.getKeyName())
 *         .version(custRecord.getKeyVersion())
 *         .access()
 *         .get();
 *
 * // Use the encryption key to decrypt the records PII data
 * </code></pre>
 */
package io.nitric.api.secret;

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
