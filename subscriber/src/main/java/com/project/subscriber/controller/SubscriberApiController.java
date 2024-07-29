package com.project.subscriber.controller;

import com.project.subscriber.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class SubscriberApiController {

    @Autowired
    private SubscriberService subscriberService;

    @GetMapping("/leader")
    public String getLeader(@RequestParam long timestamp) {
        subscriberService.updateClock(timestamp);
        return subscriberService.getLeader();
    }

    @GetMapping("/topics")
    public Set<String> getTopics(@RequestParam long timestamp) {
        subscriberService.updateClock(timestamp);
        return subscriberService.getTopics();
    }

    @PostMapping("/subscribe")
    public void subscribeToTopic(@RequestBody String topic, @RequestParam long timestamp) {
        subscriberService.updateClock(timestamp);
        subscriberService.subscribeToTopic(topic);
    }

    @GetMapping("/messages")
    public List<String> getMessages(@RequestParam String topic, @RequestParam long timestamp) {
        subscriberService.updateClock(timestamp);
        return subscriberService.getMessages(topic);
    }

    @GetMapping("/ping")
    public String ping(@RequestParam long timestamp) {
        subscriberService.updateClock(timestamp);
        return "pong";
    }
}
