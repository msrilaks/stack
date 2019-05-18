package com.stack.taskservice.handler;

import com.stack.taskservice.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskHandler {
    public Task copyTask(Task destTask, Task srcTask) {
        destTask.setCategory(srcTask.getCategory());
        destTask.setLabel(srcTask.getLabel());
        destTask.setDescription(srcTask.getDescription());
        if(srcTask.isCompleted()) {
            destTask.setCompleted(srcTask.isCompleted());
            touchCompleted(destTask);
        } else {
           touch(destTask);
        }
        return destTask;
    }

    public void touch(Task task) {
        task.setLastModifiedTimeStamp(System.currentTimeMillis());
    }

    public void touchCompleted(Task task) {
        long ts = System.currentTimeMillis();
        task.setCompletedTimeStamp(ts);
        task.setLastModifiedTimeStamp(ts);
    }

    public void touchCreated(Task task) {
        long ts = System.currentTimeMillis();
        task.setCreatedTimeStamp(ts);
        task.setLastModifiedTimeStamp(ts);
    }

    public void touchDeleted(Task task) {
        long ts = System.currentTimeMillis();
        task.setDeletedTimeStamp(ts);
        task.setLastModifiedTimeStamp(ts);
    }
}
