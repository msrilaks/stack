package com.stack.taskservice.services;

import com.stack.taskservice.context.StackRequestContext;
import com.stack.taskservice.handler.StackHandler;
import com.stack.taskservice.handler.TaskHandler;
import com.stack.taskservice.model.Stack;
import com.stack.taskservice.model.Task;
import com.stack.taskservice.repository.StackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class StackService {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            StackService.class.getName());

    @Autowired
    StackRepository stackRepository;

    @Autowired
    TaskHandler taskHandler;

    @Autowired
    StackHandler stackHandler;

    @Resource(name = "stackRequestContext")
    StackRequestContext stackRequestContext;

    public Stack createStack(Stack stack) {
        stackHandler.initStack(stack);
        return stackRepository.save(stack);
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

    public Map<String, Task> getTasks(String stackId) {
        Stack stack = stackRequestContext.getStack();
        return stack.getTasks();
    }

    public Map<String, Task> getTasks(
            String stackId,
            Boolean isDeleted,
            Boolean isMoved,
            Boolean isCompleted,
            Boolean isToDo) {
        Stack stack = stackRequestContext.getStack();
        AtomicInteger i = new AtomicInteger(0);
        Predicate<Task> predicate = (task -> true);
        if (isDeleted) {
            predicate = (task -> task.getDeletedTimeStamp() != null);
        } else if (isMoved) {
            predicate = (task -> task.getMovedTimeStamp() != null);
        } else if (isCompleted) {
            predicate = (task -> task.getCompletedTimeStamp() != null &&
                                 task.getDeletedTimeStamp() == null);
        } else if (isToDo) {
            predicate =
                    (task -> task.getDeletedTimeStamp() == null &&
                             task.getCompletedTimeStamp() == null &&
                             task.getMovedTimeStamp() == null);
        }
        return stack.getTasks().values()
                    .stream()
                    .filter(predicate)
                    .collect(Collectors.toMap(
                            n -> i.incrementAndGet() + "",
                            t -> t,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public Task getTask(String stackId, UUID taskId) {
        Stack stack = stackRequestContext.getStack();
        Optional<Task> optionalTask = taskHandler.getTask(taskId, stack);
        return optionalTask.orElse(null);
    }


    public Task createTask(Task task) {
        Stack stack = getStackByUserId(task.getUserId());
        if (stack == null) {
            stack = createStack(Stack.builder().userId(task.getUserId()).build());
        }
        return createTask(stack.getId(), task);
    }

    public Task createTask(String stackId, Task task) {
        Stack stack = stackRequestContext.getStack();
        taskHandler.initTask(task, stack);
        stack.getTasks().put(String.valueOf(task.getId()), task);
        stackHandler.reorderTasks(stack);
        Stack savedStack = stackRepository.save(stack);
        return taskHandler.getTask(task.getId(), savedStack).orElse(null);
    }

    public Task completeTask(String stackId, UUID taskId, boolean markCompleted) {
        Stack stack = stackRequestContext.getStack();
        Optional<Task> optionalTask = taskHandler.getTask(taskId, stack);
        optionalTask.ifPresent(x -> {
            taskHandler.touchCompleted(x, markCompleted);
        });
        stackHandler.reorderTasks(stack);
        stackRepository.save(stack);
        return getTask(stackId, taskId);
    }

    public Task moveTask(String stackId, UUID taskId, String toUserId) {
        Stack stack = stackRequestContext.getStack();
        Task task = taskHandler.getTask(taskId, stack).orElse(null);
        if (toUserId != null) {
            Stack moveToStack = stackRepository.findByUserId(toUserId);
            if (moveToStack == null) {
                moveToStack = createStack(Stack.builder().userId(toUserId).build());
            }
            Task newTask = taskHandler.cloneTask(task, moveToStack);
            taskHandler.touchMoved(task);
            moveToStack.getTasks().put(
                    String.valueOf(newTask.getId()),
                    newTask);
            stackHandler.reorderTasks(moveToStack);
            stackRepository.save(moveToStack);
        }

        stackHandler.reorderTasks(stack);
        stackRepository.save(stack);
        return getTask(stackId, taskId);

    }

    public Task modifyTask(
            String stackId, UUID taskId,
            @Valid Task task) {
        Stack stack = stackRequestContext.getStack();
        Optional<Task> optionalTask = taskHandler.getTask(taskId, stack);
        optionalTask.ifPresent(x -> {
            taskHandler.updateTaskDetails(x, task);
        });
        stackHandler.reorderTasks(stack);
        stackRepository.save(stack);
        return getTask(stackId, taskId);
    }

    public Task deleteTask(String stackId, UUID taskId) {
        Stack stack = stackRequestContext.getStack();
        taskHandler.touchDeleted(taskId, stack);
        stackHandler.reorderTasks(stack);
        stackRepository.save(stack);
        return taskHandler.getTask(taskId, stack).orElse(null);
    }

}
