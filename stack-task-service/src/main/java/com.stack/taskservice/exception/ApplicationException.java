package com.stack.taskservice.exception;

import com.stack.taskservice.model.Error;

public class ApplicationException extends RuntimeException {
    private Error error;

    public ApplicationException(Error error, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
    }

    public ApplicationException(Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public Error getApplicationError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
