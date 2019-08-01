package com.stack.library.model.email;

import static com.stack.library.constants.StackEmailConstants.*;

public enum StackEmailTemplate {
    STACK_CREATED(STACK_CREATED_TOPIC, STACK_CREATED_SUBJECT, STACK_CREATED_MESSAGE);

    private String topic;
    private String subject;
    private String message;

    StackEmailTemplate(String topic, String subject, String message) {
        this.topic = topic;
        this.message = message;
        this.subject = subject;
    }
}
