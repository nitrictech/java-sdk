package io.nitric.util;

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

import org.junit.Test;

import static org.junit.Assert.*;

public class ContractsTest {

    @Test
    public void test_requireNonNull() {
        Contracts.requireNonNull(new Object(), "name");

        try {
            Contracts.requireNonNull(null, "name");
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-null name", iae.getMessage());
        }
    }

    @Test
    public void test_requireNonBlank() {
        Contracts.requireNonBlank("value", "name");

        try {
            Contracts.requireNonBlank(null, "name");
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-null name", iae.getMessage());
        }

        try {
            Contracts.requireNonBlank("   ", "name");
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("provide non-blank name", iae.getMessage());
        }
    }
}
