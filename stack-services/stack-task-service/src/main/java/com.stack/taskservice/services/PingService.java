package com.stack.taskservice.services;

import com.stack.library.model.ping.PingResponse;
import com.stack.library.model.stack.StackLocation;
import com.stack.library.model.stack.Task;
import com.stack.taskservice.context.StackRequestContext;
import com.stack.taskservice.repository.StackLocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class PingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            PingService.class.getName());
    @Autowired
    private StackLocationRepository stackLocationRepository;

    @Resource(name = "stackRequestContext")
    StackRequestContext stackRequestContext;

    public void setTasksNearMe() {
        StackLocation stackLocation = new StackLocation();
        stackLocation.setStackId("d1");
        stackLocation.setDeviceId("d1");
        stackLocation.setLat(999999);
        stackLocation.setLng(888888);
        stackLocation.setTaskIdsNearLoc("iamnearyouTASK");
        stackLocationRepository.save(stackLocation);
        LOGGER.debug("stackLocation saved : " + stackLocation);
    }

    public PingResponse fetchPingResponse() {
        StackLocation stackLocation = stackLocationRepository.findById("d1").orElse(null);
        PingResponse pingResponse = PingResponse.builder().build();
        LOGGER.debug("stackLocation fetched from repo : " + stackLocation);
        if(stackLocation != null) {
            Map<String, Task> taskMap = new HashMap<>();
            Task t = Task.builder().description(stackLocation.getTaskIdsNearLoc()).build();
            taskMap.put(stackLocation.getDeviceId(),t);
            pingResponse.setTasksNearMe(taskMap);
        }
        return pingResponse;
    }
}
