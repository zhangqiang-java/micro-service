package com.zq.cloud.sms.core.provider.tencent;

import com.zq.cloud.sms.core.provider.SmsProvider;
import com.zq.cloud.utils.BusinessAssertUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 腾讯云
 */
@Slf4j
public class TencentSmsProvider implements SmsProvider {
    @Override
    public void send(String mobileNo, String content) {
        BusinessAssertUtils.fail("腾讯云短信平台，不支持自定义发送短信");
    }


    @Override
    public void sendByTemplate(String mobileNo, String templateCode, String signName, Map<String, String> templateParam) {
        //todo
    }

}
