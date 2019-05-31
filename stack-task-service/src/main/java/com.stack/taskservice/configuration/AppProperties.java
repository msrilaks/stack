package com.stack.taskservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.auth")
@Getter
@Setter
public class AppProperties {
    private String tokenSecret;
    private long   tokenExpirationMsec;
}
