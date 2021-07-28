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

package io.nitric.api.document.model;

/**
 * Provides an Order model class.
 */
public class Order {

    private String id;
    private String sku;
    private Integer number;
    private Double price;

    // Public Methods ---------------------------------------------------------

    public String getId() { return id; }
    public void setId(String id) {
        this.id = id;
    }
    public String getSku() {
        return sku;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id=" + id + ", sku=" + sku + "]";
    }

}
