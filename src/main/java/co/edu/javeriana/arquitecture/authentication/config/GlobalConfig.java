package co.edu.javeriana.arquitecture.authentication.config;

import co.edu.javeriana.arquitecture.authentication.config.oidc.OIDCConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class GlobalConfig {
    @Autowired
    private OIDCConfig oidcConfig;
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}