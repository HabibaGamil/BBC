package com.example.directory_app.DataClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostmetadataViewCountRequest {
    // whenever a get request is made to a post
    private String postMetadataId;
    private long count;
}
