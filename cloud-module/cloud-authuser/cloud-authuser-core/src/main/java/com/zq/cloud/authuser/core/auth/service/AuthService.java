package com.zq.cloud.authuser.core.auth.service;

import com.zq.cloud.authuser.core.auth.dto.UserPermissionDto;
import com.zq.cloud.authuser.facade.dto.UserPermissionCheckDto;

public interface AuthService {

    UserPermissionDto checkUserPermission(UserPermissionCheckDto checkDto);

}
