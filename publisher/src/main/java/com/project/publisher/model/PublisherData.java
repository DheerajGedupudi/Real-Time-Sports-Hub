package com.project.publisher.model;

import java.util.HashSet;
import java.util.Set;

public class PublisherData {

    private String topic;
    private Set<String> publishedMessages = new HashSet<>();

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Set<String> getPublishedMessages() {
        return publishedMessages;
    }

    public void setPublishedMessages(Set<String> publishedMessages) {
        this.publishedMessages = publishedMessages;
    }
}
