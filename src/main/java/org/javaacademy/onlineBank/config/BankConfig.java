package org.javaacademy.onlineBank.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "bank")
@Configuration
@Getter
@Setter
public class BankConfig {
    private String name;
    private String partnerUrl;
}
