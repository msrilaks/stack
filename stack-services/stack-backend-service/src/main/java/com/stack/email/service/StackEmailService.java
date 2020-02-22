package com.stack.email.service;

import com.stack.email.configuration.StackLocationProperties;
import com.stack.email.repository.StackLocationRepository;
import com.stack.email.repository.StackRecentTasksRepository;
import com.stack.library.model.stack.*;
import org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.model.Message;
import com.stack.email.repository.StackRepository;
import com.stack.email.repository.UserRepository;
import com.stack.library.model.email.BackendServiceRequest;
import com.stack.library.model.email.StackEmailTemplate;
import com.stack.library.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.BodyPart;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static com.stack.library.constants.StackEmailConstants.TASK_PUSHED_TOPIC;

@Component
public class StackEmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            StackEmailService.class.getName());

    @Autowired
    private GmailService gmailService;

    @Autowired
    private StackRepository stackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StackLocationProperties stackLocationProperties;

    @Autowired
    private StackLocationRepository stackLocationRepository;

    @Autowired
    private StackRecentTasksRepository stackRecentTasksRepository;

    public void sendEmail(
            @Valid BackendServiceRequest backendServiceRequest) {

        try {
            Stack stack =
                    stackRepository.findById(backendServiceRequest.getStackId())
                                   .orElse(null);
            if (stack == null) {
                return;
            }
            StackEmailTemplate stackEmailTemplate =
                    StackEmailTemplate.get(backendServiceRequest.getTopic());
            User user = userRepository.findByEmail(stack.getUserId()).orElse(null);
            User fromUser = null;
            List<Task> tasks = new ArrayList<>();

            if(backendServiceRequest.getFromUserEmail() != null) {
                fromUser = userRepository.findByEmail(backendServiceRequest.getFromUserEmail()).orElse(null);
            }
            if(backendServiceRequest.getTaskId() != null) {
                Task task = stackRepository.findTaskById(backendServiceRequest.getTaskId(), stack);
                tasks.add(task);
            }
            if(backendServiceRequest.getTopic().equals(TASK_PUSHED_TOPIC)) {
                setTasksRecent(stack,tasks);
            }
            if(backendServiceRequest.getLocation() != null) {
                tasks = stackRepository.fetchTasksByLocation(stack,backendServiceRequest.getLocation(), stackLocationProperties.getTaskDistanceMiles());
                setTasksNearLocation(stack,tasks,backendServiceRequest.getLocation(),backendServiceRequest.getDeviceId());
                if (tasks == null || tasks.isEmpty()) {
                    return;
                    //No need to email if no tasks found
                }
            }
            MimeMessage emailContent = createEmail(stack.getUserId(),
                                       "Stack It Down <stackitdown@gmail.com>",
                                       stackEmailTemplate.getSubject(),
                                       stackEmailTemplate.getMessage(stack,
                                                                     tasks,
                                                                     user,
                                                                     fromUser,
                                               backendServiceRequest));
            Message message = createMessageWithEmail(emailContent);
            message =
                    gmailService.getGmail().users().messages().send("me", message)
                                .execute();

            System.out.println(message.toPrettyString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MimeMessage createEmail(
            String to,
            String from,
            String subject,
            String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        MimeMultipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(bodyText, "text/html");
        multipart.addBodyPart(messageBodyPart);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                           new InternetAddress(to));
        email.setSubject(subject);
        //email.setContent(bodyText, "text/html");
        email.setContent(multipart);
        return email;
    }


    private Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    private void setTasksRecent(Stack stack, List<Task> tasks) {
        StackRecentTasks stackRecentTasks = stackRecentTasksRepository.findById(stack.getId()).orElse(null);
        if(stackRecentTasks == null) {
            stackRecentTasks = new StackRecentTasks();
            stackRecentTasks.setStackId(stack.getId());
            stackRecentTasks.setUserId(stack.getUserId());
            stackRecentTasks.setTasksRecentIds("");
        }
        StringBuffer taskIds = new StringBuffer();
        if(!stackRecentTasks.getTasksRecentIds().isEmpty()) {
            taskIds.append(stackRecentTasks.getTasksRecentIds()).append(",");
        }
        for(Task task:tasks){
            taskIds.append(task.getId()).append(",");
        }
        stackRecentTasks.setTasksRecentIds(taskIds.toString());
        stackRecentTasks.setLastModifiedDate(new Date());
        LOGGER.info("Adding stackRecentTasks to cache: " + stackRecentTasks);
        stackRecentTasksRepository.save(stackRecentTasks);
    }

    private void setTasksNearLocation(Stack stack, List<Task> tasks, Location location, String deviceId) {
        StackLocation stackLocation = new StackLocation();
        stackLocation.setStackId(stack.getId());
        stackLocation.setDeviceId(deviceId);
        stackLocation.setUserId(stack.getUserId());
        stackLocation.setLat(location.getLat());
        stackLocation.setLng(location.getLng());
        stackLocation.setLastLocationSearchDate(new Date());
        StringBuffer taskIds = new StringBuffer();
        for(Task task:tasks){
            taskIds.append(task.getId()).append(",");
        }
        stackLocation.setTaskIdsNearLoc(taskIds.toString());
        stackLocationRepository.save(stackLocation);
        //LOGGER.debug("stackLocation saved : " + stackLocation);
    }

}
