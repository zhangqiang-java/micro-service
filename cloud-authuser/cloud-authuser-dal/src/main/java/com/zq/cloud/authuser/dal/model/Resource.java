package com.zq.cloud.authuser.dal.model;

import com.zq.cloud.authuser.dal.enums.ResourceTypeEnum;
import com.zq.cloud.starter.mybatis.model.BaseModel;
import javax.persistence.*;

@Table(name = "resource")
public class Resource extends BaseModel {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 父级资源id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 菜单名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 类型 MENU菜单  INTERFACE 接口
     */
    @Column(name = "type")
    private ResourceTypeEnum type;

    /**
     * 路径
     */
    @Column(name = "url")
    private String url;

    /**
     * 排序显示
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 是否有效 0无效 1有效
     */
    @Column(name = "is_available")
    private Boolean isAvailable;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

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
     * 获取父级资源id
     *
     * @return parent_id - 父级资源id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父级资源id
     *
     * @param parentId 父级资源id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取菜单名称
     *
     * @return name - 菜单名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置菜单名称
     *
     * @param name 菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取类型 MENU菜单  INTERFACE 接口
     *
     * @return type - 类型 MENU菜单  INTERFACE 接口
     */
    public ResourceTypeEnum getType() {
        return type;
    }

    /**
     * 设置类型 MENU菜单  INTERFACE 接口
     *
     * @param type 类型 MENU菜单  INTERFACE 接口
     */
    public void setType(ResourceTypeEnum type) {
        this.type = type;
    }

    /**
     * 获取路径
     *
     * @return url - 路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置路径
     *
     * @param url 路径
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取排序显示
     *
     * @return sort - 排序显示
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序显示
     *
     * @param sort 排序显示
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parentId=").append(parentId);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", url=").append(url);
        sb.append(", sort=").append(sort);
        sb.append(", isAvailable=").append(isAvailable);
        sb.append(", remark=").append(remark);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}