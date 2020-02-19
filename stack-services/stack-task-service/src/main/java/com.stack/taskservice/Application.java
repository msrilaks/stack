package com.stack.taskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.cloud.stream.annotation.EnableBinding;
import com.stack.taskservice.configuration.Channels;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@SpringBootApplication
@PropertySource("classpath:application-${spring.profiles.active}.properties")
@EnableMongoRepositories
@EnableMongoAuditing
@EnableRedisRepositories
@EnableBinding(Channels.class)
@ComponentScan({"com.stack.taskservice", "com.stack.library"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}