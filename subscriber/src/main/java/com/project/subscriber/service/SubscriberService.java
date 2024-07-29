package com.project.subscriber.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.util.*;

@Service
public class SubscriberService {

    private final RestTemplate restTemplate;

    @Value("${coordinator.url}")
    private String coordinatorUrl;

    private String port;
    private String leader;
    private Set<String> topics = new HashSet<>();
    private Set<String> subscribedTopics = new HashSet<>();
    private Map<String, List<String>> messages = new HashMap<>();
    private long logicalClock = 0;

    public SubscriberService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void init() {
        updateLeaderAndTopics();
    }

    @Scheduled(fixedRate = 3000)
    public void updateLeaderAndTopics() {
        incrementClock();
        logTimestamp("Updating leader and topics");
        try {
            this.leader = restTemplate.getForObject(coordinatorUrl + "/api/leader?timestamp=" + logicalClock, String.class);
            this.topics = restTemplate.getForObject(leader + "/api/topics?timestamp=" + logicalClock, Set.class);
            System.out.println("Updated leader: " + leader);
            System.out.println("Updated topics: " + topics);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void subscribeToTopic(String topic) {
        incrementClock();
        logTimestamp("Subscribing to topic: " + topic);
        if ("0".equals(port)) {
            return;
        }
        try {
            String subscriberUrl = "http://localhost:" + port;
            System.out.println("My port is: " + port);
            restTemplate.postForObject(leader + "/api/add-subscriber?topic=" + topic + "&timestamp=" + logicalClock, subscriberUrl, String.class);
            subscribedTopics.add(topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unsubscribeFromTopic(String topic) {
        incrementClock();
        logTimestamp("Unsubscribing from topic: " + topic);
        if ("0".equals(port)) {
            return;
        }
        try {
            String subscriberUrl = "http://localhost:" + port;
            System.out.println("My port is: " + port);
            restTemplate.postForObject(leader + "/api/remove-subscriber?topic=" + topic + "&timestamp=" + logicalClock, subscriberUrl, String.class);
            subscribedTopics.remove(topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getMessages(String topic) {
        incrementClock();
        logTimestamp("Getting messages for topic: " + topic);
        try {
            String subscriberUrl = "http://localhost:" + port;
            System.out.println("Requesting messages for topic: " + topic);
            String url = leader + "/api/messages?topic=" + topic + "&subscriberUrl=" + subscriberUrl + "&timestamp=" + logicalClock;
            List<String> messages = restTemplate.getForObject(url, List.class);
            System.out.println("Received messages: " + messages);
            return messages;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public String getLeader() {
        incrementClock();
        return leader;
    }

    public Set<String> getTopics() {
        incrementClock();
        return topics;
    }

    public Set<String> getSubscribedTopics() {
        incrementClock();
        return subscribedTopics;
    }

    public void setPort(String port) {
        incrementClock();
        this.port = port;
    }

    private synchronized void incrementClock() {
        logicalClock++;
    }

    public synchronized void updateClock(long receivedTimestamp) {
        logicalClock = Math.max(logicalClock, receivedTimestamp) + 1;
    }

    public synchronized long getLogicalClock() {
        return logicalClock;
    }

    private void logTimestamp(String action) {
        System.out.println("Timestamp: " + logicalClock + " - " + action);
    }
}
