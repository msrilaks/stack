package com.stack.taskservice.controller;

import com.stack.library.model.ping.PingResponse;
import com.stack.library.model.stack.Location;
import com.stack.library.model.stack.Stack;
import com.stack.taskservice.context.StackRequestContext;
import com.stack.taskservice.services.PingService;
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
import java.util.Map;


@Controller
@Api(value = "Stack", description = "Fetch, Create, Update, Delete and Ping Stack", tags = {"Stack"})
public class StackController {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            StackController.class.getName());

    @Resource(name = "stackRequestContext")
    StackRequestContext stackRequestContext;

    @Autowired
    StackService stackService;

    @Autowired
    PingService pingService;

    @GetMapping(path = "/stack",
            produces = "application/json")
    @ApiOperation(value = "Get a Stack", tags = {"Stack"})
    public ResponseEntity<Stack> getStack() {
        if (stackRequestContext.getUser() == null) {
            return ResponseEntity.badRequest().build();
        }
        Stack stack = stackService.getStackByUserId(
                stackRequestContext.getUser().getEmail());
        if (stack != null) {
            return ResponseEntity.ok(stack);
        } else {
            stack = Stack.builder().userId(stackRequestContext.getUser().getEmail())
                    .build();
            return ResponseEntity.ok(stackService.createStack(stack));
        }
    }

    @PostMapping(path = "/stack", consumes = "application/json",
            produces = "application/json")
    @ApiOperation(value = "Create a Stack", tags = {"Stack"})
    public ResponseEntity<Stack> createStack(@RequestBody @Valid Stack stack) {
        return ResponseEntity.ok(stackService.createStack(stack));
    }

    @DeleteMapping(path = "/stack/{stackId}", produces = "application/json")
    @ApiOperation(value = "Delete a Stack", tags = {"Stack"})
    public ResponseEntity deleteStack(@PathVariable("stackId") String stackId) {
        stackService.deleteStack(stackId);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping(path = "/stack/{stackId}", produces = "application/json")
    @ApiOperation(value = "Get a Stack", tags = {"Stack"})
    public ResponseEntity<Stack> getStack(
            @PathVariable("stackId") String stackId) {
        return ResponseEntity.ok(stackRequestContext.getStack());
    }

    @PostMapping(path = "/stack/{stackId}/ping",
            consumes = "application/json",
            produces = "application/json")
    @ApiOperation(value = "Ping for location specific tasks and updates", tags = {
            "Stack"})
    public ResponseEntity<PingResponse> ping(
            @PathVariable("stackId") String stackId,
            @RequestHeader Map<String, String> headers,
            @RequestBody @Valid Location location) {
        return ResponseEntity.ok(pingService.fetchPingResponse(location));
    }
}