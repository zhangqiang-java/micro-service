package com.zq.cloud.dto.result;

import com.zq.cloud.dto.DtoBase;
import lombok.Data;

@Data
public class ResultBase<T> extends DtoBase {
    private boolean success;

    private String code;

    private String message;

    private T data;


    public ResultBase() {
        this.success = Boolean.TRUE;
        this.code = "SUCCESS";
        this.message = "成功";
    }

    public static ResultBase<Void> success() {
        ResultBase<Void> result = new ResultBase<>();
        result.setSuccess(true);
        result.setCode("SUCCESS");
        result.setMessage("成功");
        return result;
    }

    public static ResultBase<Void> success(String message) {
        ResultBase<Void> result = new ResultBase<>();
        result.setSuccess(true);
        result.setCode("SUCCESS");
        result.setMessage(message);
        return result;
    }

    @Override
    public String toString() {
        return "ResultBase{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
