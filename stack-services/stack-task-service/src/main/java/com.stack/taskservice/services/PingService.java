package com.stack.taskservice.services;

import com.stack.library.constants.StackEmailConstants;
import com.stack.library.model.ping.PingResponse;
import com.stack.library.model.stack.Location;
import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.StackLocation;
import com.stack.library.model.stack.Task;
import com.stack.taskservice.configuration.StackPingLocationProperties;
import com.stack.taskservice.context.StackRequestContext;
import com.stack.taskservice.handler.PubSubEmailHandler;
import com.stack.taskservice.repository.StackLocationRepository;
import com.stack.taskservice.repository.StackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            PingService.class.getName());
    @Autowired
    private StackLocationRepository stackLocationRepository;

    @Resource(name = "stackRequestContext")
    StackRequestContext stackRequestContext;

    @Autowired
    PubSubEmailHandler emailHandler;

    @Autowired
    StackRepository        stackRepository;

    @Autowired
    StackPingLocationProperties stackPingLocationProperties;

    public PingResponse fetchPingResponse(Location location) {
        Stack stack = stackRequestContext.getStack();
        PingResponse pingResponse = PingResponse.builder().build();
        pingResponse.setTasksNearLocation(getTasksNearLocationMap(stack, location));
        return pingResponse;
    }

    private Map<String, Task> getTasksNearLocationMap(Stack stack, Location location) {
        StackLocation stackLocation =
                stackLocationRepository.findById(stack.getId()).orElse(null);
        Map<String, Task> taskNearMeMap = new HashMap<>();
        LOGGER.info("## stackLocation fetched from repo : " + stackLocation);
        LOGGER.info("## stackPingLocationProperties : " + stackPingLocationProperties);
        if(stackLocation == null) {
            //Post a pub-sub request to search and populate the cache.
            emailHandler.sendEmailNotification(stack,
                       StackEmailConstants.TASK_NEAR_LOCATION_TOPIC, location);
            return taskNearMeMap;
        }
        //If new location has changed from cached search
        double distanceInMiles = location.distanceInMilesTo(stackLocation.getLat(), stackLocation.getLng());
        LOGGER.info("## distanceInMiles : " + distanceInMiles);
        if(distanceInMiles > stackPingLocationProperties.getSearchDistanceMiles()){
            //Post a pub-sub request to search and repopulate the cache.
            emailHandler.sendEmailNotification(stack,
                       StackEmailConstants.TASK_NEAR_LOCATION_TOPIC, location);
            return taskNearMeMap;
        }

        // If less than 24 hours have passed since last location based task share
        if(stackLocation.getLastLocationSharedDate() !=null && stackLocation.getLastLocationSearchDate()!=null){
            long hoursBetween =
                    ChronoUnit.MINUTES.between(Instant.ofEpochMilli(System.currentTimeMillis()),
                            stackLocation.getLastLocationSearchDate().toInstant());
            LOGGER.info("## hoursBetween : " + hoursBetween);
            if(hoursBetween < stackPingLocationProperties.getSearchIntervalElapsedMinutes()) {
                return taskNearMeMap;
            }
        }

        if(stackLocation.getTaskIdsNearLoc()==null
           || stackLocation.getTaskIdsNearLoc().isEmpty()) {
            return taskNearMeMap;
        }
        String[] taskIds = stackLocation.getTaskIdsNearLoc().split(",");
        for (String taskId : taskIds) {
            try {
                Task t = stackRepository.findTaskById(UUID.fromString(taskId), stack);
                taskNearMeMap.put(t.getId().toString(), t);
            } catch (Exception e) {
                LOGGER.error("Could not populate ping response", e);
            }
        }
        stackLocation.setLastLocationSharedDate(new Date());
        stackLocationRepository.save(stackLocation);
        return taskNearMeMap;
    }
}
