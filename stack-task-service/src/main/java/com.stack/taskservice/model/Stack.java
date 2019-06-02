package com.stack.taskservice.model;

import com.stack.taskservice.validator.StackConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@ToString
@StackConstraint
public class Stack {
    @Id
    private String            id;
    private String            userId;
    private Long              createdTimeStamp;
    private Long              lastUpdatedTimeStamp;
    private Map<String, Task> tasks;
}