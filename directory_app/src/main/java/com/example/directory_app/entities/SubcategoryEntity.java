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
@Document(indexName = Indices.SUBCATEGORY_INDEX)
@Setting(settingPath= "static/es-settings.json" )
public class SubcategoryEntity {
    @Id
    @Field(type = FieldType.Text)
    private String subcategoryId;

    @Field(type = FieldType.Text)
    private String subcategoryName;

    @Field(type = FieldType.Text)
    private String fk_categoryId;

}
