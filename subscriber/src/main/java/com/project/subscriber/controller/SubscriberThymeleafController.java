package com.project.subscriber.controller;

import com.project.subscriber.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
public class SubscriberThymeleafController {

    @Autowired
    private SubscriberService subscriberService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/broker")
    public String leader(Model model) {
        String leader = subscriberService.getLeader();
        model.addAttribute("leader", leader);
        return "broker";
    }

    @GetMapping("/topics")
    public String topics(Model model) {
        Set<String> topics = subscriberService.getTopics();
        Set<String> subscribedTopics = subscriberService.getSubscribedTopics();
        model.addAttribute("topics", topics);
        model.addAttribute("subscribedTopics", subscribedTopics);
        return "topics";
    }

    @PostMapping("/subscribe")
    public String subscribe(@RequestParam String topic) {
        subscriberService.subscribeToTopic(topic);
        return "redirect:/topics";
    }

    @PostMapping("/unsubscribe")
    public String unsubscribe(@RequestParam String topic) {
        subscriberService.unsubscribeFromTopic(topic);
        return "redirect:/topics";
    }

    @GetMapping("/subscribed-topics")
    public String subscribedTopics(Model model) {
        Set<String> subscribedTopics = subscriberService.getSubscribedTopics();
        model.addAttribute("subscribedTopics", subscribedTopics);
        return "my-topics";
    }

    @GetMapping("/messages")
    public String messages(@RequestParam String topic, Model model) {
        List<String> messages = subscriberService.getMessages(topic);
        model.addAttribute("messages", messages);
        return "messages";
    }
}
