package com.project.publisher.controller;

import com.project.publisher.service.BrokerService;
import com.project.publisher.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublisherThymeleafController {

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private BrokerService brokerService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("topic", publisherService.getTopic());
        return "index";
    }

    @GetMapping("/set-topic")
    public String setTopicPage(Model model) {
        model.addAttribute("topic", publisherService.getTopic());
        return "set-topic";
    }

    @PostMapping("/set-topic")
    public String setTopic(@RequestParam String topic, Model model) {
        publisherService.setTopic(topic);
        model.addAttribute("topic", publisherService.getTopic());
        return "set-topic";
    }

    @GetMapping("/publish")
    public String publishPage(Model model) {
        model.addAttribute("topic", publisherService.getTopic());
        return "publish";
    }

    @PostMapping("/publish")
    public String publishMessage(@RequestParam String message, Model model) {
        publisherService.publishMessage(message);
        model.addAttribute("topic", publisherService.getTopic());
        return "publish";
    }

    @GetMapping("/broker")
    public String getBroker(Model model) {
        String leaderBrokerUrl = brokerService.getLeaderBrokerUrl();
        model.addAttribute("leaderBrokerUrl", leaderBrokerUrl);
        return "broker";
    }
}
