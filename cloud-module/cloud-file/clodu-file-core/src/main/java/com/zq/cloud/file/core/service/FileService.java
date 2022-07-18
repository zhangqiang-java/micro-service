package com.zq.cloud.file.core.service;

import com.zq.cloud.file.core.dto.FileResultVo;
import com.zq.cloud.file.core.dto.FileUploadRequestDto;
import com.zq.cloud.file.core.dto.FileViewDto;
import com.zq.cloud.file.facade.dto.FileServiceQueryDto;
import com.zq.cloud.file.facade.dto.FileServiceResult;
import com.zq.cloud.file.facade.dto.FileServiceUploadRequestDto;

import javax.servlet.http.HttpServletRequest;

public interface FileService {

    /**
     * 上传文件
     *
     * @param uploadRequestDto
     * @return
     */
    FileResultVo upload(FileUploadRequestDto uploadRequestDto);


    /**
     * 查看文件
     *
     * @param fileId
     * @param authorityId
     * @param request
     * @return
     */
    FileViewDto view(Long fileId, String authorityId, HttpServletRequest request);

    /**
     * 删除文件
     *
     * @param fileId
     */
    void delete(Long fileId);


    /**
     * 查找文件
     *
     * @param queryDto
     * @return
     */
    FileServiceResult serviceFindFile(FileServiceQueryDto queryDto);


    /**
     * 通过byte数组上传文件
     *
     * @param uploadRequestDto
     * @return
     */
    void serviceUpload(FileServiceUploadRequestDto uploadRequestDto);


}
