package com.stack.taskservice.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.StackLocationSearch;
import com.stack.library.model.stack.StackTaskLocation;
import com.stack.library.model.stack.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

public class StackLocationSearchCustomRepositoryImpl implements StackLocationSearchCustomRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            StackLocationSearchCustomRepositoryImpl.class.getName());

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public void saveStackLocationSearch(String stackId) {
        StackLocationSearch stackLocationSearch = StackLocationSearch.builder()
                .stackId(stackId)
                .lastSearchTimeStamp(System.currentTimeMillis()).build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonStackLocationSearch = objectMapper.writeValueAsString(stackLocationSearch);
            redisTemplate.opsForList().leftPush("StackLocationSearch", jsonStackLocationSearch);
        } catch (JsonProcessingException e) {
            LOGGER.error("## Error : ",e);
        }

    }


}
