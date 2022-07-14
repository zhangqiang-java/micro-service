package com.zq.cloud.file.dal.model;

import com.zq.cloud.file.dal.enums.FileType;
import com.zq.cloud.starter.mybatis.model.BaseVersionModel;
import javax.persistence.*;

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
     * 数据类型DIR:目录;IMAGE:图片;VIDEO:视频;AUDIO:音频;DOC:文档;OTHER:其他
     */
    @Column(name = "file_type")
    private FileType fileType;

    /**
     * 文件相对路径
     */
    @Column(name = "relative_path")
    private String relativePath;

    /**
     * 缩略图相对路径
     */
    @Column(name = "enlarge_path")
    private String enlargePath;

    /**
     * 放大图相对路径
     */
    @Column(name = "thumbnail_path")
    private String thumbnailPath;

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
     * 获取数据类型DIR:目录;IMAGE:图片;VIDEO:视频;AUDIO:音频;DOC:文档;OTHER:其他
     *
     * @return file_type - 数据类型DIR:目录;IMAGE:图片;VIDEO:视频;AUDIO:音频;DOC:文档;OTHER:其他
     */
    public FileType getFileType() {
        return fileType;
    }

    /**
     * 设置数据类型DIR:目录;IMAGE:图片;VIDEO:视频;AUDIO:音频;DOC:文档;OTHER:其他
     *
     * @param fileType 数据类型DIR:目录;IMAGE:图片;VIDEO:视频;AUDIO:音频;DOC:文档;OTHER:其他
     */
    public void setFileType(FileType fileType) {
        this.fileType = fileType;
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

    /**
     * 获取缩略图相对路径
     *
     * @return enlarge_path - 缩略图相对路径
     */
    public String getEnlargePath() {
        return enlargePath;
    }

    /**
     * 设置缩略图相对路径
     *
     * @param enlargePath 缩略图相对路径
     */
    public void setEnlargePath(String enlargePath) {
        this.enlargePath = enlargePath;
    }

    /**
     * 获取放大图相对路径
     *
     * @return thumbnail_path - 放大图相对路径
     */
    public String getThumbnailPath() {
        return thumbnailPath;
    }

    /**
     * 设置放大图相对路径
     *
     * @param thumbnailPath 放大图相对路径
     */
    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
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
        sb.append(", fileType=").append(fileType);
        sb.append(", relativePath=").append(relativePath);
        sb.append(", enlargePath=").append(enlargePath);
        sb.append(", thumbnailPath=").append(thumbnailPath);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}