package io.nitric.api.kv;

/*-
 * #%L
 * Nitric Java SDK
 * %%
 * Copyright (C) 2021 Nitric Pty Ltd
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

import java.util.*;

/**
 * Provides test account class.
 */
public class Account {

    private Integer id;
    private String type;
    private Boolean active;
    private Double assetsValue;
    private Address currentAddress;
    private List<Address> previousAddresses = new ArrayList<>();
    private Map<String, String> properties = new HashMap<>();

    // Public Methods ---------------------------------------------------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Double getAssetsValue() {
        return assetsValue;
    }

    public void setAssetsValue(Double assetsValue) {
        this.assetsValue = assetsValue;
    }

    public Address getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(Address currentAddress) {
        this.currentAddress = currentAddress;
    }

    public List<Address> getPreviousAddresses() {
        return previousAddresses;
    }

    public void setPreviousAddresses(List<Address> previousAddresses) {
        this.previousAddresses = previousAddresses;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(type, account.type) && Objects.equals(active, account.active) && Objects.equals(assetsValue, account.assetsValue) && Objects.equals(currentAddress, account.currentAddress) && Objects.equals(previousAddresses, account.previousAddresses) && Objects.equals(properties, account.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, active, assetsValue, currentAddress, previousAddresses, properties);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", active=" + active +
                ", assetsValue=" + assetsValue +
                ", currentAddress=" + currentAddress +
                ", previousAddresses=" + previousAddresses +
                ", properties=" + properties +
                '}';
    }

}
