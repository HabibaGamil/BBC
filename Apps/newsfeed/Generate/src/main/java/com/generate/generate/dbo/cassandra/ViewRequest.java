package com.generate.generate.dbo.cassandra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewRequest implements Serializable {

    private String userId;
    private String categoryId;
}
