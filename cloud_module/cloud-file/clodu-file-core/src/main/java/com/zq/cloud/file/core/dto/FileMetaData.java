package com.zq.cloud.file.core.dto;

import com.zq.cloud.dto.DtoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文件元数据信息
 */
@Data
public class FileMetaData extends DtoBase {

    @ApiModelProperty(value = "原始文件名称", required = true, example = "file.txt")
    private String originalFileName;

    @ApiModelProperty(value = "文件大小", required = true, example = "1024")
    private long fileSize;

    @ApiModelProperty(value = "类型", required = true, example = "txt")
    private String fileType;

}
