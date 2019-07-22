package com.huangxi.springboot.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@EnableCaching
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {

}
