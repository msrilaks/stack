package com.stack.taskservice.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.StackEvent;
import com.stack.library.model.stack.Task;
import com.stack.taskservice.context.StackRequestContext;
import com.stack.taskservice.repository.StackRepository;
import com.stack.taskservice.security.google.GoogleCredentialManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;
import java.util.UUID;

@Component
public class GoogleCalendarService {
    private static final Logger      LOGGER       = LoggerFactory.getLogger(
            GoogleCalendarService.class.getName());
    private static final JsonFactory JSON_FACTORY = JacksonFactory
            .getDefaultInstance();

    @Autowired
    private StackRequestContext stackRequestContext;

    @Autowired
    private GoogleCredentialManager googleCredentialManager;

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    StackRepository stackRepository;

    public Task addEvent(UUID taskId, StackEvent stackEvent) {
        Stack stack = stackRequestContext.getStack();
        Task task = stackRepository.findTaskById(taskId, stack);
        try {
            GoogleCredential credential =
                    googleCredentialManager.getCredential(
                            stackRequestContext.getUser().getEmail());
            NetHttpTransport HTTP_TRANSPORT =
                    GoogleNetHttpTransport.newTrustedTransport();
            Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                                                    credential)
                    .setApplicationName(appName)
                    .build();

            Event event = new Event();
            event.setStart(new EventDateTime().setDateTime(stackEvent.getStart()));
            event.setEnd(new EventDateTime().setDateTime(stackEvent.getEnd()));
            event.setSummary(task.getTitle());
            event.setLocation(stackEvent.getLocation());
            event.setDescription(task.getDescription());
            service.events().insert("primary", event).execute();
            task.setStackEvent(stackEvent);
            stackRepository.saveStack(stack);
        } catch (GeneralSecurityException e) {
            LOGGER.error("Security exception adding to Google Calendar", e);
        } catch (Exception e) {
            LOGGER.error("Exception adding to Google Calendar", e);
        }
        return task;
    }
}
