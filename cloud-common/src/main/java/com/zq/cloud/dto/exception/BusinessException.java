package com.zq.cloud.dto.exception;

import com.zq.cloud.enums.CommonErrorTypeCode;
import com.zq.cloud.utils.ErrorCodeUtil;
import lombok.Data;


/**
 * 业务异常
 */
@Data
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        this.code = ErrorCodeUtil.crateErrorCode(CommonErrorTypeCode.BUSINESS_ERROR);
        this.message = message;
    }


    public BusinessException(String serviceCode, String message) {
        super(message);
        this.code = ErrorCodeUtil.crateErrorCode(serviceCode, CommonErrorTypeCode.BUSINESS_ERROR);
        this.message = message;
    }


    public BusinessException(String serviceCode, String message, String custom) {
        super(message);
        this.code = ErrorCodeUtil.crateErrorCode(serviceCode, CommonErrorTypeCode.BUSINESS_ERROR, custom);
        this.message = message;
    }


}
