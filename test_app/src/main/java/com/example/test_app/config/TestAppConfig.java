package com.example.test_app.config;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "testapp")
@RefreshScope // important
@Getter @Setter @ToString
public class TestAppConfig {
    private String msg;
    private Map<String, String> cmdMap;
}
