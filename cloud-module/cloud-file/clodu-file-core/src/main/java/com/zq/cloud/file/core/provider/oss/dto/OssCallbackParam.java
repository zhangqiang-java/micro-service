package com.zq.cloud.file.core.provider.oss.dto;


import lombok.Data;

/**
 * oss上传成功后的回调参数
 * @author zq
 */
@Data
public class OssCallbackParam {

    /**
     * 请求的回调地址
     */
    private String callbackUrl;

    /**
     * 回调是传入request中的参数
     */
    private String callbackBody;


}
