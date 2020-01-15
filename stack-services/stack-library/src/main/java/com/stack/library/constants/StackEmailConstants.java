package com.stack.library.constants;

public class StackEmailConstants {
    public static final String STACK_CREATED_TOPIC   = "STACK_CREATED";
    public static final String STACK_CREATED_SUBJECT = "Welcome to StackItDown!";
    public static final String STACK_CREATED_MESSAGE =
            "Hello <b><i>{0}</i></b>! \n"+
            "Welcome to www.stackitdown.com!\n" +
            "Now always stay on top of your " +
            "things-to-do.\n";

    public static final String TASK_PUSHED_TOPIC   = "TASK_PUSHED";
    public static final String TASK_PUSHED_SUBJECT = "You have a new task.";
    public static final String TASK_PUSHED_MESSAGE =
            "Dear {0},<br/>"+
            "{1} has pushed a new task to your stack."+
            "<i>{2}.</i><br/>"+
            "Please login to" +
            "stackitdown.com/stack/{3}/tasks/{4}<br/>";
}
