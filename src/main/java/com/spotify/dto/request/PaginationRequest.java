package com.spotify.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PaginationRequest {
    private int page;
    private int size;
    private String field;
    private String order;
    private String search;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getField() {
        if (field == null) {
            return "";
        }
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        if (order == null) {
            return "asc";
        }
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSearch() {
        if (search == null) {
            return "";
        }
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
