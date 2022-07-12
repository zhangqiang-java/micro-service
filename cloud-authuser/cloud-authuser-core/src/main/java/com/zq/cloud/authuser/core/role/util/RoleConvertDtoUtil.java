package com.zq.cloud.authuser.core.role.util;

import com.zq.cloud.authuser.core.role.dto.RoleCreateDto;
import com.zq.cloud.authuser.core.role.dto.RoleInfoVo;
import com.zq.cloud.authuser.core.role.dto.RoleVo;
import com.zq.cloud.authuser.dal.model.Resource;
import com.zq.cloud.authuser.dal.model.Role;
import com.zq.cloud.authuser.dal.model.RoleResource;
import com.zq.cloud.utils.SnowFlakeUtils;

import java.util.List;

public class RoleConvertDtoUtil {
    /**
     * 创建角色实体Dto转换
     *
     * @param createDto
     * @return
     */
    public static Role convertToCrateRole(RoleCreateDto createDto) {
        Role role = new Role();
        role.setId(SnowFlakeUtils.getNextId());
        role.setName(createDto.getName());
        role.setDescription(createDto.getDescription());
        role.setIsAvailable(Boolean.TRUE);
        return role;
    }


    /**
     * 角色展示Dto 转换
     *
     * @param role
     * @return
     */
    public static RoleVo convertToRoleVo(Role role) {
        RoleVo roleVo = new RoleVo();
        roleVo.setId(role.getId());
        roleVo.setName(role.getName());
        roleVo.setDescription(role.getDescription());
        roleVo.setAvailable(role.getIsAvailable());
        return roleVo;
    }

    /**
     * 角色详情展示Dto 转换
     *
     * @param role
     * @return
     */
    public static RoleInfoVo convertToRoleInfoVo(Role role, List<Resource> resourceList) {
        RoleInfoVo roleInfoVo = new RoleInfoVo();
        roleInfoVo.setId(role.getId());
        roleInfoVo.setName(role.getName());
        roleInfoVo.setDescription(role.getDescription());
        roleInfoVo.setAvailable(role.getIsAvailable());
        roleInfoVo.setResourceList(resourceList);
        return roleInfoVo;
    }
}
