package com.stack.taskservice.configuration;

import com.stack.taskservice.context.StackRequestContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class StackRequestConfiguration {
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST,
           proxyMode = ScopedProxyMode.TARGET_CLASS)
    public StackRequestContext stackRequestContext() {
        return new StackRequestContext();
    }


}
