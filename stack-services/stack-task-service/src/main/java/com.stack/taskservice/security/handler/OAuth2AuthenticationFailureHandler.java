package com.stack.taskservice.security.handler;

import com.stack.taskservice.security.HttpCookieOAuth2AuthorizationRequestRepository;
import com.stack.taskservice.util.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.stack.taskservice.security.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class OAuth2AuthenticationFailureHandler
        extends SimpleUrlAuthenticationFailureHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            OAuth2AuthenticationFailureHandler.class.getName());
    @Autowired
    HttpCookieOAuth2AuthorizationRequestRepository
            httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String targetUrl = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                                      .map(Cookie::getValue)
                                      .orElse(("/"));

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                                        .queryParam("error",
                                                    exception.getLocalizedMessage())
                                        .build().toUriString();
        LOGGER.error("Oauth Error", exception);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(
                request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
