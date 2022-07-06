package com.zq.cloud.authuser.web.controller;

import com.zq.cloud.dto.exception.BusinessException;
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
@RequestMapping("/anon/authuser")
public class Test {


    @GetMapping("/test")
    public String test() {
        BusinessException businessException = new BusinessException("测试异常");
        throw businessException;
    }


    @GetMapping("/test2")
    public String test2() throws InterruptedException {
       Thread.sleep(2000);
       return "2";
    }
}
