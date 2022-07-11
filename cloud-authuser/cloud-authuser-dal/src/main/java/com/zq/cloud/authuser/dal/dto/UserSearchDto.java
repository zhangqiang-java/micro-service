package com.zq.cloud.authuser.dal.dto;

import com.zq.cloud.dto.DtoBase;
import lombok.Data;

@Data
public class UserSearchDto extends DtoBase {

    /**
     * 用户昵称 模糊匹配
     */
    private String nickname;


    /**
     * 地址 模糊匹配
     */
    private String address;

    /**
     * 手机号 精确匹配
     */
    private String mobile;


    /**
     * 真实姓名 模糊匹配
     */
    private String realName;
}
