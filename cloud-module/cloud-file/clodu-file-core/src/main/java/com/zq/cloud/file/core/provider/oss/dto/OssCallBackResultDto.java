package com.zq.cloud.file.core.provider.oss.dto;

import com.zq.cloud.dto.DtoBase;
import lombok.Data;

@Data
public class OssCallBackResultDto extends DtoBase {

    /**
     * 文件名
     */
    private String fileName;


    /**
     * 预览文件url默认有时效性
     */
    private Boolean validityEnable = Boolean.TRUE;

    /**
     * 系统code
     */
    private String systemCode;


    /**
     * 业务code码
     */
    private String bizCode;

    /**
     * 文件后缀
     */
    private String fileExt;

    /**
     * 存储路径
     */
    private String relativePath;
}
