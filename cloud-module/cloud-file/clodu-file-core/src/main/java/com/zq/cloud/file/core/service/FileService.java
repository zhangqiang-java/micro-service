package com.zq.cloud.file.core.service;

import com.zq.cloud.file.core.dto.FileResultVo;
import com.zq.cloud.file.core.dto.FileUploadRequestDto;
import com.zq.cloud.file.core.dto.FileViewDto;

import javax.servlet.http.HttpServletRequest;

public interface FileService {

    FileResultVo upload(FileUploadRequestDto uploadRequestDto);


    FileViewDto view(Long fileId, String authorityId, HttpServletRequest request);

    void delete(Long fileId);
}
