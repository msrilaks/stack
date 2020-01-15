package com.stack.email.repository;

import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.Task;

import java.util.UUID;

public interface StackCustomRepository {
    Task findTaskById(UUID taskId, Stack stack);
}
