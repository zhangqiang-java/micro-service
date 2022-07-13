package com.zq.cloud.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("com.zq")
@SpringBootApplication
public class FileMain {
    public static void main(String[] args) {
        SpringApplication.run(FileMain.class, args);
    }
}
