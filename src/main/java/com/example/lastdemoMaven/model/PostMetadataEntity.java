package com.example.lastdemoMaven.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostMetadataEntity implements Serializable {

    private String postMetadataId;
    private String title;
    private String summary;
    private String imageId;
    private List<String> categoryIds;
    private List<String> subcategoryIds;
    private String topicId;
    private List<String> keywords;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date createdAt;
    private int viewCount;

    @Override
    public String toString() {
        return "PostMetadataEntity{" +
                "postMetadataId='" + postMetadataId + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", imageId='" + imageId + '\'' +
                ", categoryIds=" + categoryIds +
                ", subcategoryIds=" + subcategoryIds +
                ", topicId='" + topicId + '\'' +
                ", keywords=" + keywords +
                ", createdAt=" + createdAt +
                ", viewCount=" + viewCount +
                '}';
    }
}
