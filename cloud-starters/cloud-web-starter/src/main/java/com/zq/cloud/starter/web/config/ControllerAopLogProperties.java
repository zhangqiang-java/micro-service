package com.zq.cloud.starter.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("zq.cloud.web.controllerLog")
public class ControllerAopLogProperties {

    /**
     * 开启aop日志记录
     */
    private Boolean enabled = true;

    /**
     * 日志记录长度
     */
    private Integer logLength = 1024;


}
