package com.stack.taskservice.security;

import com.stack.taskservice.configuration.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            OAuth2AuthenticationSuccessHandler.class.getName());

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AppProperties appProperties;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws
                                           IOException, ServletException {
        String token = tokenProvider.createToken(authentication);
        LOGGER.info("SRI #### token : " + token);
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080" +
                                                              "/user/login")
                                               .queryParam("token", token)
                                               .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }


}
