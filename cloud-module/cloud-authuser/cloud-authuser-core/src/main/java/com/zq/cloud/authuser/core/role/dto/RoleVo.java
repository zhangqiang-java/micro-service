package com.zq.cloud.authuser.core.role.dto;

import com.zq.cloud.dto.DtoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleVo extends DtoBase {
    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    /**
     * 启停用用户
     */
    @ApiModelProperty("启停用角色")
    private Boolean available;
}
