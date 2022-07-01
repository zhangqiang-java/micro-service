package com.zq.cloud.starter.mybatis.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.util.Date;


/**
 * 表结构中默认有create_time,update_time两个字段，代码中不可插入不可修改，由数据库自动生成
 */
@Getter
@Setter
@ToString
public class BaseModel {

    /**
     * 新增时间
     */
    @Column(name = "create_time", insertable = false, updatable = false)
    protected Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time", insertable = false, updatable = false)
    protected Date updateTime;
}
