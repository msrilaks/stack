package com.stack.taskservice.services;

import com.stack.taskservice.handler.StackHandler;
import com.stack.taskservice.handler.TaskHandler;
import com.stack.taskservice.model.Stack;
import com.stack.taskservice.model.Task;
import com.stack.taskservice.repository.StackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;
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

    public List<Task> getTasks(String stackId) {
        Stack stack = getStack(stackId);
        return stack.getTaskList();
    }

    public Task getTask(String stackId, UUID taskId) {
        Stack stack = getStack(stackId);
        Optional<Task> optionalTask = taskHandler.getTask(taskId, stack);
        return optionalTask.orElse(null);
    }

    public Task createTask(String stackId, Task task) {
        Stack stack = getStack(stackId);
        taskHandler.initTask(task, stack);
        stack.getTaskList().add(task);
        Stack savedStack = stackRepository.save(stack);
        return savedStack.getTaskList().get(savedStack.getTaskList().size() - 1);
    }

    public Task modifyTask(
            String stackId, UUID taskId, String moveToStackUserId, boolean markCompleted,
            @Valid Task task) {
        Stack stack = getStack(stackId);
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
                moveToStack.getTaskList().add(newTask);
                stackRepository.save(moveToStack);
            });
        }
        stackRepository.save(stack);
        return getTask(stackId, taskId);
    }

    public void deleteTask(String stackId, UUID taskId) {
        Stack stack = getStack(stackId);
        taskHandler.touchDeleted(taskId, stack);
        stackRepository.save(stack);
    }

}
