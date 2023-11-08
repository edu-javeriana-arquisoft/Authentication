package co.edu.javeriana.arquitecture.authentication.service;

import lombok.NonNull;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import co.edu.javeriana.arquitecture.authentication.config.oidc.OIDCConfig;
import co.edu.javeriana.arquitecture.authentication.entity.LoginRequest;
import co.edu.javeriana.arquitecture.authentication.entity.RefreshTokenRequest;

import java.util.Map;

@Service
public class OIDCService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OIDCConfig oidcConfig;

    private static final ParameterizedTypeReference<Map<String, String>> KEY_VALUE = new ParameterizedTypeReference<>() {
    };

    /**
     * Logins de user into the system
     * @param userLogin User login request
     * @return OIDC entity with access_token refresh_token etc
     */
    public Map<String, String> login(@NonNull LoginRequest userLogin){
        val data = new LinkedMultiValueMap<String, String>();

        data.add("grant_type", "password");
        data.add("client_id", oidcConfig.getClient_id());
        data.add("client_secret", oidcConfig.getClient_secret());
        data.add("username", userLogin.getUsername());
        data.add("password", userLogin.getPassword());
        data.add("scope", "openid");

        val headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(data, headers);


        try{
            ResponseEntity<Map<String,String>> responseEntity = restTemplate.exchange(
                    oidcConfig.getToken_url(),
                    HttpMethod.POST,
                    entity,
                    KEY_VALUE
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Request to authorization server");
            }
        }catch (HttpClientErrorException e){
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }

    /**
     * Refresh token
     * @param token OIDC refresh token
     * @return new Access_token for the system requests
     */
    public Map<String, String> refreshToken(String token)  {
        val requestParams = new LinkedMultiValueMap<String, String>();
        requestParams.add("client_id", oidcConfig.getClient_id());
        requestParams.add("client_secret", oidcConfig.getClient_secret());
        requestParams.add("refresh_token", token);
        requestParams.add("grant_type", "client_credentials");

        val headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestParams, headers);

        try{
            ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                    oidcConfig.getToken_url(),
                    HttpMethod.POST,
                    entity,
                    KEY_VALUE
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Unable to refresh token");
            }
        }catch (HttpClientErrorException e){
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }

    }

    /**
     * Logouts from the system
     * @param logoutRequest Logout request entity with the refresh token
     * @return Status request from the keycloak server
     * @apiNote  The token will be still valid until it expires. This is intended in the JWT design
     */
    public Map<String, String> logout(@NonNull RefreshTokenRequest logoutRequest){
        val requestParams = new LinkedMultiValueMap<String, String>();
        requestParams.add("client_id", oidcConfig.getClient_id());
        requestParams.add("client_secret", oidcConfig.getClient_secret());
        requestParams.add("refresh_token", logoutRequest.getRefreshToken());
        requestParams.add("grant_type", "client_credentials");

        val headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestParams, headers);

        try{
            ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                    oidcConfig.getLogout_url(),
                    HttpMethod.POST,
                    entity,
                    KEY_VALUE
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Request to authorization server");
            }
        }catch (HttpClientErrorException e){
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
    }
}