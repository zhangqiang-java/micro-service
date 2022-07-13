package com.zq.cloud.file.core.provider.oss.listener;

import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * oss 下载文件事件监听
 */
@Slf4j
@Data
@AllArgsConstructor
public class OssGetProgressListener implements ProgressListener {

    private String key;

    @Override
    public void progressChanged(ProgressEvent progressEvent) {
        ProgressEventType eventType = progressEvent.getEventType();
        switch (eventType) {
            case TRANSFER_STARTED_EVENT:
                log.info("开始下载文件key:{}", key);
                break;
            case RESPONSE_CONTENT_LENGTH_EVENT:
                log.info("下载文件key:{}，文件大小为:{}k", key, progressEvent.getBytes() / 1024);
                break;
            case TRANSFER_COMPLETED_EVENT:
                log.info("下载文件key:{}成功", key);
                break;
            case TRANSFER_FAILED_EVENT:
                log.info("下载文件,key:{}失败", key);
                break;
            default:
                break;
        }
    }
}
