package com.zq.cloud.authuser.core.user.dto;

import com.zq.cloud.dto.DtoBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户视图
 */
@Data
@ApiModel
public class UserVo extends DtoBase {


    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /**
     * 用户头像id
     */
    @ApiModelProperty(value = "用户头像id")
    private Long headPortraitId;

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
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realName;

    /**
     * 是否有效 0无效 1有效
     */
    @ApiModelProperty(value = "是否有效")
    private Boolean isAvailable;


    /**
     * 上次登录时间
     */
    @ApiModelProperty(value = "上次登录时间")
    private Date lastLoginTime;

    /**
     * 上次登录IP
     */
    @ApiModelProperty(value = "上次登录IP")
    private String lastLoginIp;

    /**
     * 上次登录地址
     */
    @ApiModelProperty(value = "上次登录地址")
    private String lastLoginAddress;

    /**
     * 当次登录时间
     */
    @ApiModelProperty(value = "当次登录时间")
    private Date loginTime;

    /**
     * 当次登录IP
     */
    @ApiModelProperty(value = "当次登录IP")
    private String loginIp;

    /**
     * 当次登录归属地
     */
    @ApiModelProperty(value = "当次登录归属地")
    private String loginAddress;
}
