package com.stack.taskservice.services;

import com.stack.taskservice.model.Stack;
import com.stack.taskservice.model.Task;
import com.stack.taskservice.repository.StackRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StackService {
    @Autowired
    StackRepository stackRepository;

    public Stack createStack(Stack stack) {
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
        return null;
        //List<Task> getTasks( stackId)
    }
}
