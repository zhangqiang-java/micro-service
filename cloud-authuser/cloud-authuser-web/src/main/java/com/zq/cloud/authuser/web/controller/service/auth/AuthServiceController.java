package com.zq.cloud.authuser.web.controller.service.auth;

import com.zq.cloud.authuser.facade.AuthClient;
import com.zq.cloud.authuser.facade.dto.UserPermissionCheckDto;
import com.zq.cloud.dto.exception.NotLoginException;
import com.zq.cloud.dto.result.ResultBase;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 提供给微服务调用的鉴权
 */
@RestController
@RequestMapping("/service/authuser/auth")
public class AuthServiceController implements AuthClient {
    @Override
    public ResultBase<String> checkUserPermission(@RequestBody @Valid UserPermissionCheckDto checkDto) {
        //todo 权限校验逻辑
        throw new NotLoginException();
    }
}
