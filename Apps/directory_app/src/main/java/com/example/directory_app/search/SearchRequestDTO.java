package com.example.directory_app.search;


import lombok.Builder;
import lombok.Data;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class SearchRequestDTO extends PageRequestDTO {

    private List<String> fields;
    private String searchTerm;
    private String sortBy;
    private SortOrder order;


}
