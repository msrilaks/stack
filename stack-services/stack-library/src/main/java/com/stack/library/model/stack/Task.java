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
@TaskConstraint
public class Task implements Comparable<Task>, Cloneable {
    @Id
    private UUID                     id;
    private String                   stackId;
    private String                   userId;
    private String                   createdByUserId;
    private String                   location;
    private String                   origin;
    private String                   description;
    private String                   tags;
    private Long                     completedTimeStamp;
    private Long                     pushedTimeStamp;
    private Long                     deletedTimeStamp;
    private Long                     completeByTimeStamp;
    private TreeMap<UUID, TaskPushLogEntry> taskPushLogEntryMap = new TreeMap<>();
    //private StackEvent stackEvent;
    @CreatedDate
    private Date                     createdDate;
    @LastModifiedDate
    private Date                     lastModifiedDate;

    @Override
    public int compareTo(Task task) {
        return this.createdDate.compareTo(task.getCreatedDate()) * -1;
    }

    public void setTags(final String tagsInput){
        if(tagsInput!=null || !tagsInput.isEmpty()) {
            tagsInput.replaceAll("\\s","");
        }
        this.tags = tagsInput;
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

    public boolean containsTags(List<String> tagList) {
        if (tags == null || tags.isEmpty()) {
            return false;
        }
        if (tagList==null || tagList.isEmpty()) {
            return true;
        }

        List<String> tagArr = Arrays.asList(tags.toLowerCase().split("\\s*,\\s*"));

        for(String tag : tagList){
            if(!tagArr.contains(tag.toLowerCase().trim())) {
                return false;
            }
        }
        return true;
    }

    public void addTaskPushLogEntry(String pushedToUserId) {
        pushedTimeStamp = (pushedTimeStamp == null)?
                          System.currentTimeMillis() : pushedTimeStamp;
        TaskPushLogEntry taskPushLogEntry = new TaskPushLogEntry(stackId, userId,
                                                                    pushedToUserId);
        taskPushLogEntryMap.put(taskPushLogEntry.getId(), taskPushLogEntry);
    }

    public String getTitle() {
        return ((description.length() > 30)? description.substring(0,29) : description);
    }

    public boolean isPushed() {
        return (pushedTimeStamp!=null);
    }

    public void resetTimeStamps() {
        setDeletedTimeStamp(null);
        setCompletedTimeStamp(null);
        setPushedTimeStamp(null);
    }
}