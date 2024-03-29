package com.stack.taskservice.context;


import com.stack.library.model.stack.Stack;
import com.stack.library.model.user.User;
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
