package com.zq.cloud.authuser.core.role.dto;

import com.zq.cloud.dto.request.PageRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RolePageRequestDto extends PageRequestDto {
    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称,模糊匹配")
    private String name;

    @ApiModelProperty(value = "当前登录用户", hidden = true)
    private Long userId;
}
