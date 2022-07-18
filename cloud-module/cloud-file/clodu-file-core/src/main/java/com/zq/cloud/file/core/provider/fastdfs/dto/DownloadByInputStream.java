package com.zq.cloud.file.core.provider.fastdfs.dto;

import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;

import java.io.IOException;
import java.io.InputStream;

/**
 * 直接获取文件流
 */
public class DownloadByInputStream implements DownloadCallback<InputStream> {
    @Override
    public InputStream recv(InputStream ins) throws IOException {
        return ins;
    }
}
