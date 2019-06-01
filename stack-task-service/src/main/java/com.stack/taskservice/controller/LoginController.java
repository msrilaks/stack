package com.stack.taskservice.controller;

import com.stack.taskservice.model.LoginRequest;
import com.stack.taskservice.repository.UserRepository;
import com.stack.taskservice.security.TokenProvider;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            LoginController.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @GetMapping(path = "/login",
                produces = "application/json")
    @ApiOperation(value = "User login", tags = {"Login"})
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        LOGGER.info("### SRI LOGIN Request");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
                                                                          );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authentication", "Bearer " + token);
        return new ResponseEntity<Void>(responseHeaders, HttpStatus.OK);
        //return ResponseEntity.ok(responseHeaders, new LoginResponse());
    }
}
