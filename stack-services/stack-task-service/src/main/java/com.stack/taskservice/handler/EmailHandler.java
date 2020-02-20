package com.stack.taskservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stack.library.constants.StackEmailConstants;
import com.stack.library.model.email.BackendServiceRequest;
import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.Task;
import com.stack.library.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

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
            BackendServiceRequest backendServiceRequest =
                    BackendServiceRequest.builder().stackId(stack.getId()).topic(
                            StackEmailConstants.STACK_CREATED_TOPIC).build();
            String reqBodyData = new ObjectMapper().writeValueAsString(backendServiceRequest);
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

    public void postTaskPushed(Stack stack, Task task, User fromUser) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            BackendServiceRequest backendServiceRequest =
                    BackendServiceRequest.builder().stackId(stack.getId()).taskId(task.getId())
                            .fromUserEmail(fromUser.getEmail())
                            .topic(StackEmailConstants.TASK_PUSHED_TOPIC).build();
            String reqBodyData = new ObjectMapper().writeValueAsString(backendServiceRequest);
            HttpEntity<String> requestEntity =
                    new HttpEntity<>(reqBodyData, headers);
            ResponseEntity<Void> response = restTemplate.exchange(stackEmailUrl,
                                                                  HttpMethod.POST,
                                                                  requestEntity,
                                                                  Void.class);
            LOGGER.info("Stack task push notification sent");
        } catch (Exception e) {
            LOGGER.error("Stack task push  notification send failure", e);
        }
    }

    public void nudgeTaskOwners(Stack stack, UUID taskId, User fromUser) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            BackendServiceRequest backendServiceRequest =
                    BackendServiceRequest.builder().stackId(stack.getId()).taskId(taskId)
                            .fromUserEmail(fromUser.getEmail())
                            .topic(StackEmailConstants.TASK_NUDGE_TOPIC).build();
            String reqBodyData = new ObjectMapper().writeValueAsString(backendServiceRequest);
            HttpEntity<String> requestEntity =
                    new HttpEntity<>(reqBodyData, headers);
            ResponseEntity<Void> response = restTemplate.exchange(stackEmailUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Void.class);
            LOGGER.info("Stack task nudge notification sent to : " + stack.getUserId());
        } catch (Exception e) {
            LOGGER.error("Stack task nudge  notification send failure to "+stack.getUserId(), e);
        }
    }
}
