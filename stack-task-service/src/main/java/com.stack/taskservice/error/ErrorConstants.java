package com.stack.taskservice.error;

public class ErrorConstants {
    public static String APP_ERR_MSG                         =
            "An application error occured";
    public static String STACK_ID_INVALID_ERR_MSG            = "Stack Id is invalid";
    public static String STACK_USER_ID_INVALID_ERR_MSG       = "Stack User Id is invalid";
    public static String TASK_USER_ID_INVALID_ERR_MSG        = "Task User Id is invalid";
    public static String UNAUTHORIZED_USER_ERR_MSG           = "User JWT is invalid";
    public static String UNAUTHORIZED_USER_NOT_FOUND_ERR_MSG = "User JWT not found " +
                                                               "in request";
}
