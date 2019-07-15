package com.huangxi.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = {"com.huangxi.springboot.web.servlet"})
public class SpringbootApplication {

    public static void main(String[] args) {
//        new SpringApplicationBuilder(SpringbootApplication.class)
//                //.web(WebApplicationType.NONE)
//                .profiles("application.properties")
//                .run(args);

        SpringApplication.run(SpringbootApplication.class, args);
    }

}
