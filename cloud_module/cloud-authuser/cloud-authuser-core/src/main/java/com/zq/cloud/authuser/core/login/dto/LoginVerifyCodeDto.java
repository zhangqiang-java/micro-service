package com.zq.cloud.authuser.core.login.dto;

import com.zq.cloud.dto.DtoBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 手机号验证码Dto
 */
@Data
@ApiModel
public class LoginVerifyCodeDto extends DtoBase {

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号", example = "13629759697")
    private String mobile;

    /**
     * 验证码（todo 待实现）
     */
    @ApiModelProperty("验证码")
    private String verifyCode;
}
