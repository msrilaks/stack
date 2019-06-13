package com.stack.taskservice.handler;

import com.stack.taskservice.model.Stack;
import com.stack.taskservice.model.Task;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class StackHandler {
    public void initStack(Stack stack) {
        Long ts = System.currentTimeMillis();
        stack.setCreatedTimeStamp(ts);
        stack.setLastUpdatedTimeStamp(ts);
        if (stack.getTasks() == null || stack.getTasks().isEmpty()) {
            stack.setTasks(Collections.emptyMap());
        }
    }

    public void reorderTasks(Stack stack) {
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
