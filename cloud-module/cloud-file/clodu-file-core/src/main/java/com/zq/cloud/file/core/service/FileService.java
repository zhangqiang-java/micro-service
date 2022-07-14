package com.zq.cloud.file.core.service;

import com.zq.cloud.file.core.dto.FileResultVo;
import com.zq.cloud.file.core.dto.FileUploadRequestDto;

public interface FileService {

    FileResultVo upload(FileUploadRequestDto uploadRequestDto);


}
