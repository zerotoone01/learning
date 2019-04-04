package com.intellif.apollo.apollodemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangluojun
 * @version 2.0.0
 * @date 2019年04月03日
 * @description: TODO
 * @see
 * @since JDK1.8
 **/
@RestController
@RequestMapping("")
public class ApolloController {

    @GetMapping("/index")
    public String hello(){
        return "hello man";
    }
}
