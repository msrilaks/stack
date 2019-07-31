package com.stack.taskservice.exception;

import com.stack.library.model.error.Error;

public class TaskException extends ApplicationException {
    public TaskException(Error error, Throwable cause) {
        super(error, cause);
    }

    public TaskException(Error error) {
        super(error);
    }
}
