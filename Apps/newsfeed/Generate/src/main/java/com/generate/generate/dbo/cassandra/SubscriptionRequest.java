package com.generate.generate.dbo.cassandra;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequest implements Serializable {
    private String userId;

    private List<String> topicsId;

    private List<String> topicsName;
}
