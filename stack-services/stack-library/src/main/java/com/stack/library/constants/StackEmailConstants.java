package com.stack.library.constants;

public class StackEmailConstants {

    public static final String STACK_EMAIL_SUBSCRIPTION="STACK_EMAIL_SUBSCRIPTION";
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
        "<a href=\"https://icons8.com/icon/12028/price-tag\">Price Tag icon by Icons8</a>"+
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
        "<li><img src=\"https://img.icons8.com/dusk/32/000000/edit.png\"><i style=\"margin:15px;\">" +
            "Keep up with those many little things to do. Stack all your tasks in one place " +
            "and free your mind for more creative thoughts. There&#39;s no need to cram a " +
            "long list of to-do&#39;s into your mind or on little scribble notes or to " +
            "worry about some of them falling off the back of your mind. Keeping up with an " +
            "ever-growing, overwhelming list of to-do&#39;s has never been easier." +
            "</i></li><br/>"+
       "<li><img src=\"https://img.icons8.com/dusk/32/000000/user-location.png\">" +
            "<i style=\"margin:15px;\">Get notified. Get reminded to run an errand when " +
            "you are in the vicinity. Stack It Down notifies you of tasks that you " +
            "can complete around your current location.</i></li><br/>"+
        "<li><img src=\"https://img.icons8.com/cute-clipart/32/000000/share.png\">" +
            "<i style=\"margin:15px;\">" +
            "Collaborate, push tasks to your friends and nudge them to get it done." +
            "</i></li><br/>"+
        "<li><img src=\"https://img.icons8.com/color/32/000000/price-tag.png\">" +
            "<i style=\"margin:15px;\">" +
            "Tag your tasks and organize them into categories to search easily." +
            "</i></li><br/>"+
        "<li><img src=\"https://img.icons8.com/color/32/000000/google-calendar.png\">" +
            "<i style=\"margin:15px;\">" +
            "Add Google Calendar Events." +
            "</I></li><br/>"+
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
        "<h4>"+
        "<ul style=\"list-style-type:none;\">"+
        "{2}"+
        "</ul>"+
        "</h4>"+
        STACK_MESSAGE_FOOTER+
        STACK_MESSAGE_END;

    public static final String TASK_NUDGE_TOPIC   = "TASK_NUDGE";
    public static final String TASK_NUDGE_SUBJECT = "You got nudged for a task.";
    public static final String TASK_NUDGE_MESSAGE =
        STACK_MESSAGE_START +
        STACK_MESSAGE_HEADER +
        "<h2>Dear <b><i>{0}</i></b>,</h2><br/>"+
        "<h3><b><i>{1}</i></b> has nudged you for a task in your stack.<br/></h3>"+
        "<h4>"+
        "<ul style=\"list-style-type:none;\">"+
        "{2}"+
        "</ul>"+
        "</h4>"+
        STACK_MESSAGE_FOOTER+
        STACK_MESSAGE_END;

    public static final String TASK_NEAR_LOCATION_TOPIC   = "TASK_NEAR_LOCATION";
    public static final String TASK_NEAR_LOCATION_SUBJECT = "You have tasks " +
                                        "to do in this neighbourhood.";
    public static final String TASK_NEAR_LOCATION_MESSAGE =
            STACK_MESSAGE_START +
            STACK_MESSAGE_HEADER +
            "<h2>Dear <b><i>{0}</i></b>,</h2><br/>"+
            "<h3>While you are here, you can get a few tasks completed around this " +
            "neighbourhood." +
            "</br></h3>"+
            "<h4>"+
            "<ul style=\"list-style-type:none;\">"+
            "{2}"+
            "</ul>"+
            "</h4>"+
            STACK_MESSAGE_FOOTER+
            STACK_MESSAGE_END;

    public static final String TASK_MESSAGE =
            "<li><img src=\"https://img.icons8.com/dusk/32/000000/edit.png\">" +
            "<i style=\"margin:15px;\">" +
            "<a href=\"{1}\">{0}.</a>" +
            "</i>" +
            "</li><br/>";

}
