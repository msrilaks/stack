package com.stack.taskservice.security.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.stack.library.model.user.AuthProvider;
import com.stack.library.model.user.User;
import com.stack.taskservice.exception.OAuth2AuthenticationProcessingException;
import com.stack.taskservice.repository.UserRepository;
import com.stack.taskservice.security.UserPrincipal;
import com.stack.taskservice.security.google.GoogleCredentialManager;
import com.stack.taskservice.security.user.OAuth2UserInfo;
import com.stack.taskservice.security.user.OAuth2UserInfoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            CustomOAuth2UserService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoogleCredentialManager googleCredentialManager;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws
                                                                    OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the
            // OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(),
                                                             ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(
            OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo
                oAuth2UserInfo = OAuth2UserInfoFactory
                .getOAuth2UserInfo(
                        oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                        oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException(
                    "Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByEmail(
                oAuth2UserInfo.getEmail());
        GoogleCredential credential =
                new GoogleCredential().setAccessToken(
                        oAuth2UserRequest.getAccessToken().getTokenValue());
        googleCredentialManager.saveCredential(oAuth2UserInfo.getEmail(), credential);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getProvider().equals(AuthProvider
                                                   .valueOf(oAuth2UserRequest
                                                                    .getClientRegistration()
                                                                    .getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException(
                        "Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " +
                        user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(
            OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();

        user.setProvider(AuthProvider.valueOf(
                oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

}
