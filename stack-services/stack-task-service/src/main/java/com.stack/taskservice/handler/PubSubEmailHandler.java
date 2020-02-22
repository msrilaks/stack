package com.stack.taskservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stack.library.model.email.BackendServiceRequest;
import com.stack.library.model.stack.Location;
import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.Task;
import com.stack.library.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.stack.taskservice.configuration.Channels;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@Component
public class PubSubEmailHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            PubSubEmailHandler.class.getName());

    private final MessageChannel STACK_EMAIL_TOPIC;

    public PubSubEmailHandler(Channels channels) {
        STACK_EMAIL_TOPIC = channels.STACK_EMAIL_TOPIC();
    }

    public void sendEmailNotification(Stack stack, String topic) {
        try {
            BackendServiceRequest backendServiceRequest =
                    BackendServiceRequest.builder().stackId(stack.getId()).topic(
                            topic).build();
            String message = new ObjectMapper().writeValueAsString(backendServiceRequest);
            STACK_EMAIL_TOPIC.send(MessageBuilder.withPayload(message).build());
            LOGGER.info("Stack message posted to pub sub: " + topic);
        } catch (Exception e) {
            LOGGER.error("Stack message post to pub sub failure: "+topic, e);
        }
    }

    public void sendEmailNotification(Stack stack, Task task, User fromUser, String topic) {
        try {
            BackendServiceRequest backendServiceRequest =
                    BackendServiceRequest.builder().stackId(stack.getId()).taskId(task.getId())
                            .fromUserEmail(fromUser.getEmail())
                            .topic(topic).build();
            String message = new ObjectMapper().writeValueAsString(backendServiceRequest);
            STACK_EMAIL_TOPIC.send(MessageBuilder.withPayload(message).build());
            LOGGER.info("Stack message posted to pub sub: " + topic);
        } catch (Exception e) {
            LOGGER.error("Stack message post to pub sub failure: "+topic, e);
        }
    }

    public void sendEmailNotification(Stack stack, String topic, Location location) {
        try {
            BackendServiceRequest backendServiceRequest =
                    BackendServiceRequest.builder().stackId(stack.getId())
                                         .location(location)
                                         .topic(topic).build();
            String message = new ObjectMapper().writeValueAsString(backendServiceRequest);
            STACK_EMAIL_TOPIC.send(MessageBuilder.withPayload(message).build());
            LOGGER.info("Stack message posted to pub sub: " + topic);
        } catch (Exception e) {
            LOGGER.error("Stack message post to pub sub failure: "+topic, e);
        }
    }
}
