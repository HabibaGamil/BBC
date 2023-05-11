package com.example.springbootelasticsearch.search;

public class PageRequestDTO {
    private static final int DEFAULT_SIZE = 100;
    private int page;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }




}
