package com.stack.taskservice.security.handler;

import com.stack.taskservice.configuration.AppProperties;
import com.stack.taskservice.exception.BadRequestException;
import com.stack.taskservice.security.HttpCookieOAuth2AuthorizationRequestRepository;
import com.stack.taskservice.security.TokenProvider;
import com.stack.taskservice.util.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.stack.taskservice.security.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class OAuth2AuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            OAuth2AuthenticationSuccessHandler.class.getName());

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AppProperties appProperties;
    @Autowired
    private HttpCookieOAuth2AuthorizationRequestRepository
                          httpCookieOAuth2AuthorizationRequestRepository;


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws
                                           IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " +
                         targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

    protected String determineTargetUrl(
            HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request,
                                                             REDIRECT_URI_PARAM_COOKIE_NAME)
                                                  .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri
                                                                        .get())) {
            throw new BadRequestException(
                    "Unauthorized Redirect URI and can't proceed with the " +
                    "authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        LOGGER.info("##SRI targetUrl: "+redirectUri);
        String token = tokenProvider.createToken(authentication);

        return UriComponentsBuilder.fromUriString(targetUrl)
                                   .queryParam("token", token)
                                   .build().toUriString();
    }

    protected void clearAuthenticationAttributes(
            HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(
                request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getAuthorizedRedirectUris()
                            .stream()
                            .anyMatch(authorizedRedirectUri -> {
                                // Only validate host and port. Let the clients use
                                // different paths if they want to
                                URI authorizedURI = URI.create(authorizedRedirectUri);
                                return authorizedURI.getHost().equalsIgnoreCase(
                                        clientRedirectUri.getHost())
                                       && authorizedURI.getPort() ==
                                          clientRedirectUri.getPort();
                            });
    }
}
