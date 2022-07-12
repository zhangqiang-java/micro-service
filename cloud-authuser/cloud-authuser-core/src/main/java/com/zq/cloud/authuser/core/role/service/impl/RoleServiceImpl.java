package com.zq.cloud.authuser.core.role.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zq.cloud.authuser.core.role.dto.*;
import com.zq.cloud.authuser.core.role.service.RoleService;
import com.zq.cloud.authuser.core.role.util.RoleConvertDtoUtil;
import com.zq.cloud.authuser.dal.dto.RoleSearchDto;
import com.zq.cloud.authuser.dal.mapper.*;
import com.zq.cloud.authuser.dal.model.*;
import com.zq.cloud.dto.result.PageResult;
import com.zq.cloud.utils.BusinessAssertUtils;
import com.zq.cloud.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    @Transactional
    public void crate(RoleCreateDto createDto) {
        Role byName = roleMapper.findByName(createDto.getName());
        BusinessAssertUtils.isNull(byName, "角色名称已存在");
        Role role = RoleConvertDtoUtil.convertToCrateRole(createDto);
        roleMapper.insert(role);

        this.addRoleResourceList(createDto.getResourceIds(), role.getId());
    }

    @Override
    @Transactional
    public void update(RoleUpdateDto updateDto) {
        Role oldRole = roleMapper.selectByPrimaryKey(updateDto.getId());
        BusinessAssertUtils.notNull(oldRole, "角色不存在", updateDto.getId());

        if (!oldRole.getName().equals(updateDto.getName())) {
            Role byName = roleMapper.findByName(updateDto.getName());
            BusinessAssertUtils.isNull(byName, "角色名称已存在");
        }

        oldRole.setName(updateDto.getName());
        oldRole.setDescription(updateDto.getDescription());
        int updateSize = roleMapper.updateByPrimaryKey(oldRole);
        BusinessAssertUtils.isTrue(updateSize > 0, "角色信息发生变化,请刷新重试");

        roleResourceMapper.deleteByRoleId(updateDto.getId());
        this.addRoleResourceList(updateDto.getResourceIds(), oldRole.getId());
    }


    @Override
    public PageResult<RoleVo> findPage(RolePageRequestDto pageRequestDto) {
        RoleSearchDto searchDto = new RoleSearchDto();
        searchDto.setName(pageRequestDto.getName());
        searchDto.setUserId(pageRequestDto.getUserId());

        Page<Role> page = PageHelper.startPage(pageRequestDto.getPage(), pageRequestDto.getPageSize()).doSelectPage(
                () -> roleMapper.findPage(searchDto));

        List<RoleVo> result = new ArrayList<>(page.getResult().size());
        page.getResult().forEach(role -> {
            result.add(RoleConvertDtoUtil.convertToRoleVo(role));
        });
        return PageResult.from(page.getPageNum(), page.getPageSize(), result);
    }

    @Override
    public void enable(RoleEnableDto roleEnableDto) {
        Role oldRole = roleMapper.selectByPrimaryKey(roleEnableDto.getId());
        BusinessAssertUtils.notNull(oldRole, "角色不存在", roleEnableDto.getId());
        oldRole.setIsAvailable(roleEnableDto.getAvailable());
        if (!oldRole.getIsAvailable().equals(roleEnableDto.getAvailable())) {
            int updateSize = roleMapper.updateByPrimaryKey(oldRole);
            BusinessAssertUtils.isTrue(updateSize > 0, "角色信息发生变化,请刷新重试");
        }
    }

    /**
     * 校验用户是否有对应角色
     *
     * @param userId
     * @param roleId
     */
    @Override
    public void checkRole(Long userId, Long roleId) {
        RoleUser byUserIdAndRoleId = roleUserMapper.findByUserIdAndRoleId(userId, roleId);
        BusinessAssertUtils.notNull(byUserIdAndRoleId, "用户没有该角色权限", roleId);
    }

    @Override
    @Transactional
    public void assign(RoleAssignDto assignDto) {
        User user = userMapper.selectByPrimaryKey(assignDto.getUserId());
        BusinessAssertUtils.notNull(user, "用户不存在", assignDto.getUserId());
        roleUserMapper.deleteByUserId(assignDto.getUserId());

        if (!CollectionUtils.isEmpty(assignDto.getRoleIds())) {
            List<RoleUser> roleUserList = new ArrayList<>(assignDto.getRoleIds().size());
            for (Long roleId : assignDto.getRoleIds()) {
                RoleUser roleUser = new RoleUser();
                roleUser.setId(SnowFlakeUtils.getNextId());
                roleUser.setUserId(assignDto.getUserId());
                roleUser.setRoleId(roleId);
                roleUserList.add(roleUser);
            }
            roleUserMapper.insertList(roleUserList);
        }
    }

    /**
     * 查询角色信息
     *
     * @param id
     * @return
     */
    @Override
    public RoleInfoVo findById(Long id) {
        Role role = roleMapper.selectByPrimaryKey(id);
        List<Resource> byRoleId = resourceMapper.findByRoleId(role.getId());
        return RoleConvertDtoUtil.convertToRoleInfoVo(role, byRoleId);
    }


    /**
     * 添加资源和角色的关联关系
     *
     * @param resourceIds
     * @param roleId
     */
    private void addRoleResourceList(List<Long> resourceIds, Long roleId) {
        List<RoleResource> roleResourceList = new ArrayList<>(resourceIds.size());
        resourceIds.forEach(resourceId -> {
                    RoleResource roleResource = new RoleResource();
                    roleResource.setId(SnowFlakeUtils.getNextId());
                    roleResource.setRoleId(roleId);
                    roleResource.setResourceId(resourceId);
                    roleResourceList.add(roleResource);
                }
        );
        roleResourceMapper.insertList(roleResourceList);
    }
}
