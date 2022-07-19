package com.zq.cloud.sms.web.service;


import com.zq.cloud.dto.result.ResultBase;
import com.zq.cloud.sms.core.service.SmsService;
import com.zq.cloud.sms.facade.SmsClient;
import com.zq.cloud.sms.facade.dto.SendByTemplateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 提供给微服务调用的发送短信
 */
@RestController
@RequestMapping("/service/sms")
public class SmsServiceController implements SmsClient {
    @Autowired
    private SmsService service;


    public ResultBase<Void> sendByTemplate(@RequestBody @Valid SendByTemplateDto sendByTemplateDto) {
        service.sendByTemplate(sendByTemplateDto.getMobileNo(),
                sendByTemplateDto.getTemplateCode(),
                sendByTemplateDto.getSignName(), sendByTemplateDto.getTemplateParam());
        return ResultBase.success();
    }
}
