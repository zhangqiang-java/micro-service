package com.zq.cloud.file.core;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.DataRedundancyType;
import com.aliyun.oss.model.StorageClass;
import com.zq.cloud.file.core.config.FileProperties;
import com.zq.cloud.file.core.config.OssClientBuilderConfiguration;
import com.zq.cloud.file.core.provider.StorageProvider;
import com.zq.cloud.file.core.provider.oss.OssStorageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({FileProperties.class, OssClientBuilderConfiguration.class})
@Slf4j
public class FileAutoConfiguration {

    /**
     * oss 存储服务
     */
    @Configuration
    @ConditionalOnProperty(value = "zq.file.storage.provider", havingValue = "oss", matchIfMissing = true)
    static public class OssStorageAutoConfiguration {
        @Bean
        public StorageProvider OssStorageProvider() {
            return new OssStorageProvider();
        }

        @Bean
        @RefreshScope
        public OSSClient ossClientBuild(OssClientBuilderConfiguration ossClientConfiguration) {
            OSSClient ossClient = new OSSClient("http://" + ossClientConfiguration.getEndpoint(),
                    new DefaultCredentialProvider(ossClientConfiguration.getAccessKeyId(), ossClientConfiguration.getAccessKeySecret())
                    , ossClientConfiguration);
            if (!ossClient.doesBucketExist(ossClientConfiguration.getBucketName())) {
                log.info("您的Bucket不存在，创建Bucket： {} ", ossClientConfiguration.getBucketName());
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(ossClientConfiguration.getBucketName());
                // 设置存储空间的存储类型为标准存储。
                createBucketRequest.setStorageClass(StorageClass.Standard);
                // 数据容灾类型默认为本地冗余存储
                createBucketRequest.setDataRedundancyType(DataRedundancyType.ZRS);
                // 设置权限为私有。
                createBucketRequest.setCannedACL(CannedAccessControlList.Private);
                // 创建Bucket。
                ossClient.createBucket(createBucketRequest);
            }
            return ossClient;
        }
    }
}
