package com.stack.library.model.stack;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("StackLocationSearch")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
/*
This is a redis hash for keeping stackId for location search by backend service.
 */
public class StackLocationSearch implements Serializable {
    @Id
    private String stackId;
    private Long lastSearchTimeStamp;
}