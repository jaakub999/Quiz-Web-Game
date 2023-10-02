package com.quiz.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private Verification verification;
    private Verification userPassword;
    private Jwt jwt;

    @Data
    public static class Verification {
        private String url;
        private String location;
    }

    @Data
    public static class Jwt {
        private String secret;

        public byte[] getSecretAsBytes() {
            return secret.getBytes();
        }
    }
}
