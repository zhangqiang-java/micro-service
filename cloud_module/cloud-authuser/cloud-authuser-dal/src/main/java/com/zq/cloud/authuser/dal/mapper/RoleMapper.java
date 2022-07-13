package com.zq.cloud.authuser.dal.mapper;

import com.zq.cloud.authuser.dal.dto.RoleSearchDto;
import com.zq.cloud.authuser.dal.model.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {

    default Role findByName(String name) {
        Role role = new Role();
        role.setName(name);
        return this.selectOne(role);
    }

    List<Role> findPage(@Param("dto") RoleSearchDto dto);

}