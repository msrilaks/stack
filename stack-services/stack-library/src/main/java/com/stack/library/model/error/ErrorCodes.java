package com.stack.library.model.error;

import static com.stack.library.model.error.ErrorConstants.*;

public enum ErrorCodes {
    APPLICATION_ERROR(100, APP_ERR_MSG, APP_ERR_MSG),
    STACK_ID_INVALID(200, STACK_ID_INVALID_ERR_MSG, STACK_ID_INVALID_ERR_MSG),
    STACK_USER_ID_INVALID(201, STACK_USER_ID_INVALID_ERR_MSG,
                          STACK_USER_ID_INVALID_ERR_MSG),
    TASK_ID_INVALID(300, TASK_ID_INVALID_ERR_MSG,
                    TASK_ID_INVALID_ERR_DESC),
    TASK_USER_ID_INVALID(301, TASK_USER_ID_INVALID_ERR_MSG,
                         TASK_USER_ID_INVALID_ERR_MSG),
    TASK_CREATED_BY_USER_ID_INVALID(302, TASK_CREATED_BY_USER_ID_INVALID_ERR_MSG,
                                    TASK_CREATED_BY_USER_ID_INVALID_ERR_MSG),
    UNAUTHORIZED_USER(400, UNAUTHORIZED_USER_ERR_MSG, UNAUTHORIZED_USER_ERR_MSG),
    UNAUTHORIZED_USER_NOT_FOUND_ERR(401, UNAUTHORIZED_USER_NOT_FOUND_ERR_MSG,
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
