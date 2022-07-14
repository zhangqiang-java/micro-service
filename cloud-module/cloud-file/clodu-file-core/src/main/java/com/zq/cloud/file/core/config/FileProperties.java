package com.zq.cloud.file.core.config;

import com.zq.cloud.file.core.enums.StorageProviderType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("zq.file.storage")
public class FileProperties {

    /**
     * 文件服务提供方  目前支持fastDfs，阿里云oss，本地存储， 默认oss
     */
    private String provider = StorageProviderType.OSS.code();

    /**
     * 访问域名
     */
    private String previewDomainName;


    /**
     * 预览地址失效时间 默认30分钟 单位秒
     */
    private Integer validityTime = 30 * 60;

}
