package com.stack.library.constants;

public class StackEmailConstants {

    public static final String STACK_MESSAGE_START="<div style=\"padding:5px;\">";
    public static final String STACK_MESSAGE_END="</div>";

    public static final String STACK_MESSAGE_HEADER =
        "<div style=\"display:inline-flex;width: -webkit-fill-available;border-bottom: 2px solid darkgray;\"><img src=\"https://www.stackitdown.com/favicon.ico\" style=\"width:32px;height:32px;margin-top:15px;\"><H1 style=\"margin:15px;color:#1ea5ae;\">Stack It Down</H1></div>"+
        "<br/><br/>";
    public static final String STACK_MESSAGE_FOOTER =
        "<h3>Now always stay on top of your tasks! <a href=\"http://www.stackitdown.com\" style=\"color:#1ea5ae;\">Get Started</a></h3>"+
        "<br/>" +
        "<br/>" +
        "<div style=\"font-size: xx-small;\">\n" +
        "<a href=\"https://icons8.com/icon/46919/user-location\">User Location icon by Icons8</a>\n" +
        "<a href=\"https://icons8.com/icon/119008/share\">Share icon by Icons8</a>\n" +
        "<a href=\"https://icons8.com/icon/42799/edit\">Edit icon by Icons8</a>\n" +
        "<a href=\"https://icons8.com/icon/60037/google-calendar\">Google Calendar icon by Icons8</a>\n" +
        "</div>";

    public static final String STACK_CREATED_TOPIC   = "STACK_CREATED";
    public static final String STACK_CREATED_SUBJECT = "Welcome to StackItDown!";
    public static final String STACK_CREATED_MESSAGE =
        STACK_MESSAGE_START+
        STACK_MESSAGE_HEADER +
        "<h2>Hello <b><i>{0}</i></b> !</h2><br/>"+
        "<h3>Welcome to <a href=\"http://www.stackitdown.com\" style=\"color:#1ea5ae;\">www.stackitdown.com</a> !</h3>"+
        "<h4>"+
        "<ul style=\"list-style-type:none;\">"+
        "<li><img src=\"https://img.icons8.com/dusk/32/000000/edit.png\"><i style=\"margin:15px;\">Keep up with those little things to do. Stack all your tasks in one place and free your mind</i></li><br/>"+
        "<li><img src=\"https://img.icons8.com/cute-clipart/32/000000/share.png\"><i style=\"margin:15px;\">Collaborate, push tasks to your friends</i></li><br/>"+
        "<li><img src=\"https://img.icons8.com/dusk/32/000000/user-location.png\"><i style=\"margin:15px;\">Get notified, get reminded to run that errand the next time you are in the vicinity</i></li><br/>"+
        "<li><img src=\"https://img.icons8.com/color/32/000000/google-calendar.png\"><i style=\"margin:15px;\">Add Google Calendar Events</I></li><br/>"+
        "</ul>"+
        "</h4>"+
        STACK_MESSAGE_FOOTER+
        STACK_MESSAGE_END;



    public static final String TASK_PUSHED_TOPIC   = "TASK_PUSHED";
    public static final String TASK_PUSHED_SUBJECT = "You have a new task.";
    public static final String TASK_PUSHED_MESSAGE =
        STACK_MESSAGE_START +
        STACK_MESSAGE_HEADER +
        "<h2>Dear <b><i>{0}</i></b>,</h2><br/>"+
        "<h3><i>{1}</i> has pushed a new task to your stack.</br></h3>"+
        "<p><b><i>{2}.</i></b></p><br/>"+
        STACK_MESSAGE_FOOTER+
        STACK_MESSAGE_END;

    public static final String TASK_NUDGE_TOPIC   = "TASK_NUDGE";
    public static final String TASK_NUDGE_SUBJECT = "You got nudged for a task.";
    public static final String TASK_NUDGE_MESSAGE =
        STACK_MESSAGE_START +
        STACK_MESSAGE_HEADER +
        "<h2>Dear <b><i>{0}</i></b>,</h2><br/>"+
        "<h3><b><i>{1}</i></b> has nudged you for a task in your stack.<br/></h3>"+
        "<p><b><i>{2}.</i></b></p><br/>"+
        STACK_MESSAGE_FOOTER+
        STACK_MESSAGE_END;

}
