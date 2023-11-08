package co.edu.javeriana.arquitecture.authentication.controller;

import co.edu.javeriana.arquitecture.authentication.entity.LoginRequest;
import co.edu.javeriana.arquitecture.authentication.entity.RefreshTokenRequest;
import co.edu.javeriana.arquitecture.authentication.service.OIDCService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    @Autowired
    private OIDCService oidcService;

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginRequest userLogin){
        return oidcService.login(userLogin);
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@Valid @RequestBody RefreshTokenRequest token){
        return oidcService.refreshToken(token.getRefreshToken());
    }


    @PostMapping("/logout")
    public Map<String, String> logout(@Valid @RequestBody RefreshTokenRequest userLogout){
        return oidcService.logout(userLogout);
    }
}