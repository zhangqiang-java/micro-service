package com.zq.cloud.sms.core.provider;

import java.util.Map;

public interface SmsProvider {

    void send(String mobileNo, String content);


    void sendByTemplate(String mobileNo, String templateCode, String signName, Map<String, String> templateParam);


}
