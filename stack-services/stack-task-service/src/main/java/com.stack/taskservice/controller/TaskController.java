package com.stack.taskservice.controller;

import com.stack.library.model.stack.Task;
import com.stack.taskservice.context.StackRequestContext;
import com.stack.taskservice.services.StackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Api(value = "Task", description = "Fetch, Create, Update and Delete Tasks", tags = {"Task"})
public class TaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            TaskController.class.getName());

    @Resource(name = "stackRequestContext")
    StackRequestContext stackRequestContext;

    @Autowired
    StackService stackService;

    @GetMapping(path = "/stack/{stackId}/tasks/{taskId}", consumes = "application/json",
            produces = "application/json")
    @ApiOperation(value = "Get Task", tags = {"Task"})
    public ResponseEntity<Task> getTaskOfStack(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") UUID taskId) {
        return ResponseEntity.ok(stackService.getTask(taskId));
    }

    @GetMapping(path = "/stack/{stackId}/tasks", consumes = "application/json",
            produces = "application/json")
    @ApiOperation(value = "Get Tasks", tags = {"Stack"})
    public ResponseEntity<Map<String, Task>> getTasksOfStack(
            @PathVariable("stackId") String stackId,
            @RequestParam(name = "isDeleted", required = false, defaultValue =
                    "false") Boolean isDeleted,
            @RequestParam(name = "isPushed", required = false, defaultValue =
                    "false") Boolean isPushed,
            @RequestParam(name = "isCompleted", required = false, defaultValue =
                    "false") Boolean isCompleted,
            @RequestParam(name = "isToDo", required = false, defaultValue =
                    "false") Boolean isToDo,
            @RequestParam(name = "tags", required = false) List<String> tags) {
        return ResponseEntity.ok(stackService.getTasks(isDeleted,
                isPushed,
                isCompleted,
                isToDo,
                tags));
    }

    @PostMapping(path = "/stack/{stackId}/tasks", consumes = "application/json",
            produces = "application/json")
    @ApiOperation(value = "Create a Task", tags = {"Task"})
    public ResponseEntity<Task> createTask(
            @PathVariable("stackId") String stackId,
            @Valid @RequestBody Task task) {
        return ResponseEntity.ok(stackService.createTask(task));
    }

    @PutMapping(path = "/stack/{stackId}/tasks/{taskId}",
            consumes = "application/json",
            produces = "application/json")
    @ApiOperation(value = "Modify Task", tags = {"Task"})
    public ResponseEntity<Task> modifyTask(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") UUID taskId,
            @RequestBody @Valid Task task) {
        return ResponseEntity.ok(stackService.modifyTask(taskId, task));
    }

    @DeleteMapping(path = "/stack/{stackId}/tasks/{taskId}",
            consumes = "application/json",
            produces = "application/json")
    @ApiOperation(value = "Delete Task", tags = {"Task"})
    public ResponseEntity<Task> deleteTask(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") UUID taskId) {
        Task task = stackService.deleteTask(taskId);
        return ResponseEntity.ok(task);
    }

    @PatchMapping(path = "/stack/{stackId}/tasks/{taskId}",
            consumes = "application/json",
            produces = "application/json")
    @ApiOperation(value = "Patch Task", tags = {"Task"})
    public ResponseEntity<Task> patchTask(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") UUID taskId,
            @RequestParam(name = "isPushed", required = false, defaultValue =
                    "false") Boolean isPushed,
            @RequestParam(name = "isCompleted", required = false, defaultValue =
                    "false") Boolean isCompleted,
            @RequestParam(name = "isToDo", required = false, defaultValue =
                    "false") Boolean isToDo,
            @RequestParam(name = "nudge", required = false, defaultValue =
                    "false") Boolean nudge,
            @RequestBody @Valid Task task) {
        if (isCompleted) {
            return ResponseEntity.ok(stackService.completeTask(taskId, true));
        } else if (isToDo) {
            return ResponseEntity.ok(stackService.completeTask(taskId, false));
        } else if (isPushed) {
            return ResponseEntity.ok(stackService.pushTask(task));
        } else if (nudge) {
            return ResponseEntity.ok(stackService.nudgeTask(taskId));
        } else {
            return ResponseEntity.ok(task);
        }
    }

}
