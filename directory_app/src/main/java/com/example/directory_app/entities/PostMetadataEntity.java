package com.example.directory_app.entities;

import com.example.directory_app.helper.Indices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = Indices.POSTS_METADATA_INDEX)
@Setting(settingPath= "static/es-settings.json" )
public class PostMetadataEntity {

    @org.springframework.data.annotation.Id
    @Field(type = FieldType.Text)
    private String postMetadataId;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String summary;

    @Field(type = FieldType.Text)
    private String imageId;

    @Field(type = FieldType.Text)
    private List<String> categoryIds;

    @Field(type = FieldType.Text)
    private List<String> subcategoryIds;

    @Field(type = FieldType.Text)
    private String topicId;

    @Field(type = FieldType.Text)
    private List<String> keywords;

    @Field(type= FieldType.Date)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date createdAt;

    @Field(type = FieldType.Long)
    private long viewCount;

}
