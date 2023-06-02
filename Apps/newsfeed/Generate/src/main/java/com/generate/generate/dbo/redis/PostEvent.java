package com.generate.generate.dbo.redis;

import com.generate.generate.model.redis.PostMetadataEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostEvent implements Serializable {
    PostMetadataEntity post_meta_data;


}