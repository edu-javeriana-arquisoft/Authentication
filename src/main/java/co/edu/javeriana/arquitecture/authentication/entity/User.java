package co.edu.javeriana.arquitecture.authentication.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class User {
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String password;
}