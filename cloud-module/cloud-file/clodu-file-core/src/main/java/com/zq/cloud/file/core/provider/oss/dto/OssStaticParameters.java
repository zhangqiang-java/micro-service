package com.zq.cloud.file.core.provider.oss.dto;


/**
 *
 * oss业务需要的 静态不变参数
 *
 * @author zq
 */
public class OssStaticParameters {

    /**
     * header  获取body长度
     */
    public final static String CONTENT_LENGTH ="content-length";

    /**
     * header 获取授权码
     */
    public final static String OSS_AUTHORIZATION ="Authorization";


    /**
     * header 获取public key (OSS定义的获取publicKey的header)
     *
     */
    public final static String X_OSS_PUB_KEY_URL ="x-oss-pub-key-url";


    /**
     * 校验public key 为oss颁发的规则1
     */
    public final static String OSS_CHECK_RULE1 ="http://gosspublic.alicdn.com/";

    /**
     * 校验public key 为oss颁发的规则2
     */
    public final static String OSS_CHECK_RULE2 ="https://gosspublic.alicdn.com/";


    /**
     * oss 自带的公共回调的参数
     */
    public final static String BASE_CALL_BACK_BODY="bucket=${bucket}&object=${object}&etag=${etag}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}&format=${imageInfo.format}";






}
