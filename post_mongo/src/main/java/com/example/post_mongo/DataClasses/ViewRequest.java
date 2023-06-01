package com.example.post_mongo.DataClasses;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewRequest implements Serializable {
    private String userId;
    private String categoryId;
}