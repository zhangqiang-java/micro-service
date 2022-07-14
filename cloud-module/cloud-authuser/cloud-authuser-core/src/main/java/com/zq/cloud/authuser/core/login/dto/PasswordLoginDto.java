package com.zq.cloud.authuser.core.login.dto;

import com.zq.cloud.dto.DtoBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class PasswordLoginDto extends DtoBase {

    /**
     * 账号
     */
    @NotBlank(message = "请输入账号")
    @ApiModelProperty("账号")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "请输入密码")
    @ApiModelProperty("密码")
    private String password;

    /**
     * 验证码（todo 待实现）
     */
    @ApiModelProperty("验证码")
    private String verifyCode;
}
