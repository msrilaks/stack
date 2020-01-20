package com.stack.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;

import org.springframework.messaging.MessageChannel;

import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;

@SpringBootApplication
@PropertySource("classpath:application-${spring.profiles.active}.properties")
@PropertySource("classpath:client_secrets_${spring.profiles.active}.json")
@ComponentScan({"com.stack.email", "com.stack.library"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("stackEmailChannel") MessageChannel inputChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "STACK_EMAIL_SUBSCRIPTION");
        adapter.setOutputChannel(inputChannel);
        return adapter;
    }
    @Bean
    public MessageChannel myInputChannel() {
        return new DirectChannel();
    }

}