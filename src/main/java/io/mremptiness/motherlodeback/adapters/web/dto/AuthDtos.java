package io.mremptiness.motherlodeback.adapters.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDtos {

    public record RegisterRequest(
            @Schema(example = "user@example.com") @NotBlank @Email String email,
            @Schema(example = "P@ssw0rd!") @NotBlank String password
    ) {}

    public record LoginRequest(
            @Schema(example = "user@example.com") @NotBlank @Email String email,
            @Schema(example = "P@ssw0rd!") @NotBlank String password
    ) {}

    public record TokenResponse(String token) {}
}
