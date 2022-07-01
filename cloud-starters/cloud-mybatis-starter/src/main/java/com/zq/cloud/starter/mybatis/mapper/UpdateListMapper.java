package com.zq.cloud.starter.mybatis.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

@RegisterMapper
public interface UpdateListMapper<T> {
    @InsertProvider(
            type = UpdateListProvider.class,
            method = "dynamicSQL"
    )
    int updateListByPrimaryKey(@Param("list") List<? extends T> recordList);
}
