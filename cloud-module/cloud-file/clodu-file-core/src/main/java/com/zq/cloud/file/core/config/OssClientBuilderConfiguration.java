package com.zq.cloud.file.core.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "zq.file.oss")
public class OssClientBuilderConfiguration extends ClientBuilderConfiguration {

    /**
     * 访问域名
     */
    private String endpoint;

    /**
     * 访问密钥id
     */
    private String accessKeyId;

    /**
     * 访问密钥
     */
    private String accessKeySecret;

    /**
     * 存储空间
     */
    private String bucketName;

    /**
     * 签名有效时间 （单位秒）
     */
    private Integer expireTime = 30 * 60;

    /**
     * oss存储文件根目录
     */
    private String baseFilePath = "data/common";
}
