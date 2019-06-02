package com.stack.taskservice.model;

import com.stack.taskservice.validator.StackConstraint;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@ToString
@StackConstraint
@Builder(toBuilder = true)
public class Stack {
    @Id
    private String            id;
    private String            userId;
    private Long              createdTimeStamp;
    private Long              lastUpdatedTimeStamp;
    private Map<String, Task> tasks;
}