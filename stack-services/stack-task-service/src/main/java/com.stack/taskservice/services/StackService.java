package com.stack.taskservice.services;

import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.Task;
import com.stack.library.model.stack.TaskPushLogEntry;
import com.stack.taskservice.context.StackRequestContext;
import com.stack.taskservice.handler.EmailHandler;
import com.stack.taskservice.repository.StackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class StackService {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            StackService.class.getName());

    @Autowired
    StackRepository stackRepository;

    @Autowired
    PhotoService photoService;

    @Autowired
    EmailHandler emailHandler;

    @Resource(name = "stackRequestContext")
    StackRequestContext stackRequestContext;

    public Stack createStack(Stack stack) {
        stack = stackRepository.saveStack(stack);
        emailHandler.postStackCreated(stack);
        return stack;
    }

    public void deleteStack(String stackId) {
        stackRepository.deleteById(stackId);
    }

    public Stack getStack(String stackId) {
        return stackRepository.findById(stackId).get();
    }

    public Stack getStackByUserId(String userId) {
        return stackRepository.findByUserId(userId);
    }

    public Map<String, Task> getTasks(
            Boolean isDeleted,
            Boolean isPushed,
            Boolean isCompleted,
            Boolean isToDo, List<String> tags) {
        Stack stack = stackRequestContext.getStack();
        if (isDeleted) {
            return stackRepository.fetchDeletedTasks(stack, tags);
        } else if (isPushed) {
            return stackRepository.fetchPushedTasks(stack, tags);
        } else if (isCompleted) {
            return stackRepository.fetchCompletedTasks(stack, tags);
        } else if (isToDo) {
            return stackRepository.fetchToDoTasks(stack, tags);
        }
        return stackRepository.fetchAllTasks(stack, tags);
    }

    public Task getTask(UUID taskId) {
        Stack stack = stackRequestContext.getStack();
        return stackRepository.findTaskById(taskId, stack);
    }

    public Task createTask(Task task) {
        Stack stack = stackRequestContext.getStack();
        Task createdTask = stackRepository.saveTaskToStack(task.clone(), stack);
        LOGGER.info("Created task : " + createdTask);
        //Go over the push log in the task from request body
        if (!task.getTaskPushLogEntryMap().isEmpty()) {
            //If the task in the request has push entries, honour those entries.
            task.setId(createdTask.getId());
            return pushTask(task);
        }
        return createdTask;
    }

    public Task pushTask(Task task){
        Iterator pushLogsIterator = task.getTaskPushLogEntryMap().values().iterator();
        while(pushLogsIterator.hasNext()) {
            TaskPushLogEntry pushEntry = (TaskPushLogEntry)pushLogsIterator.next();
            pushTask(task.getId(), pushEntry.getPushedUserId());
        }
        return getTask(task.getId());
    }

    public Task pushTask(UUID taskId, String toUserId) {
        Stack stack = stackRequestContext.getStack();
        Task task = stackRepository.findTaskById(taskId, stack);
        if (toUserId != null) {
            Stack pushStack = stackRepository.findByUserId(toUserId);
            if (pushStack == null) {
                pushStack = createStack(Stack.builder().userId(toUserId).build());
            }
            Task pushTask = null;
            try {
                pushTask = stackRepository.findTaskById(taskId, pushStack);
                pushTask.resetTimeStamps();
                pushTask.setDescription(task.getDescription());
            } catch (Exception e) {
                LOGGER.info("Task not found", e);
            }
            if (pushTask == null) {
                pushTask = task.clone();
            }
            stackRepository.saveTaskToStack(pushTask, pushStack);
            photoService.movePhotos(stack.getId(), taskId.toString(), pushStack.getId(),
                                    pushTask.getId().toString());
            emailHandler.postTaskPushed(pushStack, pushTask);
            stackRepository.saveTaskAsPushed(taskId, stack, toUserId);
        }
        return task;
    }

    public Task completeTask(UUID taskId, boolean markCompleted) {
        Stack stack = stackRequestContext.getStack();
        return stackRepository.saveTaskAsCompleted(markCompleted, taskId, stack);
    }

    public Task modifyTask(
            UUID taskId,
            @Valid Task task) {
        Stack stack = stackRequestContext.getStack();
        Task modifiedTask = stackRepository.saveTaskAsModified(task.clone(), taskId, stack);
        if (!task.getTaskPushLogEntryMap().isEmpty()) {
            //If the task in the request has push entries, honour those entries.
            //modifiedTask will have the older push entries too.
            return pushTask(task);
        }
        return modifiedTask;
    }

    public Task deleteTask(UUID taskId) {
        Stack stack = stackRequestContext.getStack();
        return stackRepository.saveTaskAsDeleted(taskId, stack);
    }

}
