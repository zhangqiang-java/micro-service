package com.zq.cloud.file.dal.model;

import com.zq.cloud.starter.mybatis.model.BaseVersionModel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "file_metadata")
public class FileMetadata extends BaseVersionModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件大小
     */
    @Column(name = "file_size")
    private Long fileSize;

    /**
     * 文件后缀
     */
    @Column(name = "file_ext")
    private String fileExt;


    /**
     * 文件存储相对路径
     */
    @Column(name = "relative_path")
    private String relativePath;


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
     * 获取文件名称
     *
     * @return file_name - 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名称
     *
     * @param fileName 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取文件大小
     *
     * @return file_size - 文件大小
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * 设置文件大小
     *
     * @param fileSize 文件大小
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 获取文件后缀
     *
     * @return file_ext - 文件后缀
     */
    public String getFileExt() {
        return fileExt;
    }

    /**
     * 设置文件后缀
     *
     * @param fileExt 文件后缀
     */
    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }


    /**
     * 获取文件相对路径
     *
     * @return relative_path - 文件相对路径
     */
    public String getRelativePath() {
        return relativePath;
    }

    /**
     * 设置文件相对路径
     *
     * @param relativePath 文件相对路径
     */
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fileName=").append(fileName);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", fileExt=").append(fileExt);
        sb.append(", relativePath=").append(relativePath);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}