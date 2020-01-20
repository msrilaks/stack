package com.stack.email.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stack.email.service.StackEmailService;
import com.stack.library.model.email.EmailRequest;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

@Configuration
public class PubSubConfiguration {

    @Autowired
    StackEmailService stackEmailService;

    @StreamListener(Sink.INPUT)
    public void handleMessage(Message<String> message) {
        System.out.println("### SRI Message arrived! Payload: " + message.getPayload());
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            EmailRequest emailRequest = objectMapper.readValue(message.getPayload(), EmailRequest.class);
            stackEmailService.sendEmail(emailRequest);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
