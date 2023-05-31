package com.example.lastdemoMaven.dbo;


import com.example.lastdemoMaven.model.PostMetadataEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostEvent implements Serializable {
    PostMetadataEntity post_meta_data;
}
