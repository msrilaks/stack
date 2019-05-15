package com.stack.taskservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Stack {
    @Id
    private String     id;
    private String     userId;
    private Long       createdTimeStamp;
    private Long       lastUpdatedTimeStamp;
    private List<Task> taskList;
}