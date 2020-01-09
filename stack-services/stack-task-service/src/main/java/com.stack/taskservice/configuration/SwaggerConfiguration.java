package com.stack.taskservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        Set<String> protoSet = new HashSet<String>();
        protoSet.add("https");
        Parameter authHeader = new ParameterBuilder()
                .parameterType("header")
                .name("Authorization")
                .modelRef(new ModelRef("string"))
                .build();
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.any())
            .apis(RequestHandlerSelectors
            .basePackage("com.stack.taskservice"))
            .paths(PathSelectors.any()).build()
            .protocols(protoSet)
            .consumes(new LinkedHashSet<>(
                    Arrays.asList("application/json")))
            .produces(new LinkedHashSet<>(
                  Arrays.asList("application/json")))
            .globalOperationParameters(Collections.singletonList(authHeader))
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
            "Stack It Down REST API Specification",
            "REST APIs to create and manage Stacks, Tasks, Photos and Event.",
            "v1",
            "Terms of service",
            new Contact("Admin", "www.stackitdown.com", "stackitdown@gmail.com"),
            "License", "API license URL", Collections.emptyList());
    }
}