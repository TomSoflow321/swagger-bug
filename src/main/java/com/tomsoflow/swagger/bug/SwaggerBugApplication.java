package com.tomsoflow.swagger.bug;

import com.tomsoflow.swagger.bug.configuration.RestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import(RestConfiguration.class)
public class SwaggerBugApplication {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(SwaggerBugApplication.class);
        application.run(args);
    }
}
