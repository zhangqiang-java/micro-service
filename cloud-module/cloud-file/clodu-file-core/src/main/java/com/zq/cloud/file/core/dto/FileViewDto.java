package com.zq.cloud.file.core.dto;


import com.zq.cloud.dto.DtoBase;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.MediaType;

import java.io.InputStream;

@Data
@ToString(exclude = "inputStream")
public class FileViewDto extends DtoBase {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件是输出流
     */
    private InputStream inputStream;

    /**
     * 是否可以预览
     */
    private Boolean canPreview;


    private MediaType mediaType;
}
