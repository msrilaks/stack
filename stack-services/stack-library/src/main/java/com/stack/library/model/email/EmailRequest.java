package com.stack.library.model.email;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class EmailRequest {
    private String topic;
    private String stackId;
    private UUID   taskId;
}