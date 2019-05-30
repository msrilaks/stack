package com.stack.taskservice.controller;

import com.stack.taskservice.context.StackRequestContext;
import com.stack.taskservice.model.Stack;
import com.stack.taskservice.model.Task;
import com.stack.taskservice.services.StackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@Controller
@Api(value = "Stack", description = "REST API for Stack", tags = {"Stack"})
public class StackController {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            StackController.class.getName());

    @Resource(name = "stackRequestContext")
    StackRequestContext stackRequestContext;

    @Autowired
    StackService stackService;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping(path = "/stack",
                produces = "application/json")
    @ApiOperation(value = "Get a Stack", tags = {"Stack"})
    public ResponseEntity<Stack> getStacks(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        String userId = (String) authentication.getPrincipal().getAttributes().get(
                "email");
        return ResponseEntity.ok(stackService.getStackByUserId(userId));
    }

    @PostMapping(path = "/stack", consumes = "application/json",
                 produces = "application/json")
    @ApiOperation(value = "Create a Stack", tags = {"Stack"})
    public ResponseEntity<Stack> createStack(
            @RequestBody @Valid Stack stack, OAuth2AuthenticationToken authentication) {
        return ResponseEntity.ok(stackService.createStack(stack));
    }

    @DeleteMapping(path = "/stack/{stackId}", consumes = "application/json",
                   produces = "application/json")
    @ApiOperation(value = "Delete a Stack", tags = {"Stack"})
    public ResponseEntity deleteStack(
            @PathVariable("stackId") String stackId) {
        stackService.deleteStack(stackId);
        return ResponseEntity.ok()
                             .build();
    }

    @GetMapping(path = "/stack/{stackId}", consumes = "application/json",
                produces = "application/json")
    @ApiOperation(value = "Get a Stack", tags = {"Stack"})
    public ResponseEntity<Stack> getStack(
            @PathVariable("stackId") String stackId) {
        return ResponseEntity.ok(stackRequestContext.getStack());
    }

    @GetMapping(path = "/stack/{stackId}/tasks", consumes = "application/json",
                produces = "application/json")
    @ApiOperation(value = "Get Tasks", tags = {"Stack"})
    public ResponseEntity<List<Task>> getTasksOfStack(
            @PathVariable("stackId") String stackId) {
        return ResponseEntity.ok(stackService.getTasks(stackId));
    }

    @GetMapping(path = "/stack/{stackId}/tasks/{taskId}", consumes = "application/json",
                produces = "application/json")
    @ApiOperation(value = "Get Task", tags = {"Task"})
    public ResponseEntity<Task> getTaskOfStack(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") UUID taskId) {
        return ResponseEntity.ok(stackService.getTask(stackId, taskId));
    }

    @PostMapping(path = "/stack/{stackId}/tasks", consumes = "application/json",
                 produces = "application/json")
    @ApiOperation(value = "Create a Task", tags = {"Task"})
    public ResponseEntity<Task> createTask(
            @PathVariable("stackId") String stackId,
            @Valid @RequestBody Task task) {
        return ResponseEntity.ok(stackService.createTask(stackId, task));
    }

    @PutMapping(path = "/stack/{stackId}/tasks/{taskId}",
                consumes = "application/json",
                produces = "application/json")
    @ApiOperation(value = "Modify Task", tags = {"Task"})
    public ResponseEntity<Task> modifyTask(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") UUID taskId,
            @RequestParam(name = "moveToStackUser", required = false)
                    String moveToStackUserId,
            @RequestParam(name = "isCompleted", required = false,
                          defaultValue = "false")
                    boolean isCompleted,
            @RequestBody @Valid Task task) {
        return ResponseEntity.ok(stackService.modifyTask(stackId, taskId,
                                                         moveToStackUserId,
                                                         isCompleted, task));
    }

    @DeleteMapping(path = "/stack/{stackId}/tasks/{taskId}",
                   consumes = "application/json",
                   produces = "application/json")
    @ApiOperation(value = "Delete Task", tags = {"Task"})
    public ResponseEntity deleteTask(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") UUID taskId) {
        stackService.deleteTask(stackId, taskId);
        return new ResponseEntity(HttpStatus.OK);
    }

}