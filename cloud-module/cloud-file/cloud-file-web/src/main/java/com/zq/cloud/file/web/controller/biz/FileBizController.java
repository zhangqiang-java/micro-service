package com.zq.cloud.file.web.controller.biz;

import com.zq.cloud.dto.result.ResultBase;
import com.zq.cloud.dto.result.SingleResult;
import com.zq.cloud.file.core.dto.FileResultVo;
import com.zq.cloud.file.core.dto.FileUploadRequestDto;
import com.zq.cloud.file.core.dto.FileViewDto;
import com.zq.cloud.file.core.service.FileService;
import com.zq.cloud.file.web.controller.uitls.DownloadUtils;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 文件服务业务controller
 */
@RestController
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

    /**
     * 下载文件
     *
     * @return
     */
    @GetMapping("/download/{fileId}")
    @ApiOperation("下载文件")
    @SneakyThrows(IOException.class)
    public ResultBase<Void> download(@PathVariable("fileId") Long fileId, HttpServletRequest request, HttpServletResponse response) {
        FileViewDto view = fileService.view(fileId, null, request);
        try {
            DownloadUtils.download(view, request, response);
        } finally {
            view.getInputStream().close();
        }
        return ResultBase.success();
    }


    /**
     * 删除文件
     *
     * @return
     */
    @GetMapping("/delete/{fileId}")
    @ApiOperation("删除文件")
    public ResultBase<Void> delete(@PathVariable("fileId") Long fileId) {
        fileService.delete(fileId);
        return ResultBase.success();
    }

}
