package de.motivational.stairs.rest.swagger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Florian on 20.11.2016.
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = {})
public class Swagger2Spring {

    @Bean
    public Docket api1_0() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API Version 1.0")
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/v1.0/**"))
                .build()
                .pathMapping("/");
    }

//    @Bean
//    public Docket api2_0() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("API Version 2.0")
//                .apiInfo(getApiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.ant("/api/2.0/**"))
//                .build()
//                .pathMapping("/");
//    }

    private ApiInfo getApiInfo() {
        Contact contact = new Contact("Frederike Bruns, Viktor Spadi, Florian Decker", "http://motivationalstairs.com", "team@motivationalstairs.com");
        ApiInfo info = new ApiInfo("Motivational Stairs API", "Stairs Stairs Stairs.", "1.0.0", "http://terms-of-service.url",
                contact, "License", "http://motivationalstairs.com");
        return info;
    }
}
