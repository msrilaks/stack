package com.stack.taskservice.controller;

import com.stack.taskservice.exception.ResourceNotFoundException;
import com.stack.library.model.user.User;
import com.stack.taskservice.repository.UserRepository;
import com.stack.taskservice.security.CurrentUser;
import com.stack.taskservice.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            UserController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findByEmail(userPrincipal.getEmail())
                             .orElseThrow(() -> new ResourceNotFoundException("User",
                                                                              "email",
                                                                              userPrincipal
                                                                                      .getEmail()));
    }

}
