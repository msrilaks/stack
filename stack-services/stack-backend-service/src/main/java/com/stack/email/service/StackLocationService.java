package com.stack.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StackLocationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            StackLocationService.class.getName());

    @Scheduled(fixedDelay = 1000)
    public void scheduledTaskLocationRefresh() {
        LOGGER.info(
                "### SRI Fixed delay task - " + System.currentTimeMillis() / 1000);
    }
}
