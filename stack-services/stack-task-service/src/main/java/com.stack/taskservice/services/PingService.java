package com.stack.taskservice.services;

import com.stack.library.constants.StackEmailConstants;
import com.stack.library.model.ping.PingResponse;
import com.stack.library.model.stack.*;
import com.stack.taskservice.configuration.StackPingLocationProperties;
import com.stack.taskservice.context.StackRequestContext;
import com.stack.taskservice.handler.PubSubEmailHandler;
import com.stack.taskservice.repository.StackTasksNearLocationRepository;
import com.stack.taskservice.repository.StackRecentTasksRepository;
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
    private StackTasksNearLocationRepository stackTasksNearLocationRepository;

    @Autowired
    private StackRecentTasksRepository stackRecentTasksRepository;

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
        pingResponse.setTasksRecent(getRecentTasks(stack));
        return pingResponse;
    }

    private Map<String, Task> getRecentTasks(Stack stack) {
        Map<String, Task> tasksRecent = new HashMap<>();
        StackRecentTasks stackRecentTasks =
                stackRecentTasksRepository.findById(stack.getId()).orElse(null);
        LOGGER.info("## stackRecentTasks fetched from repo : " + stackRecentTasks);
        if(stackRecentTasks == null) {
            return tasksRecent;
        }
        if(stackRecentTasks.getTasksRecentIds()==null
                || stackRecentTasks.getTasksRecentIds().isEmpty()) {
            return tasksRecent;
        }
        String[] taskIds = stackRecentTasks.getTasksRecentIds().split(",");
        for (String taskId : taskIds) {
            try {
                Task t = stackRepository.findTaskById(UUID.fromString(taskId), stack);
                tasksRecent.put(t.getId().toString(), t);
            } catch (Exception e) {
                LOGGER.error("Could not populate ping response", e);
            }
        }
        stackRecentTasksRepository.delete(stackRecentTasks);
        return tasksRecent;
    }

    private Map<String, Task> getTasksNearLocationMap(Stack stack, Location location) {
        StackTasksNearLocation stackTasksNearLocation =
                stackTasksNearLocationRepository.findById(stack.getId()).orElse(null);
        Map<String, Task> taskNearMeMap = new HashMap<>();
        LOGGER.info("## stackLocation fetched from repo : " + stackTasksNearLocation);
        LOGGER.info("## stackPingLocationProperties : " + stackPingLocationProperties);
        if(stackTasksNearLocation == null) {
            //Post a pub-sub request to search and populate the cache.
            emailHandler.sendEmailNotification(stack,
                       StackEmailConstants.TASK_NEAR_LOCATION_TOPIC, location);
            return taskNearMeMap;
        }
        //If new location has changed from cached search
        double distanceInMiles = location.distanceInMilesTo(stackTasksNearLocation.getLat(), stackTasksNearLocation.getLng());
        LOGGER.info("## distanceInMiles : " + distanceInMiles);
        if(distanceInMiles > stackPingLocationProperties.getSearchDistanceMiles()){
            //Post a pub-sub request to search and repopulate the cache.
            emailHandler.sendEmailNotification(stack,
                       StackEmailConstants.TASK_NEAR_LOCATION_TOPIC, location);
            return taskNearMeMap;
        }

        // If less than 24 hours have passed since last location based task share
        if(stackTasksNearLocation.getLastLocationSharedDate() !=null && stackTasksNearLocation.getLastLocationSearchDate()!=null){
            long hoursBetween =
                    ChronoUnit.MINUTES.between(Instant.ofEpochMilli(System.currentTimeMillis()),
                            stackTasksNearLocation.getLastLocationSearchDate().toInstant());
            LOGGER.info("## hoursBetween : " + hoursBetween);
            if(hoursBetween < stackPingLocationProperties.getSearchIntervalElapsedMinutes()) {
                return taskNearMeMap;
            }
        }

        if(stackTasksNearLocation.getTaskIdsNearLoc()==null
           || stackTasksNearLocation.getTaskIdsNearLoc().isEmpty()) {
            return taskNearMeMap;
        }
        String[] taskIds = stackTasksNearLocation.getTaskIdsNearLoc().split(",");
        for (String taskId : taskIds) {
            try {
                Task t = stackRepository.findTaskById(UUID.fromString(taskId), stack);
                taskNearMeMap.put(t.getId().toString(), t);
            } catch (Exception e) {
                LOGGER.error("Could not populate ping response", e);
            }
        }
        stackTasksNearLocation.setLastLocationSharedDate(new Date());
        stackTasksNearLocationRepository.save(stackTasksNearLocation);
        return taskNearMeMap;
    }
}
