package com.zq.cloud.authuser.web.controller;

import com.zq.cloud.authuser.facade.UserClient;
import com.zq.cloud.authuser.facade.dto.UserPermissionCheckDto;
import com.zq.cloud.dto.exception.NotLoginException;
import com.zq.cloud.dto.result.ResultBase;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/service/authuser/user")
public class UserServiceController implements UserClient {
    @Override
    public ResultBase<String> checkUserPermission(@RequestBody @Valid UserPermissionCheckDto checkDto) {
        //todo 权限校验逻辑
        throw new NotLoginException();
    }
}
