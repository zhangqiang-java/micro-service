package com.zq.cloud.file.web.controller.oss;

import com.zq.cloud.dto.result.ResultBase;
import com.zq.cloud.file.core.provider.oss.OssStorageProvider;
import com.zq.cloud.utils.BusinessAssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * oss前端直传回调
 */
@Slf4j
@RestController
@RequestMapping("/anon/oss/file")
public class FileOssWebCallBackController {

    @Autowired(required = false)
    private OssStorageProvider ossStorageProvider;


    /**
     * oss上传回调
     *
     * @return 响应给oss的回调信息，由oss响应给前端
     */
    @PostMapping(value = "/callback")
    @ResponseBody
    public ResultBase<Void> callback(HttpServletRequest request) {
        BusinessAssertUtils.notNull(ossStorageProvider, "非阿里云OSS存储，不支持前端直传模式");
        ossStorageProvider.callback(request);
        return ResultBase.success();
    }


}
