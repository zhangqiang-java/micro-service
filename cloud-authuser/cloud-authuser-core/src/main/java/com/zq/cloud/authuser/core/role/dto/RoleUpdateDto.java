package com.zq.cloud.authuser.core.role.dto;

import com.zq.cloud.dto.DtoBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色修改实体
 */
@Data
@ApiModel
public class RoleUpdateDto extends DtoBase {

    /**
     * 角色id
     */
    @NotNull(message = "角色不能为空")
    @ApiModelProperty("角色id")
    private Long id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty("角色名称")
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;


    /**
     * 资源id
     */
    @ApiModelProperty("资源id")
    @NotBlank(message = "资源不能为空")
    private List<Long> resourceIds;

}
