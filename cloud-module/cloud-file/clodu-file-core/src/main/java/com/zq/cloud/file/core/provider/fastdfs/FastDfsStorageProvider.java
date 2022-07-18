package com.zq.cloud.file.core.provider.fastdfs;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.zq.cloud.file.core.config.FastDfsStorageProperties;
import com.zq.cloud.file.core.provider.StorageProvider;
import com.zq.cloud.file.core.provider.fastdfs.dto.DownloadByInputStream;
import com.zq.cloud.file.dal.model.FileMetadata;
import com.zq.cloud.utils.BusinessAssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

/**
 * fastDfs 存储
 */
@Slf4j
public class FastDfsStorageProvider implements StorageProvider {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private FastDfsStorageProperties fastDfsStorageProperties;


    @Override
    public void upload(InputStream inputStream, FileMetadata metaData) {
        try {
            StorePath storePath = storageClient.uploadFile(fastDfsStorageProperties.getGroupName(), inputStream, inputStream.available(), "fileExt");
            metaData.setRelativePath(storePath.getFullPath());
        } catch (Exception e) {
            log.error("fastDfs 上传文件异常", e);
            BusinessAssertUtils.fail("上传文件异常");
        }
    }

    @Override
    public InputStream get(String providerId) {
        StorePath storePath = StorePath.parseFromUrl(providerId);
        return storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByInputStream());
    }

    @Override
    public void delete(String providerId) {
        storageClient.deleteFile(providerId);
    }


}
