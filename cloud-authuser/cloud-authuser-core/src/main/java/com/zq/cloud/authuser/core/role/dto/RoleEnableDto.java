package com.zq.cloud.authuser.core.role.dto;

import com.zq.cloud.dto.DtoBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色停启用
 */
@Data
@ApiModel
public class RoleEnableDto extends DtoBase {

    /**
     * 角色id
     */
    @NotNull(message = "请选择角色")
    @ApiModelProperty("角色id")
    private Long id;

    /**
     * 启停用用户
     */
    @NotNull(message = "请选择启停用状态")
    @ApiModelProperty("启停用角色")
    private Boolean available;

}
