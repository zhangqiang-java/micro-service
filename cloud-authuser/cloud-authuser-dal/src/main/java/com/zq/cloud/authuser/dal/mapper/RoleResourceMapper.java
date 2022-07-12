package com.zq.cloud.authuser.dal.mapper;

import com.zq.cloud.authuser.dal.model.RoleResource;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleResourceMapper extends Mapper<RoleResource>, InsertListMapper<RoleResource> {

    default List<RoleResource> findByRoleId(Long roleId) {
        RoleResource roleResource = new RoleResource();
        roleResource.setRoleId(roleId);
        return this.select(roleResource);
    }


    default int deleteByRoleId(Long roleId) {
        RoleResource roleResource = new RoleResource();
        roleResource.setRoleId(roleId);
        return this.delete(roleResource);
    }

}