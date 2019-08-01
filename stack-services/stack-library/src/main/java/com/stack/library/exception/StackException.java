package com.stack.library.exception;

import com.stack.library.model.error.Error;

public class StackException extends ApplicationException {
    public StackException(Error error, Throwable cause) {
        super(error, cause);
    }

    public StackException(Error error) {super(error);}
}
