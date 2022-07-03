package com.zq.cloud.authuser.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: micro-service
 * @description:
 * @author: zhang qiang
 * @created: 2022/07/02 19:45
 */
@RestController
@RequestMapping("/auth/user")
public class Test {


    @GetMapping("/test")
    public String test() {
       int  a=0/0;
        return "223";
    }
}
