package com.zq.cloud.file.facade.dto;

import com.zq.cloud.dto.DtoBase;
import lombok.Data;

/**
 * 服务调用响应
 */
@Data
public class FileServiceResult extends DtoBase {

    /**
     * 文件id
     */
    private Long id;

    /**
     * 文件名称
     */
    private String fileName;


    /**
     * 文件查看地址
     */
    private String previewUrl;


    /**
     * 系统code
     */
    private String systemCode;


    /**
     * 业务code码
     */
    private String bizCode;


    /**
     * 文件数据
     */
    private byte[] content;
}
