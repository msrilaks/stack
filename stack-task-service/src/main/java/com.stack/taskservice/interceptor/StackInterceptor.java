package com.stack.taskservice.interceptor;

import com.stack.taskservice.context.StackRequestContext;
import com.stack.taskservice.model.Stack;
import com.stack.taskservice.services.StackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class StackInterceptor extends HandlerInterceptorAdapter {
    @Resource(name = "stackRequestContext")
    StackRequestContext stackRequestContext;

    @Autowired
    StackService stackService;

    @Override
    public boolean preHandle(
            HttpServletRequest requestServlet, HttpServletResponse responseServlet,
            Object handler) throws Exception {
        final Map<String, String> pathVariables = (Map<String, String>) requestServlet
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (pathVariables.get("stackId") != null) {
            final String stackId = String.valueOf(pathVariables.get("stackId"));
            Stack stack = stackService.getStack(stackId);
            stackRequestContext.setStack(stack);
        }
        return true;
    }
}
