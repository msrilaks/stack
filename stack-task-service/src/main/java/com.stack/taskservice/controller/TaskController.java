package com.stack.taskservice.controller;

import com.stack.taskservice.model.Task;
import com.stack.taskservice.services.StackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Api(value = "Task", description = "REST API for Task", tags = {"Task"})
public class TaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            TaskController.class.getName());
    @Autowired
    StackService stackService;

    @PostMapping(path = "/tasks", consumes = "application/json",
                 produces = "application/json")
    @ApiOperation(value = "Create Tasks", tags = {"Task"})
    public ResponseEntity<Map<String, Task>> createTask(
            @Valid @RequestBody List<Task> tasks) {
        Map<String, Task> retTasks = new HashMap<>();
        tasks.forEach(task -> {
            Task createdTask = stackService.createTask(task);
            retTasks.put(createdTask.getId().toString(), createdTask);
        });
        return ResponseEntity.ok(retTasks);
    }

}