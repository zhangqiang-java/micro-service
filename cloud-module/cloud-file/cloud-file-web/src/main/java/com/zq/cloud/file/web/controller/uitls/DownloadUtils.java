package com.zq.cloud.file.web.controller.uitls;


import com.zq.cloud.file.core.dto.FileViewDto;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DownloadUtils {

    public static void download(FileViewDto fileViewDto,HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.addHeader(
                "Content-Disposition",
                "attachment;filename=" + fileName(request, fileViewDto.getFileName()));
        response.addHeader("Content-Length", "" + fileViewDto.getFileSize());
        response.setContentType("application/octet-stream");
        try {
            StreamUtils.copy(fileViewDto.getInputStream(), response.getOutputStream());
        } finally {
            fileViewDto.getInputStream().close();
        }
    }

    public static String fileName(HttpServletRequest request, String fileName) throws IOException {
        String userAgent = request.getHeader("User-Agent").toUpperCase();

        if (userAgent.contains("FIREFOX")) {
            // firefox浏览器
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        } else if (userAgent.contains("MSIE") || userAgent.contains("TRIDENT") || userAgent.contains("EDGE")) {
            // IE浏览器
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        } else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {
            // 谷歌
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
        return fileName;
    }
}
