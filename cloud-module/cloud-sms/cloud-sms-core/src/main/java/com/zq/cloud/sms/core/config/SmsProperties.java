package com.zq.cloud.sms.core.config;

import com.aliyun.teaopenapi.models.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("zq.sms.provider")
public class SmsProperties {

    private AliYunProperties aliYun = new AliYunProperties();

    private TencentProperties tencent = new TencentProperties();

    @Data
    public static class AliYunProperties extends Config {
        private Boolean enabled = false;



    }


    @Data
    public static class TencentProperties {
        private Boolean enabled = false;


    }


}
