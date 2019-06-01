package com.stack.taskservice.controller;

import com.stack.taskservice.exception.BadRequestException;
import com.stack.taskservice.model.*;
import com.stack.taskservice.repository.UserRepository;
import com.stack.taskservice.security.TokenProvider;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
        //return new ResponseEntity<Void>(responseHeaders, HttpStatus.OK);
        //return ResponseEntity.ok(responseHeaders, new LoginResponse());
        return ResponseEntity.ok(LoginResponse.builder().accessToken(token).build());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location)
                             .body(new SignUpResponse(true, "User registered " +
                                                            "successfully@"));
    }

}
