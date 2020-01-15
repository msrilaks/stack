package com.stack.library.model.email;

import com.stack.library.constants.StackEmailConstants;
import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.Task;
import com.stack.library.model.user.User;
import lombok.Getter;

import javax.validation.Valid;

import static com.stack.library.constants.StackEmailConstants.*;

public enum StackEmailTemplate {
    STACK_CREATED(STACK_CREATED_TOPIC, STACK_CREATED_SUBJECT, STACK_CREATED_MESSAGE),
    TASK_PUSHED(TASK_PUSHED_TOPIC, TASK_PUSHED_SUBJECT, TASK_PUSHED_MESSAGE);


    @Getter
    private String topic;
    @Getter
    private String subject;

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

    public String getMessage(
            Stack stack,
            Task task,
            User user,
            User fromUser,
            @Valid EmailRequest emailRequest) {
        String userName = (user == null) ? "" : user.getName();
        String fromUserName = (fromUser == null) ? "" : fromUser.getName();
        String taskTitle = (task == null)? "": task.getTitle();
        switch (topic) {
            case StackEmailConstants.STACK_CREATED_TOPIC: {
                return java.text.MessageFormat.format(message, userName);
            }
            case TASK_PUSHED_TOPIC:
                return java.text.MessageFormat.format(message,
                                                      userName,
                                                      fromUserName,
                                                      taskTitle,
                                                      stack.getId(),
                                                      task.getId());
        }
        return message;
    }

    public String getMessage() {
        return message;
    }
}
