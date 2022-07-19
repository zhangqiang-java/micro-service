package com.zq.cloud.authuser.web.controller.service.auth;

import com.zq.cloud.authuser.core.auth.dto.UserPermissionDto;
import com.zq.cloud.authuser.core.auth.service.AuthService;
import com.zq.cloud.authuser.facade.AuthClient;
import com.zq.cloud.authuser.facade.dto.UserPermissionCheckDto;
import com.zq.cloud.dto.result.SingleResult;
import com.zq.cloud.utils.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AuthService authService;

    /**
     * 校验文件权限
     *
     * @param checkDto
     * @return
     */
    @Override
    public SingleResult<String> checkUserPermission(@RequestBody @Valid UserPermissionCheckDto checkDto) {
        UserPermissionDto userPermissionDto = authService.checkUserPermission(checkDto);
        return SingleResult.from(JacksonUtils.toJsonString(userPermissionDto));
    }
}
