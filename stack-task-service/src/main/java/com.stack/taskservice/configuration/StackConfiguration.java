package com.stack.taskservice.configuration;

import com.stack.taskservice.interceptor.StackInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StackConfiguration implements WebMvcConfigurer {
    @Autowired
    StackInterceptor stackInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(stackInterceptor).addPathPatterns("/stack");
    }
}
