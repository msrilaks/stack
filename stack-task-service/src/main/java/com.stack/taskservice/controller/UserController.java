package com.stack.taskservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            UserController.class.getName());

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @RequestMapping(value = "/user")
    public Principal user(Principal principal) {
        LOGGER.info("## SRI prince: " + principal);
        return principal;
    }

    @GetMapping(value = "/user/login")
    public ResponseEntity<Void> userLogin(@RequestParam(required = true) String token) {
        LOGGER.info("## SRI prince: " + token);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authentication", "Bearer " + token);
        return new ResponseEntity<Void>(responseHeaders, HttpStatus.OK);
    }
}
