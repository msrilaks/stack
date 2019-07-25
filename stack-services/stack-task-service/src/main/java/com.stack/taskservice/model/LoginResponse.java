package com.stack.taskservice.model;

import lombok.*;

@Builder(toBuilder = true)
@ToString
@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private final String tokenType = "Bearer";
    private       String accessToken;
}
