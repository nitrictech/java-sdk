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

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.grpc.Status;
import io.nitric.api.NitricException;
import io.nitric.api.secret.Secrets;

public class MockSecretsTest {

    @Test
    public void test_init() {
        var ms = new MockSecrets();
        assertNotNull(ms);
        assertNotNull(ms.getMock());
    }

    @Test
    public void test_whenPut() {
        var ms = new MockSecrets().whenPut("name", "version");

        var value = "password".getBytes(StandardCharsets.UTF_8);
        var sv = Secrets.secret("name").put(value);
        assertNotNull(sv);
        assertEquals("name", sv.getSecret().getName());
        assertEquals("version", sv.getVersion());

        Mockito.verify(ms.getMock(), Mockito.times(1)).put(Mockito.any());
    }

    @Test
    public void test_whenPutError() {
        var ms = new MockSecrets().whenPutError(Status.UNAVAILABLE);

        var value = "password".getBytes(StandardCharsets.UTF_8);

        var secret = Secrets.secret("name");
        try {
            secret.put(value);
            fail();
        } catch (NitricException ne) {
            Mockito.verify(ms.getMock(), Mockito.times(1)).put(Mockito.any());
        }
    }

    @Test
    public void test_whenAccess() {
        var value = "password".getBytes(StandardCharsets.UTF_8);

        var ms = new MockSecrets().whenAccess("name", "version", value);

        var sv = Secrets.secret("name").latest().access();
        assertNotNull(sv);
        assertEquals("name", sv.getSecretVersion().getSecret().getName());
        assertEquals("version", sv.getSecretVersion().getVersion());
        assertEquals("password", sv.getAsText());

        Mockito.verify(ms.getMock(), Mockito.times(1)).access(Mockito.any());
    }

    @Test
    public void test_whenAccessError() {
        var ms = new MockSecrets().whenAccessError(Status.INTERNAL);

        var sv = Secrets.secret("name").latest();
        try {
            sv.access();
            fail();
        } catch (NitricException ne) {
            Mockito.verify(ms.getMock(), Mockito.times(1)).access(Mockito.any());
        }
    }

}
