package com.zq.cloud.dto.result;

public class SingleResult<T> extends ResultBase<T> {

    public static <T> SingleResult<T> from(T dto) {
        SingleResult<T> singleResult = new SingleResult<>();
        singleResult.setData(dto);
        return singleResult;
    }

}
