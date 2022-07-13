package com.zq.cloud.file.core.dto;

import com.zq.cloud.dto.DtoBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文件响应
 */
@Data
@ApiModel
public class FileResultVo extends DtoBase {
    @ApiModelProperty(value = "文件ID", example = "123456")
    private Long id;
    @ApiModelProperty(value = "文件名", example = "123.jpg")
    private String fileName;
    @ApiModelProperty(value = "文件预览地址", example = "http://ip:port/view.jpg?authorityId=12345")
    private String viewUrl;
}
