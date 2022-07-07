package com.zq.cloud.dto.result;

import com.zq.cloud.constant.CommonStaticFinalConstant;
import com.zq.cloud.dto.DtoBase;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultBase<T> extends DtoBase {
    private boolean success;

    private String errorCode;

    private String message;

    private T data;


    public ResultBase() {
        this.success = Boolean.TRUE;
        this.errorCode = CommonStaticFinalConstant.SUCCESS_CODE;
        this.message = "成功";
    }

    public static ResultBase<Void> success() {
        ResultBase<Void> result = new ResultBase<>();
        result.setSuccess(true);
        result.setErrorCode(CommonStaticFinalConstant.SUCCESS_CODE);
        result.setMessage("成功");
        return result;
    }

    public static ResultBase<Void> success(String message) {
        ResultBase<Void> result = new ResultBase<>();
        result.setSuccess(true);
        result.setErrorCode(CommonStaticFinalConstant.SUCCESS_CODE);
        result.setMessage(message);
        return result;
    }

    @Override
    public String toString() {
        return "ResultBase{" +
                "message='" + message + '\'' +
                ", code='" + errorCode + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
