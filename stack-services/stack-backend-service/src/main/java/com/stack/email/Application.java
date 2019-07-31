package com.stack.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-${spring.profiles.active}.properties")
@PropertySource("classpath:client_secrets_${spring.profiles.active}.json")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}