package com.example.springbootelasticsearch.entity;

import com.example.springbootelasticsearch.helper.Indices;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
