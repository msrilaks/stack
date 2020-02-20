package com.stack.library.model.email;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class BackendServiceRequest {
    private String topic;
    private String stackId;
    private UUID   taskId;
    private String fromUserEmail;
}