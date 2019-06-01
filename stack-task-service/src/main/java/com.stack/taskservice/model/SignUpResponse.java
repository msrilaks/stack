package com.stack.taskservice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUpResponse {
    private boolean success;
    private String  message;
}
