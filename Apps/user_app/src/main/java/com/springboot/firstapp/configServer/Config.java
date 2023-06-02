package com.springboot.firstapp.configServer;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RefreshScope // important
@Configuration
@Component
@ConfigurationProperties(prefix = "commands")
@Getter @Setter @ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Config {
    //private String msg;
    private Map<String, String> cmdMap;
    //private Map<String, String> modifiableClasses;
}
