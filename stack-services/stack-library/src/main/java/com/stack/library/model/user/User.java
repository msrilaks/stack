package com.stack.library.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stack.library.model.user.AuthProvider;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class User {
    @Id
    @Email
    private String       email;
    private String       name;
    private String       imageUrl;
    private Boolean      emailVerified = false;
    @JsonIgnore
    private String       password;
    private AuthProvider provider;
    private String       providerId;
}
