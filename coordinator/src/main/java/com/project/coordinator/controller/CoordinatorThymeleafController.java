package com.project.coordinator.controller;

import com.project.coordinator.service.CoordinatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoordinatorThymeleafController {

    private final CoordinatorService coordinatorService;

    @Autowired
    public CoordinatorThymeleafController(CoordinatorService coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/brokers")
    public String getBrokers(Model model) {
        model.addAttribute("brokers", coordinatorService.getBrokers());
        return "brokers";
    }

    @GetMapping("/leader")
    public String getLeader(Model model) {
        model.addAttribute("leader", coordinatorService.getLeader());
        return "leader";
    }
}
