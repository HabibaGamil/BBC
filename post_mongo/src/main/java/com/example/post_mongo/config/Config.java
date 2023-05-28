package com.example.post_mongo.config;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Map;

@RefreshScope // important
@Configuration
@ConfigurationProperties(prefix = "post-mongo")
@Getter @Setter @ToString
public class Config {
    private String msg;
    private Map<String, String> cmdMap;
 //   private Map<String, String> modifiableClasses;
}
