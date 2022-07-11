package com.zq.cloud.authuser.dal.mapper;

import com.zq.cloud.authuser.dal.model.Resource;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ResourceMapper extends Mapper<Resource> {


    List<Resource> findResourceByUserIdAndType(@Param("userId") Long userId, @Param("type") String type);
}