package com.stack.taskservice.handler;

import com.stack.taskservice.model.Stack;
import com.stack.taskservice.model.Task;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TaskHandler {
    public void initTask(Task task, Stack stack) {
        task.setId(UUID.randomUUID());
        task.setStackId(stack.getId());
        task.setCreatedByUserId(stack.getUserId());
        touchCreated(task);
    }

    public Task updateTaskDetails(Task destTask, Task srcTask) {
        destTask.setCategory(srcTask.getCategory());
        destTask.setLabel(srcTask.getLabel());
        destTask.setDescription(srcTask.getDescription());
        touch(destTask);
        return destTask;
    }

    public Task cloneTask(Task fromTask, Stack toStack) {
        Task task = fromTask.toBuilder().build();
        task.setId(fromTask.getId());
        task.setStackId(toStack.getId());
        task.setUserId(toStack.getUserId());
        task.setCreatedByUserId(fromTask.getUserId());
        touchCreated(task);
        return task;
    }

    public void touch(Task task) {
        task.setLastModifiedTimeStamp(System.currentTimeMillis());
    }

    public void touchCompleted(Task task, boolean markCompleted) {
        long ts = System.currentTimeMillis();
        if (markCompleted) {
            task.setCompletedTimeStamp(ts);
            task.setLastModifiedTimeStamp(ts);
        } else {
            task.setCompletedTimeStamp(null);
            task.setLastModifiedTimeStamp(ts);
        }
    }

    public void touchCreated(Task task) {
        long ts = System.currentTimeMillis();
        task.setCreatedTimeStamp(ts);
        task.setLastModifiedTimeStamp(ts);
        task.setDeletedTimeStamp(null);
        task.setCompletedTimeStamp(null);
        task.setMovedTimeStamp(null);
    }


    public void touchMoved(Task task) {
        long ts = System.currentTimeMillis();
        task.setMovedTimeStamp(ts);
        task.setLastModifiedTimeStamp(ts);
    }

    public Optional<Task> getTask(UUID taskId, Stack stack) {
        return stack.getTaskList().stream()
                    .filter(x -> x.getId().equals(taskId))
                    .findAny();
    }


    public void touchDeleted(UUID taskId, Stack stack) {
        Optional<Task> optionalTask = getTask(taskId, stack);
        optionalTask.ifPresent(x -> {touchDeleted(x);});
    }

    private void touchDeleted(Task task) {
        long ts = System.currentTimeMillis();
        task.setDeletedTimeStamp(ts);
        task.setLastModifiedTimeStamp(ts);
    }
}
