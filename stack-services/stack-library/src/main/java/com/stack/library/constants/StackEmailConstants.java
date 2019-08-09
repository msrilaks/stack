package com.stack.library.constants;

public class StackEmailConstants {
    public static final String STACK_CREATED_TOPIC   = "STACK_CREATED";
    public static final String STACK_CREATED_SUBJECT = "Welcome to StackItDown!";
    public static final String STACK_CREATED_MESSAGE =
            "Hello {0}! \nWelcome to www.stackitdown" +
            ".com!\n" +
            "Now always stay on top of your " +
            "things-to-do.\n";

    public static final String TASK_PUSHED_TOPIC   = "TASK_PUSHED";
    public static final String TASK_PUSHED_SUBJECT = "You have a new task.";
    public static final String TASK_PUSHED_MESSAGE =
            "{0} sent you a new task\n. Please login to" +
            "stackitdown.com/stack/{1}/tasks/{2}\\n";
}
