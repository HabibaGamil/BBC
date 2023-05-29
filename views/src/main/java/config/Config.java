package config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@RefreshScope // important
@Configuration
@Component
@ConfigurationProperties(prefix = "views")
@Getter @Setter @ToString
public class Config {
    private String msg;
    private Map<String, String> cmdMap;
    private Map<String, String> modifiableClasses;
}
