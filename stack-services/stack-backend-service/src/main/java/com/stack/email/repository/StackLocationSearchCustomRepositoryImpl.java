package com.stack.email.repository;

import com.stack.library.model.stack.StackLocationSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

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
        redisTemplate.opsForList().leftPush("StackLocationSearch", stackLocationSearch);
    }

    @Override
    public StackLocationSearch getStackLocationSearch() {
        return (StackLocationSearch)redisTemplate.opsForList().rightPop("StackLocationSearch");
    }


}
