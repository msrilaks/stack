package com.stack.taskservice.repository;

import com.stack.library.model.stack.Stack;
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
    public void saveStack(Stack stack) {
        redisTemplate.opsForHash().delete(stack.getId());
        for(Task task:stack.getTasks()) {
            saveTask(task);
        }
    }
    private void saveTask(Task task) {
        // If placeId is removed from task, delete from cache
        if(task.getPlaceId() == null
                || task.getDeletedTimeStamp() != null
                || task.getCompletedTimeStamp() != null
                || task.getPushedTimeStamp() != null) {
            redisTemplate.opsForHash().delete(task.getStackId(), task.getId());
            return;
        }
        StackTaskLocation stackTaskLocation =
                (StackTaskLocation)redisTemplate.opsForHash().get(task.getStackId(), task.getId());
        //If not present in cache or is present but different in cache, add it
        if(stackTaskLocation ==  null ||
           (stackTaskLocation !=  null && !task.getPlaceId().equals(stackTaskLocation.getPlaceId()))) {
            stackTaskLocation = StackTaskLocation.builder().stackId(task.getStackId())
                                     .taskId(task.getId().toString())
                                     .userId(task.getUserId())
                                     .placeId(task.getPlaceId())
                                     .formattedAddress(task.getFormattedAddress())
                                     .location(task.getLocation())
                                     .locationTimeStamp(System.currentTimeMillis())
                                     .build();
            redisTemplate.opsForHash().put(task.getStackId(), task.getId(),
                                           stackTaskLocation);
            //Do not save location in db
            //task.clearLocation();
        } else {
            //no op
            return;
        }
    }
}
