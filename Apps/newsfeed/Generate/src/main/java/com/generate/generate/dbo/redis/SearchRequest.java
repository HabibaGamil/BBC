package com.generate.generate.dbo.redis;

import lombok.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchRequest implements Serializable {
    private boolean most_viewed;
    private int type;
    private List<String> ids;
    private int number_of_posts;
}