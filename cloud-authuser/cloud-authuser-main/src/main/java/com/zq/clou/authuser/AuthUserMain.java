package com.zq.clou.authuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.zq.cloud.authuser.dal")
@SpringBootApplication
@EnableFeignClients("com.zq")
public class AuthUserMain {
    public static void main(String[] args) {

        SpringApplication.run(AuthUserMain.class, args);
    }
}
