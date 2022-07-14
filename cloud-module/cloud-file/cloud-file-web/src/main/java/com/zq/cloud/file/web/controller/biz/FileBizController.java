package com.zq.cloud.file.web.controller.biz;

import com.zq.cloud.dto.result.SingleResult;
import com.zq.cloud.file.core.dto.FileResultVo;
import com.zq.cloud.file.core.dto.FileUploadRequestDto;
import com.zq.cloud.file.core.service.FileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 文件服务业务controller
 */
@ResponseBody
@RequestMapping("/biz/file")
public class FileBizController {
    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     *
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public SingleResult<FileResultVo> upload(@Validated @RequestBody FileUploadRequestDto uploadRequestDto) {
        return SingleResult.from(fileService.upload(uploadRequestDto));
    }
}
