package com.zq.cloud.starter.mybatis.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tk.mybatis.mapper.annotation.Version;

import javax.persistence.Column;

/**
 * 增加乐观锁Version字段, 代码生成参考配置：
 * <p><table tableName="table_name" >
 * <property name="rootClass" value="com.xxx.BaseVersionModel"/>
 * <generatedKey column="id" sqlStatement="JDBC"/>
 * </table>
 * </p>
 */
@Getter
@Setter
@ToString(callSuper = true)
public class BaseVersionModel extends BaseModel {
    /**
     * 乐观锁version
     */
    @Column(name = "version", insertable = false)
    @Version
    protected Integer version;
}
