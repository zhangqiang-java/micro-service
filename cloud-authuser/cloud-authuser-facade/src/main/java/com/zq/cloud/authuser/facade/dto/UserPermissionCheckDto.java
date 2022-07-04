package com.zq.cloud.authuser.facade.dto;

import com.zq.cloud.dto.DtoBase;
import lombok.Data;
import lombok.ToString;

/**
 * gateway 权限校验
 */
@Data
@ToString
public class UserPermissionCheckDto extends DtoBase {

    /**
     * 权限token
     */
    private String token;

    /**
     * 请求路径
     */
    private String path;
}
