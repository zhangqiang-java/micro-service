package com.zq.cloud.file.core.provider;

import com.zq.cloud.file.core.dto.FileMetaData;

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
    void upload(InputStream inputStream, FileMetaData metaData);
}
