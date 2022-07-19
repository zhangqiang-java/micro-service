package com.zq.cloud.sms.core.provider.aliyu;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.zq.cloud.sms.core.provider.SmsProvider;
import com.zq.cloud.utils.BusinessAssertUtils;
import com.zq.cloud.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


/**
 * 阿里云短信服务
 */
@Slf4j
public class AliYunSmsProvider implements SmsProvider {

    private static final String SUCCESS_CODE = "OK";

    @Autowired
    private Client aliYunSmsClient;


    @Override
    public void send(String mobileNo, String content) {
        BusinessAssertUtils.fail("阿里云短信平台，不支持自定义发送短信");
    }


    @Override
    public void sendByTemplate(String mobileNo, String templateCode, String signName, Map<String, String> templateParam) {
        try {
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(mobileNo)
                    .setSignName(signName)
                    .setTemplateCode(templateCode)
                    .setTemplateParam(JacksonUtils.toJsonString(templateParam));
            SendSmsResponse sendSmsResponse = aliYunSmsClient.sendSms(sendSmsRequest);
            String statusCode = sendSmsResponse.getBody().code;
            BusinessAssertUtils.isTrue(SUCCESS_CODE.equals(statusCode), sendSmsResponse.getBody().message);
        } catch (Exception e) {
            log.error("阿里云发送短信异常,手机号：{},模板：{},参数：{}", mobileNo, templateCode, JacksonUtils.toJsonString(templateParam), e);
            BusinessAssertUtils.fail("阿里云发送短信异常");
        }
    }

}
