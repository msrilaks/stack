package com.stack.taskservice.controller;

import com.stack.library.model.stack.Task;
import com.stack.library.model.user.User;
import com.stack.taskservice.exception.ResourceNotFoundException;
import com.stack.taskservice.repository.StackRepository;
import com.stack.taskservice.repository.UserRepository;
import com.stack.taskservice.security.CurrentUser;
import com.stack.taskservice.security.UserPrincipal;
import com.stack.taskservice.services.StackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Api(value = "User", description = "Stack User Endpoints", tags = {"User"})
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            UserController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    StackService stackService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Get User - me", tags = {"User"}, hidden = true)
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findByEmail(userPrincipal.getEmail())
                             .orElseThrow(() -> new ResourceNotFoundException(
                                     "User",
                                      "email",
                                      userPrincipal.getEmail()));
    }

    @GetMapping(path = "/stack/{stackId}/tasks/{taskId}/users",
                consumes = "application/json",
                produces = "application/json")
    @ApiOperation(value = "Get User who owns the Task", tags = {"User"})
    public ResponseEntity<Map<String, User>> getTaskUser(@PathVariable("stackId") String stackId,
                                                         @PathVariable("taskId") UUID taskId) {
        Task task = stackService.getTask(taskId);
        Map<String, User> userMap = new HashMap<>();

        if(task.isPushed()) {
            task.getTaskPushLogEntryMap().values().stream()
                .forEach(v->userRepository.findByEmail(v.getPushedUserId()).
                        ifPresent(user ->userMap.put(user.getEmail(),user)));
        } else {
            userRepository.findByEmail(task.getUserId()).ifPresent(user ->userMap.put(user.getEmail(),user));
        }
        return ResponseEntity.ok(userMap);
    }
}
