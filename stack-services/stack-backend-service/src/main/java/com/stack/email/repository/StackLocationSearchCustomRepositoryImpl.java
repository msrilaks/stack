package com.stack.email.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stack.library.model.stack.StackLocationSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class StackLocationSearchCustomRepositoryImpl implements StackLocationSearchCustomRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            StackLocationSearchCustomRepositoryImpl.class.getName());
    private static String STACK_LOCATION_SEARCH = "StackLocationSearch";
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
        } catch (Exception e) {
            LOGGER.error("## Error : ", e);
        }
    }

    @Override
    public StackLocationSearch getStackLocationSearch() {
        String jsonStackLocationSearch = (String) redisTemplate.opsForList().rightPop(STACK_LOCATION_SEARCH);
        ObjectMapper objectMapper = new ObjectMapper();
        StackLocationSearch stackLocationSearch = null;
        try {
            stackLocationSearch = objectMapper.readValue(jsonStackLocationSearch, StackLocationSearch.class);
        } catch (Exception e) {
            LOGGER.error("Error reading json to object", e);
        }
        return (stackLocationSearch);
    }

    @Override
    public long size() {
        return redisTemplate.opsForList().size(STACK_LOCATION_SEARCH);
    }


}
