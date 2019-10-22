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
    private String                   description;
    private String                   tags;
    private Map<String, TaskContent> taskContentMap;
    private Long                     completedTimeStamp;
    private Long                     pushedTimeStamp;
    private Long                     deletedTimeStamp;
    private Long                     completeByTimeStamp;
    //private StackEvent stackEvent;
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
        task.setTags(getTags());
        task.setDescription(getDescription());
        task.setUserId(getUserId());
        task.setCreatedByUserId(getCreatedByUserId());
        task.setCreatedDate(getCreatedDate());
        task.resetTimeStamps();
        return task;
    }

    public String getTitle() {
        return ((description.length() > 30)? description.substring(0,29) : description);
    }

    public void resetTimeStamps() {
        setDeletedTimeStamp(null);
        setCompletedTimeStamp(null);
        setPushedTimeStamp(null);
    }
}