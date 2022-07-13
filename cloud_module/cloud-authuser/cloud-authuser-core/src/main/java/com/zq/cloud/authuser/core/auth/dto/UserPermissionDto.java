package com.zq.cloud.authuser.core.auth.dto;

import com.zq.cloud.dto.DtoBase;
import lombok.Data;

/**
 * 用户权限校验返回
 */
@Data
public class UserPermissionDto extends DtoBase {

    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String nickname;


    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 真实姓名
     */
    private String realName;

}
