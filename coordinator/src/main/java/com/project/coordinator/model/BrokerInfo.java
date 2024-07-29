package com.project.coordinator.model;

public class BrokerInfo implements Comparable<BrokerInfo> {
    private String id;
    private String address;

    // Getters and setters

    @Override
    public int compareTo(BrokerInfo other) {
        return this.id.compareTo(other.id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
