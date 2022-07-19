package com.zq.cloud.sms.core.service;

import java.util.Map;

public interface SmsService {


    void send(String mobileNo, String content) throws Exception;


    void sendByTemplate(String mobileNo, String templateCode, String signName, Map<String, String> templateParam);


}
