package com.zq.cloud.file.web.controller.anon;

import com.zq.cloud.dto.result.ResultBase;
import com.zq.cloud.file.core.dto.FileViewDto;
import com.zq.cloud.file.core.service.FileService;
import com.zq.cloud.file.web.controller.uitls.DownloadUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 文件服务无权限 通用Controller
 */
@Slf4j
@RestController
@RequestMapping("/anon/file")
public class FileAnonController {


    @Autowired
    private FileService fileService;

    /**
     * 预览文件或下载
     *
     * @param fileId
     * @param authorityId
     * @param request
     * @param response
     */
    @SneakyThrows(IOException.class)
    @GetMapping("/view.*")
    public ResultBase<Void> previewOrDownload(Long fileId, String authorityId, HttpServletRequest request, HttpServletResponse response) {
        FileViewDto viewDto = fileService.view(fileId, authorityId, request);

        try {
            //可以预览
            if (viewDto.getCanPreview()) {
                response.addHeader("Content-Length", viewDto.getFileSize().toString());
                response.setContentType(viewDto.getMediaType().toString());
                StreamUtils.copy(viewDto.getInputStream(), response.getOutputStream());
            } else {
                log.info("不支持预览,直接进行下载");
                DownloadUtils.download(viewDto, request, response);
            }
        } finally {
            viewDto.getInputStream().close();
        }
        return ResultBase.success();
    }
}
