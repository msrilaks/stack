package com.stack.email.service;

import com.stack.email.configuration.StackLocationProperties;
import com.stack.email.repository.StackLocationSearchRepository;
import com.stack.library.model.stack.StackLocationSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class StackLocationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            StackLocationService.class.getName());

    @Autowired
    StackLocationSearchRepository stackLocationSearchRepository;

    @Autowired
    private StackLocationProperties stackLocationProperties;

    @Async
    @Scheduled(fixedDelay = 300000)
    public void scheduledTaskLocationRefresh() {
        LOGGER.info("### Running Location Refresh ");
        long timeOut =  System.currentTimeMillis()
                + stackLocationProperties.getLocationRefreshTimeMinutes() * 60 * 1000;
        long size = stackLocationSearchRepository.size();
        StackLocationSearch stackLocationSearch = null;
        for(int i=0; i<size; i++) {
            stackLocationSearch = stackLocationSearchRepository.getStackLocationSearch();
            if(stackLocationSearch.getLastSearchTimeStamp() > timeOut) {
                break;
            }
        }
        if(stackLocationSearch == null) {
            LOGGER.info("### Found no Stack needing Location Refresh: " );
            return;
        }
        //Run location refresh on this stack
        LOGGER.info("### Picked for Location Refresh: " + stackLocationSearch);

    }
}
