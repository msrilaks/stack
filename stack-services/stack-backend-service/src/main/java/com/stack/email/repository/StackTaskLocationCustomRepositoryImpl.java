package com.stack.email.repository;

import com.stack.library.model.stack.Location;
import com.stack.library.model.stack.StackTaskLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StackTaskLocationCustomRepositoryImpl implements StackTaskLocationCustomRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            StackTaskLocationCustomRepositoryImpl.class.getName());

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public void saveStackTaskLocation(StackTaskLocation stackTaskLocation) {
        redisTemplate.opsForHash().put(stackTaskLocation.getStackId(), stackTaskLocation.getTaskId(),
                stackTaskLocation);
    }

    @Override
    public List<StackTaskLocation> fetchByLocation(String stackId, Location location, long taskDistanceMiles) {
        Map<String, StackTaskLocation> stackTaskLocationMap = redisTemplate.opsForHash().entries(stackId);
        System.out.println("### SRI taskDistanceMiles: " + taskDistanceMiles);
        System.out.println("### SRI location: " + location);
        Predicate<StackTaskLocation> predicate =
                (stackTaskLocation -> stackTaskLocation.getLocation() != null
                        && stackTaskLocation.getLocation().distanceInMilesTo(location) <= taskDistanceMiles);
        return stackTaskLocationMap.values()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

}
