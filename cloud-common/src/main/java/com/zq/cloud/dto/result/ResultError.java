package com.zq.cloud.dto.result;

public class ResultError<T> extends ResultBase<T> {

    public static ResultError<Void> error(String code, String message) {
        ResultError<Void> result = new ResultError<>();
        result.setSuccess(false);
        result.setErrorCode(code);
        result.setMessage(message);
        return result;
    }
}
