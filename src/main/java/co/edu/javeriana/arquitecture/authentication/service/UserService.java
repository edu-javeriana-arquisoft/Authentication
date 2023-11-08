package co.edu.javeriana.arquitecture.authentication.service;

import lombok.NonNull;
import lombok.val;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.javeriana.arquitecture.authentication.config.keycloak.KeycloakConfigHolder;
import co.edu.javeriana.arquitecture.authentication.entity.User;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private Keycloak keycloak;

    @Autowired
    private KeycloakConfigHolder keycloakConfig;

    /**
     * Register user in the system
     * @param user User entity with the new user info
     */
    public void addUser(@NonNull User user){

        val credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(user.getPassword());

        val userR = new UserRepresentation();
        userR.setUsername(user.getUsername());
        userR.setEmail(user.getEmail());
        userR.setCredentials(List.of(credentials));
        userR.setEnabled(true);

        try(val response = keycloak.realm(keycloakConfig.getRealm()).users().create(userR)){
            val status = HttpStatus.valueOf(response.getStatus());
            if(status.is4xxClientError()){
                throw new ResponseStatusException(status, status.getReasonPhrase());
            }
        }
    }

}