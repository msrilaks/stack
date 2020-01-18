package com.stack.taskservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "stack.purge.policy")
@Getter
@Setter
public class StackPurgeProperties {
    private long completedTasksDays;
    private long deletedTasksDays;
    private long pushedTasksDays;
}
