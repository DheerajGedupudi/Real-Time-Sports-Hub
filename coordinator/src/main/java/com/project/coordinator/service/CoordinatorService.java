package com.project.coordinator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class CoordinatorService {

    private static final Logger logger = LoggerFactory.getLogger(CoordinatorService.class);
    private static final long TIMEOUT = 3000; // 3 seconds

    private final List<String> brokers = new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<String, Long> brokerHeartbeatTimestamps = new ConcurrentHashMap<>();
    private String leader = null;

    private long logicalClock = 0;

    public synchronized void incrementClock() {
        logicalClock++;
    }

    public synchronized void updateClock(long receivedTimestamp) {
        logicalClock = Math.max(logicalClock, receivedTimestamp) + 1;
    }

    public synchronized long getLogicalClock() {
        return logicalClock;
    }

    public void registerBroker(String brokerUrl) {
        incrementClock();
        if (!brokers.contains(brokerUrl)) {
            brokers.add(brokerUrl);
            logger.info("Registered broker: {}", brokerUrl);
        }
        brokerHeartbeatTimestamps.put(brokerUrl, System.currentTimeMillis());
        if (leader == null || !brokers.contains(leader)) {
            leader = brokerUrl;
        }
    }

    public void heartbeat(String brokerUrl) {
        incrementClock();
        brokerHeartbeatTimestamps.put(brokerUrl, System.currentTimeMillis());
        logger.info("Received heartbeat from {}", brokerUrl);
    }

    @Scheduled(fixedRate = 3000)
    public void checkHeartbeats() {
        long currentTime = System.currentTimeMillis();
        List<String> activeBrokers = brokerHeartbeatTimestamps.entrySet().stream()
                .filter(entry -> (currentTime - entry.getValue()) <= TIMEOUT)
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());

        brokers.retainAll(activeBrokers);
        brokerHeartbeatTimestamps.keySet().retainAll(activeBrokers);

        if (!brokers.contains(leader)) {
            startElection();
        }

        logger.info("Active brokers: {}", brokers);
    }

    public List<String> getBrokers() {
        incrementClock();
        return brokers;
    }

    public String getLeader() {
        incrementClock();
        return leader;
    }

    private void startElection() {
        incrementClock();
        String newLeader = brokers.stream()
                .max(String::compareTo)
                .orElse(null);
        if (newLeader!=null)
        {
            leader = newLeader;
            logger.info("New leader elected: {}", leader);
        }
    }
}
