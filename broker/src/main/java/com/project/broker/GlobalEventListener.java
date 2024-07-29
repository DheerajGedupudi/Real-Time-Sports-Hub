package com.project.broker;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Component
public class GlobalEventListener {

    @EventListener
    public void handleEvent(ServletRequestHandledEvent event) {
        System.out.println("Request URL: " + event.getRequestUrl());
    }
}
