package com.stack.taskservice.repository;

import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.Task;

import java.util.List;
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

    Map<String, Task> fetchDeletedTasks(Stack stack, List<String> tags);

    Map<String, Task> fetchCompletedTasks(Stack stack, List<String> tags);

    Map<String, Task> fetchPushedTasks(Stack stack, List<String> tags);

    Map<String, Task> fetchToDoTasks(Stack stack, List<String> tags);

    Map<String, Task> fetchAllTasks(Stack stack,List<String> tags);
}