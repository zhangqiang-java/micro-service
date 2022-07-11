package com.zq.cloud.authuser.core.user.dto;

import com.zq.cloud.dto.DtoBase;
import com.zq.cloud.validation.MobileNo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@ApiModel
public class UserCreateDto extends DtoBase {

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 手机号
     */
    @MobileNo(message = "请输入正确的手机号码")
    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 邮箱
     */
    @Email(message = "请输入正确得邮箱")
    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 真实姓名
     */
    @NotBlank(message = "真实姓名为空")
    @ApiModelProperty(value = "真实姓名")
    private String realName;

    /**
     * 用户头像id
     */
    @ApiModelProperty(value = "用户头像id")
    private Long headPortrait;

}
