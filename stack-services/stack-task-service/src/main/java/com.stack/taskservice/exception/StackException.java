package com.stack.taskservice.exception;

import com.stack.taskservice.model.Error;

public class StackException extends ApplicationException {
    public StackException(Error error, Throwable cause) {
        super(error, cause);
    }

    public StackException(Error error) {super(error);}
}
