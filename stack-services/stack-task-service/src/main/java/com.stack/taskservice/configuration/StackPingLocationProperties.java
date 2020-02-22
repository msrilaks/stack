package com.stack.taskservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "stack.ping.policy.location")
@Getter
@Setter
public class StackPingLocationProperties {
    private long searchIntervalElapsedMinutes;
    private long searchDistanceMiles;
}
