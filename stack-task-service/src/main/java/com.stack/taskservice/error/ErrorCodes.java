package com.stack.taskservice.error;

import com.stack.taskservice.model.Error;

import static com.stack.taskservice.error.ErrorConstants.*;

public enum ErrorCodes {
    APPLICATION_ERROR(1, APP_ERR_MSG, APP_ERR_MSG),
    STACK_ID_INVALID(2, STACK_ID_INVALID_ERR_MSG, STACK_ID_INVALID_ERR_MSG),
    STACK_USER_ID_INVALID(3, STACK_USER_ID_INVALID_ERR_MSG,
                          STACK_USER_ID_INVALID_ERR_MSG),
    TASK_USER_ID_INVALID(4, TASK_USER_ID_INVALID_ERR_MSG,
                         TASK_USER_ID_INVALID_ERR_MSG),
    UNAUTHORIZED_USER(5, UNAUTHORIZED_USER_ERR_MSG, UNAUTHORIZED_USER_ERR_MSG),
    UNAUTHORIZED_USER_NOT_FOUND_ERR(6, UNAUTHORIZED_USER_NOT_FOUND_ERR_MSG,
                                    UNAUTHORIZED_USER_NOT_FOUND_ERR_MSG);


    private int    errorCode;
    private String errorMessage;
    private String errorDescription;

    ErrorCodes(int errorCode, String errorMessage, String errorDescription) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDescription = errorDescription;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public Error constructError() {
        return new Error(errorCode, errorMessage, errorDescription);
    }
}
