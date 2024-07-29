package com.project.publisher.service;

import com.project.publisher.model.PublisherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

@Service
public class PublisherService {

    private final RestTemplate restTemplate;
    private final PublisherData publisherData = new PublisherData();

    @Value("${coordinator.url}")
    private String coordinatorUrl;

    private String leaderBrokerUrl;
    private long logicalClock = 0;

    public PublisherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void init() {
        updateLeaderBroker();
    }

    public void setTopic(String topic) {
        incrementClock();
        logTimestamp("Setting topic: " + topic);
        this.publisherData.setTopic(topic);
        try {
            if (leaderBrokerUrl != null) {
                restTemplate.postForObject(leaderBrokerUrl + "/api/add-topic?timestamp=" + logicalClock, topic, String.class);
                System.out.println("Topic set: " + topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTopic() {
        return this.publisherData.getTopic();
    }

    public void publishMessage(String message) {
        incrementClock();
        logTimestamp("Publishing message: " + message);
        this.publisherData.getPublishedMessages().add(message);
        try {
            if (leaderBrokerUrl != null) {
                restTemplate.postForObject(leaderBrokerUrl + "/api/add-message?topic=" + this.publisherData.getTopic() + "&timestamp=" + logicalClock, message, String.class);
                System.out.println("Published message: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 3000)
    public void pingLeaderBroker() {
        incrementClock();
        logTimestamp("Pinging leader broker");
        if (leaderBrokerUrl != null) {
            try {
                restTemplate.getForObject(leaderBrokerUrl + "/api/ping?timestamp=" + logicalClock, String.class);
                System.out.println("Pinged leader broker at: " + leaderBrokerUrl);
            } catch (Exception e) {
                System.out.println("Leader broker not responding, fetching new leader from coordinator");
                updateLeaderBroker();
            }
        }
    }

    private void updateLeaderBroker() {
        incrementClock();
        logTimestamp("Updating leader broker");
        try {
            this.leaderBrokerUrl = restTemplate.getForObject(coordinatorUrl + "/api/leader?timestamp=" + logicalClock, String.class);
            System.out.println("Updated leader broker: " + leaderBrokerUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLeaderBrokerUrl() {
        return leaderBrokerUrl;
    }

    public void setLeaderBrokerUrl(String leaderBrokerUrl) {
        this.leaderBrokerUrl = leaderBrokerUrl;
    }

    public String getCoordinatorUrl() {
        return coordinatorUrl;
    }

    public void setCoordinatorUrl(String coordinatorUrl) {
        this.coordinatorUrl = coordinatorUrl;
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
