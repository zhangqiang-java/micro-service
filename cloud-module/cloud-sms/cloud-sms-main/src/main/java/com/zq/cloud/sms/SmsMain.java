package com.zq.cloud.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.zq.cloud.sms.dal")
@EnableFeignClients("com.zq")
@SpringBootApplication
public class SmsMain {
    public static void main(String[] args) {
        SpringApplication.run(SmsMain.class);
    }
}
