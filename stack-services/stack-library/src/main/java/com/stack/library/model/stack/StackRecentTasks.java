package com.stack.library.model.stack;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@RedisHash("StackRecentTasks")
@Getter
@Setter
@ToString
public class StackRecentTasks implements Serializable {
    @Id
    private String stackId;
    private String userId;
    private String tasksRecentIds;
    private Date lastModifiedDate;
}