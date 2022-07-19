package com.zq.cloud.sms.core.service.impl;

import com.zq.cloud.sms.core.constants.SystemConstants;
import com.zq.cloud.sms.core.provider.SmsProvider;
import com.zq.cloud.sms.core.route.SmsProviderRouteStrategy;
import com.zq.cloud.sms.core.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 短信服务
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Value("${zq.sms.whiteList:}")
    private List<String> smsSendWhiteList;

    @Autowired
    private SmsProviderRouteStrategy routeStrategy;


    @Override
    public void send(String mobileNo, String content) {
        if (checkNeedSend(mobileNo)) {
            SmsProvider smsProvider = routeStrategy.chooseProvider();
            smsProvider.send(mobileNo, content);
        }

    }

    @Override
    public void sendByTemplate(String mobileNo, String templateCode, String signName, Map<String, String> templateParam) {
        if (checkNeedSend(mobileNo)) {
            SmsProvider smsProvider = routeStrategy.chooseProvider();
            smsProvider.sendByTemplate(mobileNo, templateCode, signName, templateParam);
        }

    }

    /**
     * 只有线上环境 或者配置了白名单的测试用户 才真正发送短信
     *
     * @param mobileNo
     * @return
     */
    private Boolean checkNeedSend(String mobileNo) {
        String activeProfile = System.getProperty(SystemConstants.SPRING_PROFILES_ACTIVE);
        return activeProfile.equals(SystemConstants.ONLINE_PROFILE) || smsSendWhiteList.contains(mobileNo);
    }
}
