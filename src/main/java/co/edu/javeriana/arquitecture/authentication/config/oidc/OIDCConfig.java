package co.edu.javeriana.arquitecture.authentication.config.oidc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Data class containing OIDC configuration
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "oidc")
@Component
public class OIDCConfig {
    private String client_id;
    private String client_secret;
    private String token_url;
    private String logout_url;
    private String jwks_url;
}