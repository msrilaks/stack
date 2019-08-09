package com.stack.taskservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stack.library.constants.StackEmailConstants;
import com.stack.library.model.email.EmailRequest;
import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmailHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            EmailHandler.class.getName());

    @Value("${stack.email.url}")
    private String stackEmailUrl;

    public void postStackCreated(Stack stack) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            EmailRequest emailRequest =
                    EmailRequest.builder().stackId(stack.getId()).topic(
                            StackEmailConstants.STACK_CREATED_TOPIC).build();
            String reqBodyData = new ObjectMapper().writeValueAsString(emailRequest);
            HttpEntity<String> requestEntity =
                    new HttpEntity<>(reqBodyData, headers);
            ResponseEntity<Void> response = restTemplate.exchange(stackEmailUrl,
                                                                  HttpMethod.POST,
                                                                  requestEntity,
                                                                  Void.class);
            LOGGER.info("Stack create notification sent");
        } catch (Exception e) {
            LOGGER.error("Stack create notification send failure", e);
        }
    }

    public void postTaskPushed(Stack stack, Task task) {
    }
}
