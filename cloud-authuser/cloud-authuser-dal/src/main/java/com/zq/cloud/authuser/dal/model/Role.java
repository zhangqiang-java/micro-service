package com.zq.cloud.authuser.dal.model;

import com.zq.cloud.starter.mybatis.model.BaseModel;
import javax.persistence.*;

@Table(name = "role")
public class Role extends BaseModel {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 角色显示名称
     */
    @Column(name = "description")
    private String description;

    /**
     * 是否有效 0无效 1有效
     */
    @Column(name = "is_available")
    private Boolean isAvailable;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取角色显示名称
     *
     * @return description - 角色显示名称
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置角色显示名称
     *
     * @param description 角色显示名称
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取是否有效 0无效 1有效
     *
     * @return is_available - 是否有效 0无效 1有效
     */
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     * 设置是否有效 0无效 1有效
     *
     * @param isAvailable 是否有效 0无效 1有效
     */
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", isAvailable=").append(isAvailable);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}