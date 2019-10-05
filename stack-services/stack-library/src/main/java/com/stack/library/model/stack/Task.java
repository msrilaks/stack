package com.stack.library.model.stack;

import com.stack.library.validator.TaskConstraint;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
@TaskConstraint
public class Task implements Comparable<Task>, Cloneable {
    @Id
    private UUID                     id;
    private String                   stackId;
    private String                   userId;
    private String                   createdByUserId;
    private String                   origin;
    private String                   category;
    private String                   label;
    private String                   description;
    private Map<String, TaskContent> taskContentMap;
    private Long                     completedTimeStamp;
    private Long                     pushedTimeStamp;
    private Long                     deletedTimeStamp;
    private Long                     completeByTimeStamp;
    @CreatedDate
    private Date                     createdDate;
    @LastModifiedDate
    private Date                     lastModifiedDate;

    @Override
    public int compareTo(Task task) {
        return this.createdDate.compareTo(task.getCreatedDate()) * -1;
    }

    @Override
    public Task clone() {
        Task task = Task.builder().build();
        task.setId(getId());
        task.setLabel(getLabel());
        task.setCategory(getCategory());
        task.setDescription(getDescription());
        task.setUserId(getUserId());
        task.setCreatedByUserId(getCreatedByUserId());
        task.setCreatedDate(getCreatedDate());
        task.setDeletedTimeStamp(null);
        task.setCompletedTimeStamp(null);
        task.setPushedTimeStamp(null);
        return task;
    }
}