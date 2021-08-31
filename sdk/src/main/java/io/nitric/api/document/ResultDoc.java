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

package io.nitric.api.document;

/**
 * Provides a Result Document class for QueryResults and Streams. The ResultDoc class provides access to the
 * document's content and key.
 *
 * @see Query
 * @see QueryResults
 */
public class ResultDoc<T> {

    final Key key;
    final T content;

    /*
     * Enforce package builder patterns.
     */
    ResultDoc(Key key, T content) {
        this.key = key;
        this.content = content;
    }

    /**
     * @return the document key
     */
    public Key getKey() {
        return key;
    }

    /**
     * @return the document content
     */
    public T getContent() {
        return content;
    }

    /**
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[key=" + key + ", content=" + content + "]";
    }

}