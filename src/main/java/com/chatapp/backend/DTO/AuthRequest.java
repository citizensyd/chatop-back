package com.chatapp.backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The AuthRequest class represents a request to authenticate a user.
 * It contains the user's email (or login) and password.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @Schema(description = "Email or login of the user", example = "john.doe@example.com", required = true)
    @NotBlank(message = "Email or login is required")
    private String login;

    @Schema(description = "Password of the user", example = "pa$$word56", required = true)
    @NotBlank(message = "Password is required")
    private String password;
}
