package com.stack.taskservice.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.stack.taskservice.context.StackRequestContext;
import com.stack.taskservice.security.google.GoogleCredentialManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;

@Component
public class GoogleCalendarService {
    private static final Logger      LOGGER           = LoggerFactory.getLogger(
            GoogleCalendarService.class.getName());
    private static final String      APPLICATION_NAME = "Stack";
    private static final JsonFactory JSON_FACTORY     = JacksonFactory
            .getDefaultInstance();

    @Autowired
    private StackRequestContext stackRequestContext;

    @Autowired
    private GoogleCredentialManager googleCredentialManager;

    public void listCalendar() {

        try {
            GoogleCredential credential =
                    googleCredentialManager.getCredential(
                            stackRequestContext.getUser().getEmail());
            NetHttpTransport HTTP_TRANSPORT =
                    GoogleNetHttpTransport.newTrustedTransport();
            Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                                                    credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            LOGGER.info("## SRI Google calendar: " + service.calendarList());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
