package com.stack.library.constants;

public class StackEmailConstants {
    public static final String STACK_CREATED_TOPIC   = "STACK_CREATED";
    public static final String STACK_CREATED_SUBJECT = "Welcome to StackItDown!";
    public static final String STACK_CREATED_MESSAGE =
            "Hello <b><i>{0}</i></b>! <br/><br/>"+
            "Welcome to www.stackitdown.com!<br/><br/>" +
            "Now always stay on top of your tasks!<br/>" ;

    public static final String TASK_PUSHED_TOPIC   = "TASK_PUSHED";
    public static final String TASK_PUSHED_SUBJECT = "You have a new task.";
    public static final String TASK_PUSHED_MESSAGE =
            "Dear <b><i>{0}</i></b>,<br/><br/>"+
            "<b><i>{1}</i></b> has pushed a new task to your stack.</br>"+
            "<p><b><i>{2}.</i></b></p><br/>"+
            "Please login to www.stackitdown.com to take a look.<br/>";

    public static final String TASK_NUDGE_TOPIC   = "TASK_NUDGE";
    public static final String TASK_NUDGE_SUBJECT = "You got nudged for a task.";
    public static final String TASK_NUDGE_MESSAGE =
            "Dear <b><i>{0}</i></b>,<br/><br/>"+
            "<b><i>{1}</i></b> has nudged you for a task in your stack.<br/>"+
            "<p><b><i>{2}.</i></b></p><br/>"+
            "Please login to www.stackitdown.com to take a look.<br/>";

}
