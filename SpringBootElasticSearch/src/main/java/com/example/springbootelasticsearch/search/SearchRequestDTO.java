package com.example.springbootelasticsearch.search;


import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

public class SearchRequestDTO extends PageRequestDTO {

    private List<String> fields;
    private String searchTerm;
    private String sortBy;
    private SortOrder order;

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public SortOrder getOrder() {
        return order;
    }

    public void setOrder(SortOrder order) {
        this.order = order;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

}
