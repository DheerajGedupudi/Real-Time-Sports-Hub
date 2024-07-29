package com.project.broker.controller;

import com.project.broker.service.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class BrokerThymeleafController {

    @Autowired
    private BrokerService brokerService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("timestamp", brokerService.getLogicalClock());
        return "index";
    }

    @GetMapping("/broker")
    public String broker(Model model, @RequestParam long timestamp) {
        brokerService.updateClock(timestamp);
        String leader = brokerService.getLeader();
        model.addAttribute("leader", leader);
        model.addAttribute("timestamp", brokerService.getLogicalClock());
        return "broker";
    }

    @GetMapping("/leader")
    public String leader(Model model, @RequestParam long timestamp) {
        brokerService.updateClock(timestamp);
        String leader = brokerService.getLeader();
        model.addAttribute("leader", leader);
        model.addAttribute("timestamp", brokerService.getLogicalClock());
        return "leader";
    }

    @GetMapping("/brokers")
    public String brokers(Model model, @RequestParam long timestamp) {
        brokerService.updateClock(timestamp);
        List<String> brokers = brokerService.getBrokers();
        model.addAttribute("brokers", brokers);
        model.addAttribute("timestamp", brokerService.getLogicalClock());
        return "brokers";
    }

    @GetMapping("/topics")
    public String getTopics(Model model, @RequestParam long timestamp) {
        brokerService.updateClock(timestamp);
        Set<String> topics = brokerService.getTopics();
        model.addAttribute("topics", topics);
        model.addAttribute("timestamp", brokerService.getLogicalClock());
        return "topics";
    }

    @GetMapping("/messages")
    public String getMessages(Model model, @RequestParam long timestamp) {
        brokerService.updateClock(timestamp);
        Map<String, List<String>> messages = brokerService.getMessages();
        model.addAttribute("messages", messages);
        model.addAttribute("timestamp", brokerService.getLogicalClock());
        return "messages";
    }

    @GetMapping("/subscribers")
    public String getSubscribers(Model model, @RequestParam long timestamp) {
        brokerService.updateClock(timestamp);
        Map<String, List<String>> subscribers = brokerService.getSubscribersWithTopics();
        model.addAttribute("subscribers", subscribers);
        model.addAttribute("timestamp", brokerService.getLogicalClock());
        return "subscribers";
    }
}
