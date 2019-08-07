package com.stack.library.model.email;

import com.stack.library.constants.StackEmailConstants;
import lombok.Getter;

import static com.stack.library.constants.StackEmailConstants.*;

public enum StackEmailTemplate {
    STACK_CREATED(STACK_CREATED_TOPIC, STACK_CREATED_SUBJECT, STACK_CREATED_MESSAGE),
    TASK_PUSHED(TASK_PUSHED_TOPIC, TASK_PUSHED_SUBJECT, TASK_PUSHED_MESSAGE);


    @Getter
    private String topic;
    @Getter
    private String subject;
    @Getter
    private String message;

    StackEmailTemplate(String topic, String subject, String message) {
        this.topic = topic;
        this.message = message;
        this.subject = subject;
    }


    public static StackEmailTemplate get(String topic) {
        switch (topic) {
            case StackEmailConstants.STACK_CREATED_TOPIC:
                return STACK_CREATED;
            case TASK_PUSHED_TOPIC:
                return TASK_PUSHED;
        }
        return null;
    }
}
