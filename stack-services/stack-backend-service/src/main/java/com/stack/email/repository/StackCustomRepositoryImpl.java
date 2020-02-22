package com.stack.email.repository;

import com.stack.library.exception.TaskException;
import com.stack.library.model.error.ErrorCodes;
import com.stack.library.model.stack.Location;
import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.Task;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StackCustomRepositoryImpl implements StackCustomRepository{
    @Override
    public Task findTaskById(UUID taskId, Stack stack) {
        return stack.getTasks().stream()
                         .filter(x -> x.getId().equals(taskId))
                         .findAny().orElse(null);
    }

    @Override
    public List<Task> fetchTasksByLocation(Stack stack, Location location, long taskDistanceMiles) {
        System.out.println("### SRI taskDistanceMiles: "+ taskDistanceMiles);
        System.out.println("### SRI location: "+ location);
        Predicate<Task> predicate =
                (task -> task.getLocation() != null
                && task.getLocation().distanceInMilesTo(location) <= taskDistanceMiles
                && task.getDeletedTimeStamp() == null
                && task.getCompletedTimeStamp() == null
                && task.getPushedTimeStamp() == null);
        return stack.getTasks()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
