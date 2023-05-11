package com.example.springbootelasticsearch.search.util;

import com.example.springbootelasticsearch.search.SearchRequestDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;
import org.elasticsearch.index.query.*;

import java.util.List;

public final class SearchUtil {
    private SearchUtil(){}


    public static SearchRequest buildSearchRequest(final String indexName, final SearchRequestDTO dto){
        try{
            final int page = dto.getPage();
            final int size = dto.getSize();
            final int from = page <=0? 0: page*size;
            SearchSourceBuilder builder = new SearchSourceBuilder()
                    .from(from)
                    .size(size)
                    .postFilter(getQueryBuilder(dto));

            if(dto.getSortBy() != null){
                 builder = builder.sort(
                         dto.getSortBy(),
                         dto.getOrder() != null? dto.getOrder() : SortOrder.ASC
                 );
            }

            SearchRequest request= new SearchRequest(indexName);
            request.source(builder);
            return  request;

        } catch (final Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static QueryBuilder getQueryBuilder(final SearchRequestDTO dto){
        if(dto==null) return null;


        final List<String> fields = dto.getFields();
        if(CollectionUtils.isEmpty(fields)) return null;

        if(fields.size()>1){
            MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm())
                    .operator(Operator.OR)
                    .fuzziness("AUTO");

            fields.forEach(queryBuilder::field);
            return queryBuilder;
        }

        return fields.stream()
                .findFirst()
                .map(field ->
                        QueryBuilders.matchQuery(field, dto.getSearchTerm())
                                .operator(Operator.OR)
                                .fuzziness("AUTO")
                               )
                .orElse(null);
    }
}
