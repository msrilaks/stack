package com.stack.taskservice.controller;

import com.stack.taskservice.model.Stack;
import com.stack.taskservice.model.Task;
import com.stack.taskservice.services.StackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@Api(value = "Stack", description = "REST API for Stack", tags = {"Stack"})
public class StackController {
    @Autowired
    StackService stackService;

    @PostMapping(path = "/stack", consumes = "application/json",
                 produces = "application/json")
    @ApiOperation(value = "Create a Stack", tags = {"Stack"})
    public ResponseEntity<Stack> createStack(
            @RequestBody @Valid Stack stack) {
        return ResponseEntity.ok(stackService.createStack(stack));
    }

    @DeleteMapping(path = "/stack/{stackId}", consumes = "application/json",
                   produces = "application/json")
    @ApiOperation(value = "Delete a Stack", tags = {"Stack"})
    public ResponseEntity deleteStack(
            @PathVariable("stackId") String stackId) {
        stackService.deleteStack(stackId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/stack/{stackId}", consumes = "application/json",
                produces = "application/json")
    @ApiOperation(value = "Get a Stack", tags = {"Stack"})
    public ResponseEntity<Stack> getStack(
            @PathVariable("stackId") String stackId) {
        Stack stack = stackService.getStack(stackId);
        return ResponseEntity.ok(stack);
    }

    @GetMapping(path = "/stack/{stackId}/tasks", consumes = "application/json",
                produces = "application/json")
    @ApiOperation(value = "Get Tasks", tags = {"Stack"})
    public ResponseEntity<List<Task>> getTasksOfStack(
            @PathVariable("stackId") String stackId) {
        return ResponseEntity.ok(stackService.getTasks(stackId));
    }

    @GetMapping(path = "/stack/{stackId}/task/{taskId}", consumes =
            "application/json",
                produces = "application/json")
    @ApiOperation(value = "Get Tasks", tags = {"Stack"})
    public ResponseEntity<Task> getTaskOfStack(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") String taskId) {
        return ResponseEntity.ok(stackService.getTask(stackId, taskId));
    }
}