package com.stack.taskservice.repository;

import com.stack.library.model.stack.StackTaskLocation;
import com.stack.library.model.stack.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class StackTaskLocationCustomRepositoryImpl implements StackTaskLocationCustomRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            StackTaskLocationCustomRepositoryImpl.class.getName());

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public StackTaskLocation saveTask(Task task) {
        if(task.getPlaceId() == null) {
            return null;
        }
        StackTaskLocation stackTaskLocation = StackTaskLocation.builder().stackId(task.getStackId())
                .taskId(task.getId().toString()).userId(task.getUserId()).placeId(task.getPlaceId())
                .formattedAddress(task.getFormattedAddress()).locationTimeStamp(System.currentTimeMillis())
                .build();
        return stackTaskLocation;
    }
}
