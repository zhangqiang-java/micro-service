package com.zq.cloud.file.core.provider.oss.dto;


import lombok.Data;

/**
 *  服务端签名 前端直传 响应的Policy和回调设置
 *
 * @author zq
 */
@Data
public class OssPolicyResult {

    /**
     * 访问身份验证中用到用户标识
     */
    private String OSSAccessKeyId;

    /**
     * 用户表单上传的策略,经过base64编码过的字符串
     */
    private String policy;

    /**
     * 对policy签名后的字符串
     */
    private String signature;


    /**
     * oss对外服务的访问域名
     */
    private String host;


    /**
     * 存储目录
     *
     */
    private String directory;


    /**
     * 回调参数
     */
    private OssCallbackParam callback;


}
