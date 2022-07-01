package com.zq.cloud.starter.mybatis.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

@RegisterMapper
public interface UpdateListSelectiveMapper<T> {
    @InsertProvider(
            type = UpdateListSelectiveProvider.class,
            method = "dynamicSQL"
    )
    int updateListByPrimaryKeySelective(@Param("list") List<? extends T> record);
}
