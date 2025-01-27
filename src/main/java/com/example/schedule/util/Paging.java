package com.example.schedule.util;

import lombok.Getter;

@Getter
public class Paging {
    private final int pageNo;
    private final int size;
    private final int start;
    private final int end;

    public Paging(int pageNo, int size) {
        this.pageNo = pageNo;
        this.size = size;
        this.start = (pageNo - 1) * size;
        this.end = pageNo * size;
    }
}
