package com.tomsoflow.swagger.bug.configuration;

import com.tomsoflow.swagger.bug.configuration.security.InjectRoleFilter;
import com.tomsoflow.swagger.bug.web.TestApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(RestController.class),
        basePackageClasses = TestApi.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RestConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/swagger-ui.html", "/v2/api-docs*/**", "/configuration/security",
                        "/webjars/springfox-swagger-ui/**", "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .authorizeRequests()
                .and()
                .addFilterBefore(new InjectRoleFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Test API")
                .description("Test to show that Swagger UI won't 'find' given rest controller and its endpoints")
                .version("v1")
                .build();
    }

    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(Authentication.class)
                .select()
                //.apis(RequestHandlerSelectors.withMethodAnnotation(RequestMapping.class))
                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.ant("/**"))
                .build()
                .apiInfo(apiInfo());
    }
}
