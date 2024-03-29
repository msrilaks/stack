package com.stack.library.model.stack;

import com.stack.library.validator.TaskConstraint;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class TaskPushLogEntry implements Cloneable {
    @Id
    private UUID                     id;
    private String                   stackId;
    private String                   userId;
    private String                   pushedUserId;
    private Long                     createdTimeStamp;

    public TaskPushLogEntry(String stackId, String userId, String pushedUserId) {
        setId(UUID.randomUUID());
        setStackId(stackId);
        setUserId(userId);
        setPushedUserId(pushedUserId);
        setCreatedTimeStamp(System.currentTimeMillis());
    }
}