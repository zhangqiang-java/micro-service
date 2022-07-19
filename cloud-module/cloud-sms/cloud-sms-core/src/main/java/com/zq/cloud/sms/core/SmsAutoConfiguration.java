package com.zq.cloud.sms.core;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import com.zq.cloud.sms.core.config.SmsProperties;
import com.zq.cloud.sms.core.provider.aliyu.AliYunSmsProvider;
import com.zq.cloud.sms.core.provider.tencent.TencentSmsProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({SmsProperties.class})
@Slf4j
public class SmsAutoConfiguration {


    @Configuration
    @ConditionalOnProperty(value = "zq.sms.provider.aliYun.enabled", havingValue = "true")
    static public class AliYunAutoConfiguration {

        @Bean
        public Client AliYunSmsClient(SmsProperties smsProperties) throws Exception {
            Config config = new Config()
                    .setAccessKeyId(smsProperties.getAliYun().getAccessKeyId())
                    .setAccessKeySecret(smsProperties.getAliYun().getAccessKeySecret());
            config.endpoint = "dysmsapi.aliyuncs.com";
            return new Client(config);
        }

        @Bean
        public AliYunSmsProvider aliYunSmsProvider() {
            return new AliYunSmsProvider();
        }
    }


    @Configuration
    @ConditionalOnProperty(value = "zq.sms.provider.tencent.enabled", havingValue = "true")
    static public class TencentAutoConfiguration {
        @Bean
        public TencentSmsProvider tencentSmsProvider() {
            return new TencentSmsProvider();
        }
    }


}
