package com.zq.cloud.sms.facade;

import com.zq.cloud.dto.result.ResultBase;
import com.zq.cloud.sms.facade.dto.SendByTemplateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "${cloud-sms.service-id:cloud-sms}", path = "/service/sms")
public interface SmsClient {

    @PostMapping("/sendByTemplate")
    ResultBase<Void> sendByTemplate(@RequestBody @Valid SendByTemplateDto sendByTemplateDto);

}
