package com.stack.taskservice.error;

public class ErrorConstants {
    public static String APP_ERR_MSG                             =
            "An application error occured";
    public static String STACK_ID_INVALID_ERR_MSG                = "Stack Id is invalid";
    public static String STACK_USER_ID_INVALID_ERR_MSG           =
            "Stack User Id is invalid";
    public static String TASK_USER_ID_INVALID_ERR_MSG            =
            "Task UserId is invalid";
    public static String TASK_ID_INVALID_ERR_MSG                 =
            "Task Id is invalid";
    public static String TASK_ID_INVALID_ERR_DESC                =
            "Task was not found with given Id.";
    public static String TASK_CREATED_BY_USER_ID_INVALID_ERR_MSG = "Task " +
                                                                   "Created By " +
                                                                   "UserId is invalid";
    public static String UNAUTHORIZED_USER_ERR_MSG               = "User JWT is invalid";
    public static String UNAUTHORIZED_USER_NOT_FOUND_ERR_MSG     = "User JWT not found " +
                                                                   "in request";
}
