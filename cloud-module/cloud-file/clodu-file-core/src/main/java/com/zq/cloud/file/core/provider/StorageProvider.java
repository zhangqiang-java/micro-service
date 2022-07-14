package com.zq.cloud.file.core.provider;

import com.zq.cloud.file.dal.model.FileMetadata;

import java.io.InputStream;

/**
 * 服务提供者
 */
public interface StorageProvider {

    /**
     * 上传文件
     *
     * @param inputStream
     * @param metaData
     */
    void upload(InputStream inputStream, FileMetadata metaData);
}
