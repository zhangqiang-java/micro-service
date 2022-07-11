
package com.zq.cloud.dto.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> extends ResultBase<PageResult.PageList<T>> {

    public static <T> PageResult<T> from(PageList<T> pageList) {
        PageResult<T> result = new PageResult<>();
        result.setData(pageList);
        return result;
    }

    public static <T> PageResult<T> from(int page, int totalCount, List<T> list) {
        PageList<T> pageList = new PageList<>();
        pageList.setPage(page);
        pageList.setTotalCount(totalCount);
        pageList.setList(list);
        return from(pageList);
    }


    @Setter
    @Getter
    @ToString
    public static class PageList<E> implements Serializable {

        /**
         * 页码
         */
        protected int page;
        /**
         * 总条数
         */
        protected int totalCount;

        /**
         * 数据
         */
        protected List<E> list;
    }
}