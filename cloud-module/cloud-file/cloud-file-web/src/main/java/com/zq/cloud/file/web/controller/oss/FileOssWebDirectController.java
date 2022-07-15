package com.zq.cloud.file.web.controller.oss;

import com.zq.cloud.dto.result.SingleResult;
import com.zq.cloud.file.core.provider.oss.OssStorageProvider;
import com.zq.cloud.file.core.provider.oss.dto.OssPolicyResult;
import com.zq.cloud.utils.BusinessAssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * oss 后端签名前端直传回调模式 controller
 */
@Slf4j
@RestController
@RequestMapping("/biz/file/oss")
public class FileOssWebDirectController {

    @Autowired(required = false)
    private OssStorageProvider ossStorageProvider;


    /**
     * 获取oss前端直传需要的 Policy和回调
     *
     * @return 请求上传的Policy和回调
     */
    @GetMapping(value = "/getSignature")
    public SingleResult<OssPolicyResult> getWebSignature() {
        BusinessAssertUtils.notNull(ossStorageProvider, "非阿里云OSS存储，不支持前端直传模式");
        return SingleResult.from(ossStorageProvider.policy());
    }
}
