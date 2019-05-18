package com.stack.taskservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Task {
    @Id
    private UUID   id;
    private String userId;
    private String createdByUserId;
    private String origin;
    private String category;
    private String label;
    private String description;
    private boolean isCompleted;
    private Long   createdTimeStamp;
    private Long   lastModifiedTimeStamp;
    private Long   completedTimeStamp;
    private Long   deletedTimeStamp;
}