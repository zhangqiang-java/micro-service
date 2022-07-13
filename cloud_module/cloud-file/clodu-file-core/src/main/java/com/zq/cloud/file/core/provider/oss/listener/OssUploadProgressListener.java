package com.zq.cloud.file.core.provider.oss.listener;

import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * oss 上传文件事件监听
 */
@Slf4j
@Data
@AllArgsConstructor
public class OssUploadProgressListener implements ProgressListener {

    private String key;

    @Override
    public void progressChanged(ProgressEvent progressEvent) {
        ProgressEventType eventType = progressEvent.getEventType();
        switch (eventType) {
            case TRANSFER_STARTED_EVENT:
                log.info("开始上传文件key:{}", key);
                break;
            case TRANSFER_COMPLETED_EVENT:
                log.info("上传文件key:{}成功", key);
                break;
            case TRANSFER_FAILED_EVENT:
                log.info("上传文件,key:{}失败", key);
                break;
            case REQUEST_BYTE_TRANSFER_EVENT://进度条监听
            default:
                break;
        }
    }
}
