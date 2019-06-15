package com.stack.taskservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Task implements Comparable<Task> {
    @Id
    private UUID   id;
    private String stackId;
    private String userId;
    private String createdByUserId;
    private String origin;
    private String category;
    private String label;
    private String description;
    private Long   createdTimeStamp;
    private Long   lastModifiedTimeStamp;
    private Long   completedTimeStamp;
    private Long   movedTimeStamp;
    private Long   deletedTimeStamp;
    private Long   completeByTimeStamp;

    @Override
    public int compareTo(Task task) {
        return this.createdTimeStamp.compareTo(task.getCreatedTimeStamp()) * -1;
    }
}