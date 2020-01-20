package com.stack.email.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stack.email.service.StackEmailService;
import com.stack.library.model.email.EmailRequest;
@Configuration
public class PubSubConfiguration {

    @Autowired
    StackEmailService stackEmailService;

    @ServiceActivator(inputChannel = "stackEmailChannel")
    public void messageReceiver(String payload) {
        System.out.println("### SRI Message arrived! Payload: " + payload);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            EmailRequest emailRequest = objectMapper.readValue(payload, EmailRequest.class);
            stackEmailService.sendEmail(emailRequest);
        }catch(Exception e) {
            e.printStackTrace();
        }

    }
}
