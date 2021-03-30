package io.nitric.api.kv;

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