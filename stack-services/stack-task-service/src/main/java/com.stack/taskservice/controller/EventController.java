package com.stack.taskservice.controller;

import com.stack.library.model.stack.StackEvent;
import com.stack.library.model.stack.Task;
import com.stack.taskservice.context.StackRequestContext;
import com.stack.taskservice.services.GoogleCalendarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.UUID;

@Controller
@Api(value = "Event", description = "Create and Manage Google Event for Tasks", tags = {"Event"})
public class EventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            EventController.class.getName());

    @Resource(name = "stackRequestContext")
    StackRequestContext stackRequestContext;

    @Autowired
    GoogleCalendarService googleCalendarService;

    @PostMapping(path = "/stack/{stackId}/tasks/{taskId}/event",
            consumes = "application/json",
            produces = "application/json")
    @ApiOperation(value = "Create Google Event for a Task", tags = {"Event"})
    public ResponseEntity<Task> createGoogleEvent(
            @PathVariable("stackId") String stackId,
            @PathVariable("taskId") UUID taskId,
            @RequestBody @Valid StackEvent stackEvent) {
        return ResponseEntity.ok(googleCalendarService.addEvent(taskId, stackEvent));
    }
}
