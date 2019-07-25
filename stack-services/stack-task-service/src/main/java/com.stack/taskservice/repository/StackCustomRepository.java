package com.stack.taskservice.repository;

import com.stack.taskservice.model.Stack;
import com.stack.taskservice.model.Task;

import java.util.Map;
import java.util.UUID;

public interface StackCustomRepository {
    Task saveTaskToStack(Task task, Stack stack);

    Task saveTaskAsPushed(UUID taskId, Stack stack);

    Task saveTaskAsDeleted(UUID taskId, Stack stack);

    Task saveTaskAsModified(Task copyFrom, UUID taskId, Stack stack);

    Task saveTaskAsCompleted(boolean markCompleted, UUID taskId, Stack stack);

    Stack saveStack(Stack stack);

    Task findTaskById(UUID taskId, Stack stack);

    Map<String, Task> fetchDeletedTasks(Stack stack);

    Map<String, Task> fetchCompletedTasks(Stack stack);

    Map<String, Task> fetchPushedTasks(Stack stack);

    Map<String, Task> fetchToDoTasks(Stack stack);

    Map<String, Task> fetchAllTasks(Stack stack);
}