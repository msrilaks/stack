package com.stack.library.model.email;

import com.stack.library.constants.StackEmailConstants;
import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.Task;
import com.stack.library.model.user.User;
import lombok.Getter;

import javax.validation.Valid;

import java.util.List;
import java.util.UUID;

import static com.stack.library.constants.StackEmailConstants.*;

public enum StackEmailTemplate {
    STACK_CREATED(STACK_CREATED_TOPIC, STACK_CREATED_SUBJECT, STACK_CREATED_MESSAGE),
    TASK_PUSHED(TASK_PUSHED_TOPIC, TASK_PUSHED_SUBJECT, TASK_PUSHED_MESSAGE),
    TASK_NUDGE(TASK_NUDGE_TOPIC, TASK_NUDGE_SUBJECT, TASK_NUDGE_MESSAGE),
    TASK_NEAR_LOCATION(TASK_NEAR_LOCATION_TOPIC, TASK_NEAR_LOCATION_SUBJECT, TASK_NEAR_LOCATION_MESSAGE);


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
            case TASK_NUDGE_TOPIC:
                return TASK_NUDGE;
            case TASK_NEAR_LOCATION_TOPIC:
                return TASK_NEAR_LOCATION;
        }
        return null;
    }

    public String getMessage(
            Stack stack,
            List<Task> taskList,
            User user,
            User fromUser,
            @Valid BackendServiceRequest backendServiceRequest) {
        String userName = (user == null) ? "" : user.getName();
        String fromUserName = (fromUser == null) ? "" : fromUser.getName();
        StringBuffer tasks = new StringBuffer();
        for(Task task:taskList) {
            String taskTitle = (task == null) ? "" : task.getDescription();
            String taskId = (task == null) ? "" : task.getId().toString();
            String taskLink = "https://www.stackitdown.com/stackitdown/";// + taskId;
            tasks.append(java.text.MessageFormat.format(TASK_MESSAGE, taskTitle, taskLink));
        }
        switch (topic) {
            case StackEmailConstants.STACK_CREATED_TOPIC: {
                return java.text.MessageFormat.format(message, userName);
            }
            case TASK_NEAR_LOCATION_TOPIC:
                return java.text.MessageFormat.format(message,
                        userName,
                        fromUserName,
                        tasks.toString()
                        );
            case TASK_PUSHED_TOPIC:
            case TASK_NUDGE_TOPIC:
                return java.text.MessageFormat.format(message,
                                                      userName,
                                                      fromUserName,
                                                      tasks.toString());
        }
        return message;
    }

    public String getMessage() {
        return message;
    }
}
