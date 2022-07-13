package com.zq.cloud.authuser.dal.dto;

import com.zq.cloud.dto.DtoBase;
import lombok.Data;

@Data
public class RoleSearchDto extends DtoBase {
    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */

    private String description;

    /**
     * 是否有效 0无效 1有效
     */
    private Boolean isAvailable;

    /**
     * 用户id
     */
    private Long userId;
}
