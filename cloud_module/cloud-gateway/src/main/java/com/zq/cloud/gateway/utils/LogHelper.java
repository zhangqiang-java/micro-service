package com.zq.cloud.gateway.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class LogHelper {

    /**
     * 根据MediaType获取字符集，如果获取不到，则默认返回<tt>UTF_8</tt>
     *
     * @param mediaType MediaType
     * @return Charset
     */
    public static Charset getMediaTypeCharset(@Nullable MediaType mediaType) {
        if (Objects.nonNull(mediaType) && mediaType.getCharset() != null) {
            return mediaType.getCharset();
        } else {
            return StandardCharsets.UTF_8;
        }
    }


    /**
     * 判断是否是上传文件
     * 注：未全部穷举
     * @param mediaType MediaType
     * @return Boolean
     */
    public static boolean isFileType(@Nullable MediaType mediaType) {
        if (Objects.isNull(mediaType)) {
            return false;
        }
        return mediaType.equalsTypeAndSubtype(MediaType.MULTIPART_FORM_DATA)
                || mediaType.equalsTypeAndSubtype(MediaType.MULTIPART_MIXED)
                || mediaType.equalsTypeAndSubtype(MediaType.MULTIPART_RELATED)
                || mediaType.equalsTypeAndSubtype(MediaType.IMAGE_GIF)
                || mediaType.equalsTypeAndSubtype(MediaType.IMAGE_JPEG)
                || mediaType.equalsTypeAndSubtype(MediaType.IMAGE_PNG)
                || mediaType.equalsTypeAndSubtype(MediaType.APPLICATION_OCTET_STREAM)
                || mediaType.equalsTypeAndSubtype(MediaType.APPLICATION_PDF);
    }


    /**
     * 字节数据转换为字符串 ，注：文件类型不会转换，返回固定值”[文件类型不打印]“
     *
     * @param mediaType
     * @param bytes
     * @return
     */
    public static String covertStr(@Nullable MediaType mediaType, @Nullable byte[] bytes) {
        Charset charset = LogHelper.getMediaTypeCharset(mediaType);
        boolean isFileType = LogHelper.isFileType(mediaType);
        if (isFileType) {
            return "[文件类型不打印]";
        } else {
            return bytes == null ? null : new String(bytes, charset);
        }
    }

}
