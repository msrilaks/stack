package com.stack.taskservice.repository;

import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.StackTaskLocation;
import com.stack.library.model.stack.Task;

public interface StackTaskLocationCustomRepository {
    StackTaskLocation saveTask(Task task);
}