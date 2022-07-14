package com.zq.cloud.authuser.dal.mapper;

import com.zq.cloud.authuser.dal.model.RoleUser;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface RoleUserMapper extends Mapper<RoleUser> , InsertListMapper<RoleUser> {


    default RoleUser findByUserIdAndRoleId(Long userId, Long roleID) {
        RoleUser roleUser = new RoleUser();
        roleUser.setRoleId(roleID);
        roleUser.setUserId(userId);
        return this.selectOne(roleUser);
    }


    default List<RoleUser> findByUserId(Long userId) {
        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(userId);
        return this.select(roleUser);
    }


    default Set<Long> findRoleIdsByUserId(Long userId) {
        List<RoleUser> byUserId = this.findByUserId(userId);
        if (CollectionUtils.isEmpty(byUserId)) {
            return new HashSet<>();
        }
        return byUserId.stream().map(RoleUser::getRoleId).collect(Collectors.toSet());
    }


    default int deleteByUserId(Long userId){
        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(userId);
        return this.delete(roleUser);
    }
}