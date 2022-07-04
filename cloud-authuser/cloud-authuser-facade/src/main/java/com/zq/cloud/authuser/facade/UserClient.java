package com.zq.cloud.authuser.facade;

import com.zq.cloud.authuser.facade.dto.UserPermissionCheckDto;
import com.zq.cloud.dto.result.ResultBase;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "${cloud-authuser.service-id:cloud-authuser}", path = "/service/authuser/user")
public interface UserClient {

    @GetMapping("/checkUserPermission")
    ResultBase<String> checkUserPermission(@RequestBody @Valid UserPermissionCheckDto checkDto);
}
