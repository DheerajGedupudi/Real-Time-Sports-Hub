package com.project.subscriber.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LeaderBrokerService {

    @Value("${coordinator.url}")
    private String coordinatorUrl;

    private RestTemplate restTemplate = new RestTemplate();
    private long logicalClock = 0;

    public String getLeaderBrokerUrl() {
        incrementClock();
        logTimestamp("Getting leader broker URL");
        String url = coordinatorUrl + "/api/leader?timestamp=" + logicalClock;
        return restTemplate.getForObject(url, String.class);
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
