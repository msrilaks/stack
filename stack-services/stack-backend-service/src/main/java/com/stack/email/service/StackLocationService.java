package com.stack.email.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StackLocationService {
    @Scheduled(fixedDelay = 1000)
    public void scheduledTaskLocationRefresh() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
    }
}
