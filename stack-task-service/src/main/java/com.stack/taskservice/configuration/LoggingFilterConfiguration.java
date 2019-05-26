package com.stack.taskservice.configuration;

import com.stack.taskservice.filter.RequestResponseLoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class LoggingFilterConfiguration {
    @Autowired
    private RequestResponseLoggingFilter filter;

    @Bean
    public FilterRegistrationBean loggingFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
