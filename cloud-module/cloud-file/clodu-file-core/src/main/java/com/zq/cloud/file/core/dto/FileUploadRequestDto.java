package com.zq.cloud.file.core.dto;

import com.zq.cloud.dto.DtoBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * 上传文件请求
 */
@Data
@ApiModel
public class FileUploadRequestDto extends DtoBase {

    @NotNull(message = "请选择上传的文件")
    @ApiModelProperty(value = "文件", required = true)
    private MultipartFile file;

    /**
     * 自定义文件名
     */
    @ApiModelProperty(value = "自定义文件名")
    private String customFileName;

    /**
     * 默认压缩
     */
    @ApiModelProperty(value = "是否需要压缩")
    private Boolean compress = Boolean.TRUE;

    /**
     * 预览文件url默认有时效性
     */
    @ApiModelProperty(value = "预览文件url时效性")
    private Boolean validityEnable = Boolean.TRUE;


}
