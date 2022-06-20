package com.zq.cloud.utils;

import com.zq.cloud.enums.CommonErrorTypeCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成规则： S + 微服务CODE码+ T +错误类型CODE码 + C+微服务自定义的代表特定含义的信息
 */
public class ErrorCodeUtil {

    private static Logger logger = LoggerFactory.getLogger(ErrorCodeUtil.class);

    /**
     * 前缀S 代表后面是微服务编号
     */
    private static final String SERVICE_PREFIX = "S";


    /**
     * 前缀T 代表后面异常类型
     */
    private static final String ERROR_TYPE_PREFIX = "T";

    /**
     * 前缀C 代表后面微服务自定义的特殊信息
     */
    private static final String CUSTOM_MESSAGE_PREFIX = "C";

    public static String crateErrorCode(CommonErrorTypeCode errorTypeCode) {
        return SERVICE_PREFIX + ERROR_TYPE_PREFIX + errorTypeCode.getCode();
    }

    public static String crateErrorCode(String serviceCode, CommonErrorTypeCode errorTypeCode) {
        return SERVICE_PREFIX + serviceCode + ERROR_TYPE_PREFIX + errorTypeCode.getCode();
    }

    public static String crateErrorCode(String serviceCode, CommonErrorTypeCode errorTypeCode, String custom) {
        return crateErrorCode(serviceCode, errorTypeCode) + CUSTOM_MESSAGE_PREFIX + custom;
    }
}
