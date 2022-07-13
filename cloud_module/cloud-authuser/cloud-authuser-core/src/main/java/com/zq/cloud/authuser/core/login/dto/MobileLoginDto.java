package com.zq.cloud.authuser.core.login.dto;

import com.zq.cloud.dto.DtoBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class MobileLoginDto extends DtoBase {

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号", example = "13600000000")
    private String mobile;

    @NotBlank(message = "短信验证码不能为空")
    @ApiModelProperty(value = "短信验证码", example = "123456")
    private String smsVerifyCode;

}
