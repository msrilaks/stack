package com.stack.email.controller;

import com.stack.email.service.StackEmailService;
import com.stack.library.model.email.EmailRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@Api(value = "Email", description = "REST API for Email", tags = {"Email"})
public class StackEmailController {
    @Autowired
    StackEmailService stackEmailService;

    @PostMapping(path = "/email", consumes = "application/json",
                 produces = "application/json")
    @ApiOperation(value = "Send an email", tags = {"Email"})
    public ResponseEntity<Void> sendEmail(@Valid @RequestBody EmailRequest emailRequest) {
        stackEmailService.sendEmail(emailRequest);
        return ResponseEntity.ok()
                             .build();
    }
}
