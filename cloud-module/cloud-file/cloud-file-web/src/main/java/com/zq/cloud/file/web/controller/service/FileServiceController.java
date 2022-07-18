package com.zq.cloud.file.web.controller.service;

import com.zq.cloud.dto.result.ResultBase;
import com.zq.cloud.dto.result.SingleResult;
import com.zq.cloud.file.core.dto.FileUploadRequestDto;
import com.zq.cloud.file.core.service.FileService;
import com.zq.cloud.file.facade.FileClient;
import com.zq.cloud.file.facade.dto.FileServiceQueryDto;
import com.zq.cloud.file.facade.dto.FileServiceResult;
import com.zq.cloud.file.facade.dto.FileServiceUploadRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 提供给微服务
 */
@RestController
@RequestMapping("/service/file")
public class FileServiceController implements FileClient {
    @Autowired
    private FileService fileService;


    /**
     * 查找文件信息
     *
     * @param queryDto
     * @return
     */
    public SingleResult<FileServiceResult> findFile(@RequestBody @Valid FileServiceQueryDto queryDto) {
        return SingleResult.from(fileService.serviceFindFile(queryDto));
    }


    /**
     * 上传文件
     *
     * @param uploadRequestDto
     * @return
     */
    public ResultBase<Void> upload(@RequestBody @Valid FileServiceUploadRequestDto uploadRequestDto) {
        fileService.serviceUpload(uploadRequestDto);
        return ResultBase.success();
    }


    /**
     * 上传文件
     *
     * @param fileId
     * @return
     */
    public ResultBase<Void> delete(Long fileId) {
        fileService.delete(fileId);
        return ResultBase.success();
    }
}
