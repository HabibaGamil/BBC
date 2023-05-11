package com.example.directory_app.entities;

import com.example.directory_app.helper.Indices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = Indices.TOPIC_INDEX)
@Setting(settingPath= "static/es-settings.json" )
public class TopicEntity {

    @Id
    @Field(type = FieldType.Text)
    private String topicId;

    @Field(type = FieldType.Text)
    private String topicName;

    @Field(type = FieldType.Text)
    private String fk_subCategoryId;

    @Field(type = FieldType.Text)
    private List<String> relatedTopics;

}
