package com.zq.cloud.authuser.core.role.dto;

import com.zq.cloud.dto.DtoBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 角色创建实体
 */
@Data
@ApiModel
public class RoleCreateDto extends DtoBase {

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
    @NotEmpty(message = "请选择角色的资源")
    @ApiModelProperty("资源id")
    private List<Long> resourceIds;

}
