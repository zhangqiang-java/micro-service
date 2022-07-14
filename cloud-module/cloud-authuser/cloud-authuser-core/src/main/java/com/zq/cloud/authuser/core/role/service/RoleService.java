package com.zq.cloud.authuser.core.role.service;

import com.zq.cloud.authuser.core.role.dto.*;
import com.zq.cloud.dto.result.PageResult;

public interface RoleService {


    /**
     * 创建角色
     *
     * @param createDto
     */
    void crate(RoleCreateDto createDto);

    /**
     * 修改角色
     *
     * @param createDto
     */
    void update(RoleUpdateDto createDto);

    /**
     * 分页查询角色
     *
     * @param pageRequestDto
     * @return
     */
    PageResult<RoleVo> findPage(RolePageRequestDto pageRequestDto);

    /**
     * 启停用用户
     *
     * @param roleEnableDto
     */
    void enable(RoleEnableDto roleEnableDto);

    /**
     * 校验用户角色权限
     *
     * @param userId
     */
    void checkRole(Long userId, Long roleId);


    /**
     * 分配角色
     *
     * @param assignDto
     */
    void assign(RoleAssignDto assignDto);

    RoleInfoVo findById(Long id);
}
