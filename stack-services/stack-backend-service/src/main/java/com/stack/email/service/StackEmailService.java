package com.stack.email.service;

import com.stack.email.model.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class StackEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(
            @Valid EmailRequest emailRequest) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("srilakshmi.mudaliar@gmail.com", "srilakshmi29@hotmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);

    }
}
