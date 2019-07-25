package com.stack.taskservice.context;


import com.stack.taskservice.model.Stack;
import com.stack.taskservice.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StackRequestContext {
    private Stack  stack;
    private User   user;
    private String stackId;
}
