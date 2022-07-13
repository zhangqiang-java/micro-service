package com.zq.cloud.file.core.provider.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.google.common.base.Strings;
import com.zq.cloud.file.core.config.OssClientBuilderConfiguration;
import com.zq.cloud.file.core.dto.FileMetaData;
import com.zq.cloud.file.core.provider.StorageProvider;
import com.zq.cloud.file.core.provider.oss.listener.OssUploadProgressListener;
import com.zq.cloud.utils.BusinessAssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 阿里云oss提供存储服务
 */
@Slf4j
public class OssStorageProvider implements StorageProvider {

    @Autowired
    private OSS ossClient;

    @Autowired
    private OssClientBuilderConfiguration ossConfiguration;

    @Override
    public void upload(InputStream inputStream, FileMetaData metaData) {
        String storePath = this.createStorePath(metaData);
        this.uploadFile(storePath, inputStream);
    }


    /**
     * 上传
     *
     * @param storePath   oss文件存储路径
     * @param inputStream
     */
    private PutObjectResult uploadFile(String storePath, InputStream inputStream) {
        PutObjectResult putObjectResult = null;
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfiguration.getBucketName(), storePath, inputStream).withProgressListener(new OssUploadProgressListener(storePath));
            putObjectResult = ossClient.putObject(putObjectRequest);
        } catch (Exception e) {
            log.error("oss 上传文件:" + storePath + "异常", e);
            BusinessAssertUtils.fail("oss 上传文件异常");
        }
        return putObjectResult;
    }

    /**
     * 生成文件存储路径
     *
     * @param metaData
     * @return
     */
    private String createStorePath(FileMetaData metaData) {
        String baseFilePath = ossConfiguration.getBaseFilePath();
        if (baseFilePath.endsWith("/")) {
            baseFilePath = baseFilePath.substring(0, baseFilePath.length() - 1);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
        String storePath = baseFilePath + sdf.format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "");
        if (StringUtils.isNotBlank(metaData.getFileType())) {
            return storePath + "." + metaData.getFileType();
        }
        return storePath;
    }


}
