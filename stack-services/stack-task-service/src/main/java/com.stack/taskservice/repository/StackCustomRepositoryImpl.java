package com.stack.taskservice.repository;

import com.stack.library.exception.TaskException;
import com.stack.library.model.error.ErrorCodes;
import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.*;

public class StackCustomRepositoryImpl implements StackCustomRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            StackCustomRepositoryImpl.class.getName());

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Task saveTaskToStack(Task task, Stack stack) {
        if (task.getId() == null) {
            task.setId(UUID.randomUUID());
        }
        Task taskFromStack = stack.getTasks().values().stream()
                                  .filter(x -> x.getId().equals(task.getId()))
                                  .findAny().orElse(null);
        task.setStackId(stack.getId());
        task.setUserId(stack.getUserId());
        if (taskFromStack == null) {
            task.setCreatedDate(new Date());
            stack.getTasks().put((stack.getTasks().size() + 1) + "", task);
        }

        return updateTaskToStack(task, stack);
    }

    private Task updateTaskToStack(Task task, Stack stack) {
        task.setLastModifiedDate(new Date());
        saveStack(stack);
        return task;
    }

    @Override
    public Task saveTaskAsPushed(UUID taskId, Stack stack, String pushedToUserId) {
        Task task = findTaskById(taskId, stack);
        task.addTaskPushLogEntry(pushedToUserId);
        return updateTaskToStack(task, stack);
    }

    @Override
    public Task saveTaskAsDeleted(UUID taskId, Stack stack) {
        Task task = findTaskById(taskId, stack);
        task.setDeletedTimeStamp(System.currentTimeMillis());
        return updateTaskToStack(task, stack);
    }

    @Override
    public Task saveTaskAsModified(
            Task copyFrom, UUID taskId, Stack stack) {
        Task task = findTaskById(taskId, stack);
        task.setDescription(copyFrom.getDescription());
        task.setTags(copyFrom.getTags());
        task.setLocation(copyFrom.getLocation());
        return updateTaskToStack(task, stack);
    }

    @Override
    public Task saveTaskAsCompleted(boolean markCompleted, UUID taskId, Stack stack) {
        Task completedTask = findTaskById(taskId, stack);
        if (markCompleted) {
            completedTask.setCompletedTimeStamp(System.currentTimeMillis());
        } else {
            completedTask.setCompletedTimeStamp(null);
        }
        return updateTaskToStack(completedTask, stack);
    }

    @Override
    public Stack saveStack(Stack stack) {
        reorderTasks(stack);
        mongoTemplate.save(stack);
        return stack;
    }

    @Override
    public Task findTaskById(UUID taskId, Stack stack) {
        Task task = stack.getTasks().values().stream()
                         .filter(x -> x.getId().equals(taskId))
                         .findAny().orElse(null);
        if (task == null) {
            throw new TaskException(ErrorCodes.TASK_ID_INVALID.constructError().appendMessage(". Invalid taskID: "+taskId)
            .appendMessage(", Stack: " + stack.getId()));
        }
        return task;
    }

    @Override
    public Map<String, Task> fetchDeletedTasks(Stack stack, List<String> tags) {
        Predicate<Task> predicate =
                (task -> task.getDeletedTimeStamp() != null);
        return fetchTasks(stack, predicate.and(filterByTags(tags)));
    }

    @Override
    public Map<String, Task> fetchCompletedTasks(Stack stack, List<String> tags) {
        Predicate<Task> predicate = (task -> task.getCompletedTimeStamp() != null &&
                                             task.getDeletedTimeStamp() == null);
        return fetchTasks(stack, predicate.and(filterByTags(tags)));
    }

    @Override
    public Map<String, Task> fetchPushedTasks(Stack stack, List<String> tags) {
        Predicate<Task> predicate = (task -> task.getPushedTimeStamp() != null &&
                                             task.getDeletedTimeStamp() == null);
        return fetchTasks(stack, predicate.and(filterByTags(tags)));
    }

    @Override
    public Map<String, Task> fetchToDoTasks(Stack stack, List<String> tags) {
        Predicate<Task> predicate = (task -> task.getDeletedTimeStamp() == null &&
                                             task.getCompletedTimeStamp() == null &&
                                             task.getPushedTimeStamp() == null);
        return fetchTasks(stack, predicate.and(filterByTags(tags)));
    }

    @Override
    public Map<String, Task> fetchAllTasks(Stack stack, List<String> tags) {
        Predicate<Task> predicate = (task -> true);
        return fetchTasks(stack, predicate);
    }

    private Predicate<Task> filterByTags(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return (task -> true);
        }
        return (task -> task.containsTags(tags));
    }

    private Map<String, Task> fetchTasks(Stack stack, Predicate<Task> predicate) {
        AtomicInteger i = new AtomicInteger(0);
        return stack.getTasks().values()
                    .stream()
                    .filter(predicate)
                    .collect(Collectors.toMap(
                            n -> i.incrementAndGet() + "",
                            t -> t,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private void reorderTasks(Stack stack) {
        if (stack.getTasks() == null || stack.getTasks().isEmpty()) {
            stack.setTasks(new LinkedHashMap<>());
            return;
        }
        AtomicInteger i = new AtomicInteger(0);
        Map<String, Task> tasks = stack.getTasks().entrySet()
                                       .stream()
                                       .sorted(Map.Entry.comparingByValue())
                                       .collect(Collectors.toMap(
                                               n -> i.incrementAndGet() + "",
                                               Map.Entry::getValue,
                                               (oldValue, newValue) -> oldValue,
                                               LinkedHashMap::new));

        stack.setTasks(tasks);
    }
}
