package com.zq.cloud.file.dal.model;

import com.zq.cloud.starter.mybatis.model.BaseVersionModel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "file")
public class File extends BaseVersionModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;


    /**
     * 系统code码
     */
    @Column(name = "system_code")
    private String systemCode;

    /**
     * 业务code码
     */
    @Column(name = "biz_code")
    private String bizCode;

    /**
     * 文件元数据信息
     */
    @Column(name = "metadata_id")
    private Long metadataId;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * 获取系统code码
     *
     * @return system_code - 系统code码
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * 设置系统code码
     *
     * @param systemCode 系统code码
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * 获取业务code码
     *
     * @return biz_code - 业务code码
     */
    public String getBizCode() {
        return bizCode;
    }

    /**
     * 设置业务code码
     *
     * @param bizCode 业务code码
     */
    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    /**
     * 获取文件元数据信息
     *
     * @return metadata_id - 文件元数据信息
     */
    public Long getMetadataId() {
        return metadataId;
    }

    /**
     * 设置文件元数据信息
     *
     * @param metadataId 文件元数据信息
     */
    public void setMetadataId(Long metadataId) {
        this.metadataId = metadataId;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", systemCode=").append(systemCode);
        sb.append(", bizCode=").append(bizCode);
        sb.append(", metadataId=").append(metadataId);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}