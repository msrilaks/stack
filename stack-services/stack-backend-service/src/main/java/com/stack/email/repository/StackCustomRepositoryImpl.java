package com.stack.email.repository;

import com.stack.library.exception.TaskException;
import com.stack.library.model.error.ErrorCodes;
import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.Task;

import java.util.UUID;

public class StackCustomRepositoryImpl implements StackCustomRepository{
    @Override
    public Task findTaskById(UUID taskId, Stack stack) {
        return stack.getTasks().stream()
                         .filter(x -> x.getId().equals(taskId))
                         .findAny().orElse(null);
    }
}
