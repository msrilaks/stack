package com.stack.taskservice.handler;

import com.stack.taskservice.model.Stack;
import org.springframework.stereotype.Component;

import java.util.Collections;

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
}
