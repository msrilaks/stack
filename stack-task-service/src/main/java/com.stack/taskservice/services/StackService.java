package com.stack.taskservice.services;

import com.stack.taskservice.model.Stack;
import com.stack.taskservice.model.Task;
import com.stack.taskservice.repository.StackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class StackService {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            StackService.class.getName());

    @Autowired
    StackRepository stackRepository;

    public Stack createStack(Stack stack) {
        LOGGER.info(stack.toString());
        Long ts = System.currentTimeMillis();
        stack.setCreatedTimeStamp(ts);
        stack.setLastUpdatedTimeStamp(ts);
        if (stack.getTaskList() == null || stack.getTaskList().isEmpty()) {
            stack.setTaskList(Collections.emptyList());
        }
        return stackRepository.save(stack);
    }

    public void deleteStack(String stackId) {
        stackRepository.deleteById(stackId);
    }

    public Stack getStack(String stackId) {
        Stack stack = stackRepository.findById(stackId).get();
        return stack;
    }

    public List<Task> getTasks(String stackId) {
        Stack stack = getStack(stackId);
        return stack.getTaskList();
    }

    public Task getTask(String stackId, String taskId) {
        Stack stack = getStack(stackId);
        List<Task> tasks = stack.getTaskList();
        return tasks.stream()
                    .filter(x -> x.getId().equals(taskId))
                    .findAny()
                    .orElse(null);
    }
}
