package com.project.subscriber.model;

import java.util.List;

public class Subscriber {
    private int id;
    private String name;
    private List<String> subscribedTopics;

    public Subscriber() {
    }

    public Subscriber(int id, String name, List<String> subscribedTopics) {
        this.id = id;
        this.name = name;
        this.subscribedTopics = subscribedTopics;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSubscribedTopics() {
        return subscribedTopics;
    }

    public void setSubscribedTopics(List<String> subscribedTopics) {
        this.subscribedTopics = subscribedTopics;
    }
}
