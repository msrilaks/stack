package com.stack.email.repository;

import com.stack.library.model.stack.Location;
import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.Task;

import java.util.List;
import java.util.UUID;

public interface StackCustomRepository {
    Task findTaskById(UUID taskId, Stack stack);
    List<Task> fetchTasksByLocation(Stack stack, Location location, long taskDistanceMiles);
}
