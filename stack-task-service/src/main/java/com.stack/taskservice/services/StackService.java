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
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

    public Task getTask(String stackId, UUID taskId) {
        Stack stack = stackRequestContext.getStack();
        Optional<Task> optionalTask = taskHandler.getTask(taskId, stack);
        return optionalTask.orElse(null);
    }

    public Task createTask(String stackId, Task task) {
        Stack stack = stackRequestContext.getStack();
        taskHandler.initTask(task, stack);
        stack.getTasks().put(String.valueOf(stack.getTasks().size() + 1), task);
        Stack savedStack = stackRepository.save(stack);
        return savedStack.getTasks().get(String.valueOf(savedStack.getTasks().size()));
    }

    public Task modifyTask(
            String stackId, UUID taskId, String moveToStackUserId, boolean markCompleted,
            @Valid Task task) {
        Stack stack = stackRequestContext.getStack();
        Optional<Task> optionalTask = taskHandler.getTask(taskId, stack);
        optionalTask.ifPresent(x -> {
            taskHandler.updateTaskDetails(x, task);
            taskHandler.touchCompleted(x, markCompleted);
        });

        if (moveToStackUserId != null) {
            Stack moveToStack = stackRepository.findByUserId(moveToStackUserId);
            if (moveToStack == null) {
                //TODO
            }
            optionalTask.ifPresent(x -> {
                taskHandler.touchMoved(x);
                Task newTask = taskHandler.cloneTask(x, moveToStack);
                moveToStack.getTasks().put(
                        String.valueOf(moveToStack.getTasks().size() + 1),
                        newTask);
                stackRepository.save(moveToStack);
            });
        }
        stackRepository.save(stack);
        return getTask(stackId, taskId);
    }

    public void deleteTask(String stackId, UUID taskId) {
        Stack stack = stackRequestContext.getStack();
        taskHandler.touchDeleted(taskId, stack);
        stackRepository.save(stack);
    }

}
