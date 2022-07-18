package com.zq.cloud.file.facade.dto;

import com.zq.cloud.dto.DtoBase;
import lombok.Data;

/**
 * 上传文件请求
 */
@Data
public class FileServiceUploadRequestDto extends DtoBase {

    /**
     * 文件数据
     */
    private byte[] content;

    /**
     * 自定义文件名
     */
    private String customFileName;


    /**
     * 系统code
     */

    private String systemCode;


    /**
     * 业务code码
     */
    private String bizCode;


}
