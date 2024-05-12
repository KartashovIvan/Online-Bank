package org.javaacademy.onlineBank.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@AllArgsConstructor
public class SwaggerConfig {
    private BankConfig bankConfig;
    private ServerProperties serverProperties;
    @Bean
    public OpenAPI init() {
        Integer port = serverProperties.getPort();
        String bankName = bankConfig.getName();
        Server bankServer = new Server();
        bankServer.setUrl("http://localhost:%s".formatted(port));
        bankServer.setDescription("Server %s company".formatted(bankName));

        Info info = new Info();
        info.title("Сервис банка %s".formatted(bankName));
        info.version("1.0");
        info.description("Сервис по управлению банковскими операциями");

        return new OpenAPI().info(info).servers(List.of(bankServer));
    }
}
