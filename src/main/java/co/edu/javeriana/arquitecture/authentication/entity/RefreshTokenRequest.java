package co.edu.javeriana.arquitecture.authentication.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotNull
    @NotBlank
    private String refreshToken;
}