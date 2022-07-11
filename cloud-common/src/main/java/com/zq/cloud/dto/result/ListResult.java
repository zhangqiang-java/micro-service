package com.zq.cloud.dto.result;

import java.util.List;

public class ListResult<T> extends ResultBase<List<T>> {

    public static <T> ListResult<T> from(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setData(list);
        return result;
    }
}
