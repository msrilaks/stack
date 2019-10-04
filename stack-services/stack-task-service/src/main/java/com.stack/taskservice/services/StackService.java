package com.stack.taskservice.services;

import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.Task;
import com.stack.taskservice.context.StackRequestContext;
import com.stack.taskservice.handler.EmailHandler;
import com.stack.taskservice.repository.StackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.Valid;
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
            Boolean isToDo) {
        Stack stack = stackRequestContext.getStack();
        if (isDeleted) {
            return stackRepository.fetchDeletedTasks(stack);
        } else if (isPushed) {
            return stackRepository.fetchPushedTasks(stack);
        } else if (isCompleted) {
            return stackRepository.fetchCompletedTasks(stack);
        } else if (isToDo) {
            return stackRepository.fetchToDoTasks(stack);
        }
        return stackRepository.fetchAllTasks(stack);
    }

    public Task getTask(UUID taskId) {
        Stack stack = stackRequestContext.getStack();
        return stackRepository.findTaskById(taskId, stack);
    }

    public Task createTask(Task task) {
        Stack stack = stackRequestContext.getStack();
        Task createdTask = stackRepository.saveTaskToStack(task, stack);
        if (!task.getUserId().equals(stack.getUserId())) {
            return pushTask(createdTask.getId(), task.getUserId());
        }
        return createdTask;
    }

    public Task pushTask(UUID taskId, String toUserId) {
        Stack stack = stackRequestContext.getStack();
        Task task = stackRepository.findTaskById(taskId, stack);
        if (toUserId != null) {
            Stack pushStack = stackRepository.findByUserId(toUserId);
            if (pushStack == null) {
                pushStack = createStack(Stack.builder().userId(toUserId).build());
            }
            Task pushTask = task.clone();
            pushTask.setUserId(toUserId);
            stackRepository.saveTaskToStack(pushTask, pushStack);
            photoService.copyPhotos(stack.getId(),taskId.toString(),pushStack.getId(),
                                    pushTask.getId().toString());
            emailHandler.postTaskPushed(pushStack, pushTask);
            stackRepository.saveTaskAsPushed(taskId, stack);
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
        Task toModifyTask = stackRepository.findTaskById(taskId, stack);
        if (!task.getUserId().equals(stack.getUserId()) ||
            (task.getUserId().equals(stack.getUserId()) &&
             !toModifyTask.getUserId().equals(task.getUserId()))) {
            stackRepository.saveTaskAsModified(task, taskId, stack);
            return pushTask(taskId, task.getUserId());
        }
        return stackRepository.saveTaskAsModified(task, taskId, stack);
    }

    public Task deleteTask(UUID taskId) {
        Stack stack = stackRequestContext.getStack();
        return stackRepository.saveTaskAsDeleted(taskId, stack);
    }

}
