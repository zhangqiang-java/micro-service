package com.zq.cloud.authuser.core.user.dto;

import com.zq.cloud.dto.request.PageRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserPageRequestDto extends PageRequestDto {
    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称,模糊匹配")
    private String nickname;


    /**
     * 地址
     */
    @ApiModelProperty(value = "地址,模糊匹配")
    private String address;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号,精确匹配")
    private String mobile;


    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名，模糊匹配")
    private String realName;

}
