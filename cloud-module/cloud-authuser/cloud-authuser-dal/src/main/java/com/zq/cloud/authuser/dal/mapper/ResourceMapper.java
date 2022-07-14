package com.zq.cloud.authuser.dal.mapper;

import com.zq.cloud.authuser.dal.enums.ResourceTypeEnum;
import com.zq.cloud.authuser.dal.model.Resource;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ResourceMapper extends Mapper<Resource> {

    default List<Resource> findAllInterface() {
        Resource resource = new Resource();
        resource.setType(ResourceTypeEnum.INTERFACE);
        return this.select(resource);
    }


    List<Resource> findResourceByUserIdAndType(@Param("userId") Long userId, @Param("type") String type, @Param("isAvailable")Boolean isAvailable);


    List<Resource> findByRoleId(Long roleId);

    List<Resource> findByParentIdSet(@Param("parentIds") Collection<Long> menuResourceIds, @Param("type") String type, @Param("isAvailable")Boolean isAvailable);
}