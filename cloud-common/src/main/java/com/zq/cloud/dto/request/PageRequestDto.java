
package com.zq.cloud.dto.request;


import lombok.Data;

@Data
public class PageRequestDto extends RequestBase {
    /**
     * 页码
     */
    private Integer page;

    /**
     * 每页数量
     */
    private Integer pageSize;


    public Integer getPage() {
        return (page == null || page <= 0) ? 1 : page;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize < 1) {
            return defaultPageSize();
        } else if (pageSize > maxPageSize()) {
            return maxPageSize();
        } else {
            return pageSize;
        }
    }

    protected Integer defaultPageSize() {
        return 10;
    }

    protected Integer maxPageSize() {
        return 5000;
    }


}
