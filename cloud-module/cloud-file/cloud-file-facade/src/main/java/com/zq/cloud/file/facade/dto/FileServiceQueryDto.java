package com.zq.cloud.file.facade.dto;

import com.zq.cloud.dto.DtoBase;
import lombok.Data;

@Data
public class FileServiceQueryDto extends DtoBase {

    /**
     * 文件id
     */
    private Long id;


    /**
     * 是否需要文件数据 默认不不需要
     */
    private Boolean needBytes = Boolean.FALSE;


    /**
     * 查看地址时效性 微服务调用默认不做过期处理
     */
    private Boolean validityEnable = Boolean.FALSE;
}
