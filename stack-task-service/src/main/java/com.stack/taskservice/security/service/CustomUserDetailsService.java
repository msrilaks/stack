package com.stack.taskservice.security.service;


import com.stack.taskservice.model.User;
import com.stack.taskservice.repository.UserRepository;
import com.stack.taskservice.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                                  .orElseThrow(() ->
                                                       new UsernameNotFoundException(
                                                               "User not found with " +
                                                               "email : " +
                                                               email)
                                              );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(String emailId) {
        User user = userRepository.findById(emailId).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + emailId)
                                                                );

        return UserPrincipal.create(user);
    }
}