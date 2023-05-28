package com.example.test_app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Map;
@Configuration
@Component
@ConfigurationProperties(prefix = "testapp")
@Getter @Setter @ToString
public class Config {
    @Value("${testapp.msg}")
    private String msg;
    private Map<String, String> cmdMap;
    private Map<String, String> modifiableClasses;
}
