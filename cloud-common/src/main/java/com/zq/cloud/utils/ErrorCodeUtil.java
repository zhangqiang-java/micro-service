package com.zq.cloud.utils;

import com.zq.cloud.constant.CommonStaticFinalConstant;
import com.zq.cloud.enums.CommonErrorTypeCode;
import org.apache.commons.lang3.StringUtils;

/**
 * 生成规则： S + 微服务CODE码+ T +错误类型CODE码 + C+微服务自定义的代表特定含义的信息
 */
public class ErrorCodeUtil {

    public static String crateErrorCode(CommonErrorTypeCode errorTypeCode) {
        return CommonStaticFinalConstant.ERROR_TYPE_PREFIX + errorTypeCode.getCode();
    }

    public static String crateErrorCode(String serviceCode, CommonErrorTypeCode errorTypeCode) {
        if (StringUtils.isBlank(serviceCode)) {
            return crateErrorCode(errorTypeCode);
        }
        return CommonStaticFinalConstant.SERVICE_PREFIX + serviceCode + CommonStaticFinalConstant.ERROR_TYPE_PREFIX + errorTypeCode.getCode();
    }

    public static String crateErrorCode(String serviceCode, CommonErrorTypeCode errorTypeCode, String custom) {
        return crateErrorCode(serviceCode, errorTypeCode) + CommonStaticFinalConstant.CUSTOM_MESSAGE_PREFIX + custom;
    }
}
