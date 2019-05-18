package com.stack.taskservice.exception;

import com.stack.taskservice.model.Error;

public class TaskException extends ApplicationException {
    public TaskException(Error error, Throwable cause) {
        super(error, cause);
    }
}
