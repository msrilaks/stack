package com.stack.library.model.stack;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@RedisHash("StackTaskLocation")
@Getter
@Setter
@ToString
@Builder
public class StackTaskLocation implements Serializable {
    @Id
    private String stackId;
    private String userId;
    private String taskId;
    private String placeId;
    private String formattedAddress;
    private Location location;
    private Long locationTimeStamp;
}