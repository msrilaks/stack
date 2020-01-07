package com.stack.taskservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.any())
                                                      .apis(RequestHandlerSelectors
                                                                    .basePackage(
                                                                            "com.stack" +
                                                                            ".taskservice"))
                                                      .paths(PathSelectors.any())
                                                      .build();
        Set<String> protoSet = new HashSet<String>();
        protoSet.add("http");
        protoSet.add("https");
        docket.protocols(protoSet);
        return docket;
    }
}