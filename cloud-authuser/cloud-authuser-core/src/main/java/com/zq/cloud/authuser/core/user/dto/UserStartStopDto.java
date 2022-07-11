package com.zq.cloud.authuser.core.user.dto;

import com.zq.cloud.dto.DtoBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class UserStartStopDto extends DtoBase {

    /**
     * 用户id
     */
    @NotNull(message = "请选择用户")
    @ApiModelProperty("用户id")
    private Long id;

    /**
     * 启停用用户
     */
    @NotNull(message = "请选择启停用状态")
    @ApiModelProperty("启停用用户")
    private Boolean available;
}
