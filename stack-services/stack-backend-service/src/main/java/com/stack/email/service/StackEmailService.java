package com.stack.email.service;

import org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.model.Message;
import com.stack.email.repository.StackRepository;
import com.stack.email.repository.UserRepository;
import com.stack.library.model.email.BackendServiceRequest;
import com.stack.library.model.email.StackEmailTemplate;
import com.stack.library.model.stack.Stack;
import com.stack.library.model.stack.Task;
import com.stack.library.model.user.User;
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
import java.util.Properties;

@Component
public class StackEmailService {

    @Autowired
    private GmailService gmailService;

    @Autowired
    private StackRepository stackRepository;

    @Autowired
    private UserRepository userRepository;


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
            if(backendServiceRequest.getFromUserEmail() != null) {
                fromUser = userRepository.findByEmail(backendServiceRequest.getFromUserEmail()).orElse(null);
            }
            Task task = stackRepository.findTaskById(backendServiceRequest.getTaskId(), stack);
            MimeMessage emailContent = createEmail(stack.getUserId(),
                                       "Stack It Down <stackitdown@gmail.com>",
                                       stackEmailTemplate.getSubject(),
                                       stackEmailTemplate.getMessage(stack,
                                                                     task,
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


}
