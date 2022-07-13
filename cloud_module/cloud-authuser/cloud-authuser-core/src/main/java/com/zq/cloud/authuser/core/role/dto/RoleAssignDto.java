package com.zq.cloud.authuser.core.role.dto;

import com.zq.cloud.dto.DtoBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色分配dto
 */
@Data
@ApiModel
public class RoleAssignDto extends DtoBase {

    /**
     * 待分配的用户id
     */
    @NotNull(message = "请选择分配的用户")
    @ApiModelProperty("待分配的用户id")
    private Long userId;


    /**
     * 待分配的角色
     */
    @NotEmpty(message = "请选择要分配的角色")
    @ApiModelProperty("待分配的角色ids")
    private List<Long> roleIds;


}
