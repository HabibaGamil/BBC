package com.feed.feed.dbo.response;

import com.feed.feed.model.redis.category_data;
import com.feed.feed.model.redis.topic_data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class HomePageResponse extends Response{
    String userId;
    List<category_data> preferences;
    List<topic_data> subscriptions;
    List<Object> news;
}
