package com.stack.email.service;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.model.Message;
import com.stack.email.repository.StackRepository;
import com.stack.library.model.email.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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

    public void sendEmail(
            @Valid EmailRequest emailRequest) {

        try {
            //            Stack stack =
            //                    stackRepository.findById(emailRequest.getStackId())
            //                    .orElse(null);
            //            if(stack == null) {
            //                return;
            //            }

            MimeMessage emailContent = createEmail("srilakshmi.mudaliar@gmail.com",//stack
                                                   // .getUserId(),
                                                   "stackitdown",
                                                   "Welcome to StackItDown!",
                                                   "Now stay on top of your tasks..");
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

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                           new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
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
