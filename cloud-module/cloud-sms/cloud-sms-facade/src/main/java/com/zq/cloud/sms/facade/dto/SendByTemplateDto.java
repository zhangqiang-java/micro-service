package com.zq.cloud.sms.facade.dto;

import lombok.Data;

import java.util.Map;

/**
 * 模板短信发送
 */
@Data
public class SendByTemplateDto {

    String mobileNo;

    String templateCode;

    String signName;

    Map<String, String> templateParam;
}
