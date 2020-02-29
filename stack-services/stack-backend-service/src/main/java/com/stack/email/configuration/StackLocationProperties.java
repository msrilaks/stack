package com.stack.email.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "stack.location.policy")
@Getter
@Setter
public class StackLocationProperties {
    private long taskDistanceMiles;
    private long locationRefreshTimeMinutes;
}
