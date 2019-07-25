package com.stack.email.model;

import static com.stack.email.util.StackEmailConstants.*;

public enum StackEmailTemplate {
    STACK_CREATED(STACK_CREATED_TOPIC,STACK_CREATED_SUBJECT,STACK_CREATED_MESSAGE);
    private StackEmailTemplate (String topic, String subject, String message) {
        this.topic = topic;
        this.message = message;
        this.subject = subject;
    }
    private String topic;
    private String subject;
    private String message;
}
