package com.stack.taskservice.configuration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Channels {

  @Output
  MessageChannel STACK_EMAIL_TOPIC();

}