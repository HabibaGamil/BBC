package com.feed.feed.dbo.response;

import com.feed.feed.model.redis.sub_category_data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class SubCategoryResponse extends Response{
    private String subCategoryId;
    private sub_category_data posts;
}
