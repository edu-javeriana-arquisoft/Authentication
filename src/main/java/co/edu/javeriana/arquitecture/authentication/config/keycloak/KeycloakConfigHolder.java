package co.edu.javeriana.arquitecture.authentication.config.keycloak;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Data class that holds the keycloak configuration
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "keycloak-config")
@Component
public class KeycloakConfigHolder {
    private String clientId;
    private String clientSecret;
    private String url;
    private String realm;
}